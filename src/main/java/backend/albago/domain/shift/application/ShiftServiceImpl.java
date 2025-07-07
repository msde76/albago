package backend.albago.domain.shift.application;

import backend.albago.domain.member.domain.entity.Member;
import backend.albago.domain.member.domain.repository.MemberRepository;
import backend.albago.domain.shift.converter.ShiftConverter;
import backend.albago.domain.shift.domain.entity.ShiftLog;
import backend.albago.domain.shift.domain.repository.ShiftLogRepository;
import backend.albago.domain.shift.dto.ShiftRequestDTO;
import backend.albago.domain.shift.dto.ShiftResponseDTO;
import backend.albago.domain.shift.exception.ShiftException;
import backend.albago.domain.team.domain.entity.Team;
import backend.albago.domain.team.domain.repository.TeamMemberRepository;
import backend.albago.domain.team.domain.repository.TeamRepository;
import backend.albago.global.error.code.status.ErrorStatus;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ShiftServiceImpl implements ShiftService {

    private final MemberRepository memberRepository;
    private final TeamRepository teamRepository;
    private final TeamMemberRepository teamMemberRepository;
    private final ShiftLogRepository shiftLogRepository;
    private final GeodesyService geodesyService;

    @Override
    @Transactional
    public ShiftResponseDTO.ClockInResult clockIn(Long teamId, String memberIdString, ShiftRequestDTO.ClockInDTO request) {
        Long memberId = Long.parseLong(memberIdString);

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ShiftException(ErrorStatus.NO_SUCH_MEMBER));
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new ShiftException(ErrorStatus.TEAM_NOT_FOUND));

        if (!teamMemberRepository.existsByTeamAndMember(team, member)) {
            throw new ShiftException(ErrorStatus.MEMBER_NOT_IN_TEAM);
        }

        // --- GPS 위치 검증 로직 시작 ---
        if (team.getReferenceLatitude() == null || team.getReferenceLongitude() == null || team.getAttendanceRadiusMeters() == null) {
            throw new ShiftException(ErrorStatus.LOCATION_NOT_CONFIGURED);
        }

        double distance = geodesyService.calculateDistance(
                team.getReferenceLatitude(),
                team.getReferenceLongitude(),
                request.getCurrentLatitude(),
                request.getCurrentLongitude()
        );

        if (distance > team.getAttendanceRadiusMeters()) {
            throw new ShiftException(ErrorStatus.OUT_OF_ATTENDANCE_RADIUS);
        }
        // --- GPS 위치 검증 로직 끝 ---

        Optional<ShiftLog> existingShift = shiftLogRepository.findTopByMemberAndTeamAndClockOutTimeIsNullOrderByClockInTimeDesc(member, team);
        if (existingShift.isPresent()) {
            throw new ShiftException(ErrorStatus.ALREADY_CLOCK_IN);
        }

        ShiftLog newShiftLog = ShiftLog.builder()
                .member(member)
                .team(team)
                .shiftDate(LocalDateTime.now().toLocalDate())
                .clockInTime(LocalDateTime.now())
                .recordedClockInLatitude(request.getCurrentLatitude())
                .recordedClockInLongitude(request.getCurrentLongitude())
                .clockOutTime(null)
                .payLocation(team.getLocationName())
                .build();

        ShiftLog savedShiftLog = shiftLogRepository.save(newShiftLog);

        return ShiftConverter.toClockInResult(savedShiftLog, "출근이 기록되었습니다.");
    }

    @Override
    @Transactional
    public ShiftResponseDTO.ClockOutResult clockOut(Long teamId, String memberIdString, ShiftRequestDTO.ClockOutDTO request) {
        Long memberId = Long.parseLong(memberIdString);

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ShiftException(ErrorStatus.NO_SUCH_MEMBER));
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new ShiftException(ErrorStatus.TEAM_NOT_FOUND));

        if (!teamMemberRepository.existsByTeamAndMember(team, member)) {
            throw new ShiftException(ErrorStatus.MEMBER_NOT_IN_TEAM);
        }

        // --- GPS 위치 검증 로직 시작 (출근과 동일하게) ---
        if (team.getReferenceLatitude() == null || team.getReferenceLongitude() == null || team.getAttendanceRadiusMeters() == null) {
            throw new ShiftException(ErrorStatus.LOCATION_NOT_CONFIGURED);
        }

        double distance = geodesyService.calculateDistance(
                team.getReferenceLatitude(),
                team.getReferenceLongitude(),
                request.getCurrentLatitude(),
                request.getCurrentLongitude()
        );

        if (distance > team.getAttendanceRadiusMeters()) {
            throw new ShiftException(ErrorStatus.OUT_OF_ATTENDANCE_RADIUS);
        }
        // --- GPS 위치 검증 로직 끝 ---

        ShiftLog currentShiftLog = shiftLogRepository.findTopByMemberAndTeamAndClockOutTimeIsNullOrderByClockInTimeDesc(member, team)
                .orElseThrow(() -> new ShiftException(ErrorStatus.NOT_CLOCK_IN_STATE));

        currentShiftLog.clockOut(LocalDateTime.now(), request.getPayLocation());
        currentShiftLog.setRecordedClockOutLatitude(request.getCurrentLatitude());
        currentShiftLog.setRecordedClockOutLongitude(request.getCurrentLongitude());

        return ShiftConverter.toClockOutResult(currentShiftLog, "퇴근이 기록되었습니다.");
    }

    @Override
    @Transactional(readOnly = true)
    public ShiftResponseDTO.findShiftsResult findShifts(Long teamId, String memberIdString, LocalDate startDate, LocalDate endDate) {
        Long memberId = Long.parseLong(memberIdString);

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ShiftException(ErrorStatus.NO_SUCH_MEMBER));
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new ShiftException(ErrorStatus.TEAM_NOT_FOUND));

        if (!teamMemberRepository.existsByTeamAndMember(team, member)) {
            throw new ShiftException(ErrorStatus.MEMBER_NOT_IN_TEAM);
        }

        if (startDate.isAfter(endDate)) {
            throw new ShiftException(ErrorStatus.INVALID_DATE_RANGE);
        }

        List<ShiftLog> shiftLogs = shiftLogRepository.findByMemberAndTeamAndShiftDateBetweenAndClockOutTimeIsNotNullOrderByShiftDateAscClockInTimeAsc(
                member, team, startDate, endDate
        );

        return ShiftConverter.toFindShiftsResult(memberId, teamId, startDate, endDate, shiftLogs);
    }

    @Override
    @Transactional(readOnly = true)
    public ShiftResponseDTO.findTeamShiftsResult findTeamShifts(Long teamId, String memberIdString, LocalDate startDate, LocalDate endDate) {

        Long requesterMemberId = Long.parseLong(memberIdString);

        memberRepository.findById(requesterMemberId)
                .orElseThrow(() -> new ShiftException(ErrorStatus.NO_SUCH_MEMBER));

        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new ShiftException(ErrorStatus.TEAM_NOT_FOUND));

        if (!team.getOwnerMember().getId().equals(requesterMemberId)) {
            throw new ShiftException(ErrorStatus._FORBIDDEN);
        }

        if (startDate.isAfter(endDate)) {
            throw new ShiftException(ErrorStatus.INVALID_DATE_RANGE);
        }

        List<ShiftLog> shiftLogs = shiftLogRepository.findByTeamAndShiftDateBetweenAndClockOutTimeIsNotNullOrderByShiftDateAscClockInTimeAsc(
                team, startDate, endDate
        );

        return ShiftConverter.toFindTeamShiftsResult(team.getId(), team.getTeamName(), startDate, endDate, shiftLogs);
    }
}
