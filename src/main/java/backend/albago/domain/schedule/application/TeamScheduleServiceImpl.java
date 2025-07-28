package backend.albago.domain.schedule.application;

import backend.albago.domain.member.domain.entity.Member;
import backend.albago.domain.member.domain.repository.MemberRepository;
import backend.albago.domain.schedule.converter.TeamScheduleConverter;
import backend.albago.domain.schedule.domain.entity.PersonalSchedule;
import backend.albago.domain.schedule.domain.entity.TeamSchedule;

import backend.albago.domain.schedule.domain.repository.PersonalScheduleRepository;
import backend.albago.domain.schedule.domain.repository.TeamScheduleRepository;
import backend.albago.domain.schedule.dto.TeamScheduleRequestDTO;
import backend.albago.domain.schedule.dto.TeamScheduleResponseDTO;
import backend.albago.domain.schedule.exception.ScheduleException;
import backend.albago.domain.team.domain.entity.Team;
import backend.albago.domain.team.domain.entity.TeamMember; // TeamMember 임포트 추가
import backend.albago.domain.team.domain.repository.TeamMemberRepository;
import backend.albago.domain.team.domain.repository.TeamRepository;
import backend.albago.global.error.code.status.ErrorStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TeamScheduleServiceImpl implements TeamScheduleService {

    private final MemberRepository memberRepository;
    private final TeamRepository teamRepository;
    private final TeamScheduleRepository teamScheduleRepository;
    private final TeamMemberRepository teamMemberRepository;
    private final PersonalScheduleRepository personalScheduleRepository;

    @Override
    @Transactional
    public TeamScheduleResponseDTO.CreateTeamScheduleResult createTeamSchedule(
            Long teamId,
            String requestMemberIdString,
            TeamScheduleRequestDTO.CreateTeamScheduleDTO request) {

        Long requestMemberId = Long.parseLong(requestMemberIdString);

        Member requestMember = memberRepository.findById(requestMemberId)
                .orElseThrow(() -> new ScheduleException(ErrorStatus.NO_SUCH_MEMBER));

        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new ScheduleException(ErrorStatus.TEAM_NOT_FOUND));

        if (!team.getOwnerMember().getId().equals(requestMember.getId())) {
            throw new ScheduleException(ErrorStatus._FORBIDDEN);
        }

        Member assignedMember = null;
        TeamMember assignedTeamMemberInfo = null; // 할당된 멤버의 TeamMember 정보
        if (request.getMemberId() != null) {
            assignedMember = memberRepository.findById(request.getMemberId())
                    .orElseThrow(() -> new ScheduleException(ErrorStatus.NO_SUCH_MEMBER));

            // 할당된 멤버가 해당 팀에 속하는지 확인하고 TeamMember 정보 가져오기
            assignedTeamMemberInfo = teamMemberRepository.findByTeamAndMember(team, assignedMember)
                    .orElseThrow(() -> new ScheduleException(ErrorStatus.MEMBER_NOT_IN_TEAM));
        }

        // TeamScheduleConverter에 TeamMember 정보를 함께 전달하여 급여 정보 복사
        TeamSchedule teamSchedule = TeamScheduleConverter.toTeamSchedule(team, assignedMember, assignedTeamMemberInfo, request);
        TeamSchedule savedTeamSchedule = teamScheduleRepository.save(teamSchedule);

        // 개인 스케줄에 반영 (할당된 멤버가 있는 경우)
        if (assignedMember != null) {
            PersonalSchedule personalSchedule = PersonalSchedule.builder()
                    .member(assignedMember)
                    .team(team)
                    .scheduleType("team")
                    .startTime(savedTeamSchedule.getStartTime())
                    .endTime(savedTeamSchedule.getEndTime())
                    .name(savedTeamSchedule.getName())
                    .memo(savedTeamSchedule.getMemo())
                    .color(savedTeamSchedule.getColor())
                    .relatedTeamScheduleId(savedTeamSchedule.getId())

                    // PersonalSchedule에 존재하는 급여 필드만 복사
                    .hourlyWage(savedTeamSchedule.getScheduleHourlyWage())
                    .weeklyAllowance(savedTeamSchedule.getWeeklyAllowance())
                    .nightAllowance(savedTeamSchedule.getNightAllowance())
                    .nightRate(savedTeamSchedule.getNightRate())
                    .overtimeAllowance(savedTeamSchedule.getOvertimeAllowance())
                    .overtimeRate(savedTeamSchedule.getOvertimeRate())
                    .holidayAllowance(savedTeamSchedule.getHolidayAllowance())
                    .holidayRate(savedTeamSchedule.getHolidayRate())
                    .deductions(savedTeamSchedule.getDeductions())
                    .build();
            personalScheduleRepository.save(personalSchedule);
        }

        return TeamScheduleConverter.toCreateTeamScheduleResult(savedTeamSchedule);
    }

    @Override
    @Transactional(readOnly = true)
    public TeamScheduleResponseDTO.FindTeamScheduleResult findTeamSchedule(
            Long teamId,
            LocalDate startDate,
            LocalDate endDate,
            String requestMemberIdString) {

        Long requestMemberId = Long.parseLong(requestMemberIdString);

        Member requestMember = memberRepository.findById(requestMemberId)
                .orElseThrow(() -> new ScheduleException(ErrorStatus.NO_SUCH_MEMBER));

        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new ScheduleException(ErrorStatus.TEAM_NOT_FOUND));

        if (!teamMemberRepository.existsByTeamAndMember(team, requestMember)) {
            throw new ScheduleException(ErrorStatus._FORBIDDEN);
        }

        if (startDate.isAfter(endDate)) {
            throw new ScheduleException(ErrorStatus._BAD_REQUEST);
        }

        List<TeamSchedule> teamSchedules =
                teamScheduleRepository.findByTeamIdAndStartTimeBetweenOrderByStartTimeAsc(
                        teamId, startDate.atStartOfDay(), endDate.atStartOfDay().plusDays(1).minusNanos(1)
                );


        return TeamScheduleConverter.toFindTeamScheduleResult(teamId, teamSchedules);
    }

    @Override
    @Transactional
    public TeamScheduleResponseDTO.UpdateTeamScheduleResult updateTeamSchedule(
            Long teamId,
            Long teamScheduleId,
            String requestMemberIdString,
            TeamScheduleRequestDTO.UpdateTeamScheduleDTO request) {

        Long requestMemberId = Long.parseLong(requestMemberIdString);

        Member requestMember = memberRepository.findById(requestMemberId)
                .orElseThrow(() -> new ScheduleException(ErrorStatus.NO_SUCH_MEMBER));

        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new ScheduleException(ErrorStatus.TEAM_NOT_FOUND));

        TeamSchedule teamScheduleToUpdate = teamScheduleRepository.findById(teamScheduleId)
                .orElseThrow(() -> new ScheduleException(ErrorStatus.SCHEDULE_NOT_FOUND));

        if (!team.getOwnerMember().getId().equals(requestMember.getId())) {
            throw new ScheduleException(ErrorStatus._FORBIDDEN);
        }
        if (!teamScheduleToUpdate.getTeam().getId().equals(teamId)) {
            throw new ScheduleException(ErrorStatus._BAD_REQUEST);
        }

        Member oldAssignedMember = teamScheduleToUpdate.getMember();
        Member newAssignedMember = null;

        if (request.getMemberId() != null) {
            newAssignedMember = memberRepository.findById(request.getMemberId())
                    .orElseThrow(() -> new ScheduleException(ErrorStatus.NO_SUCH_MEMBER));

            teamMemberRepository.findByTeamAndMember(team, newAssignedMember)
                    .orElseThrow(() -> new ScheduleException(ErrorStatus.MEMBER_NOT_IN_TEAM));

            teamScheduleToUpdate.setMember(newAssignedMember);
        } else {
            teamScheduleToUpdate.setMember(null);
        }

        Optional.ofNullable(request.getStartTime()).ifPresent(teamScheduleToUpdate::setStartTime);
        Optional.ofNullable(request.getEndTime()).ifPresent(teamScheduleToUpdate::setEndTime);
        Optional.ofNullable(request.getName()).ifPresent(teamScheduleToUpdate::setName);
        Optional.ofNullable(request.getMemo()).ifPresent(teamScheduleToUpdate::setMemo);
        Optional.ofNullable(request.getColor()).ifPresent(teamScheduleToUpdate::setColor);

        Optional.ofNullable(request.getScheduleHourlyWage()).ifPresent(teamScheduleToUpdate::setScheduleHourlyWage);
        Optional.ofNullable(request.getWeeklyAllowance()).ifPresent(teamScheduleToUpdate::setWeeklyAllowance);
        Optional.ofNullable(request.getNightAllowance()).ifPresent(teamScheduleToUpdate::setNightAllowance);
        Optional.ofNullable(request.getNightRate()).ifPresent(teamScheduleToUpdate::setNightRate);
        Optional.ofNullable(request.getOvertimeAllowance()).ifPresent(teamScheduleToUpdate::setOvertimeAllowance);
        Optional.ofNullable(request.getOvertimeRate()).ifPresent(teamScheduleToUpdate::setOvertimeRate);
        Optional.ofNullable(request.getHolidayAllowance()).ifPresent(teamScheduleToUpdate::setHolidayAllowance);
        Optional.ofNullable(request.getHolidayRate()).ifPresent(teamScheduleToUpdate::setHolidayRate);
        Optional.ofNullable(request.getDeductions()).ifPresent(teamScheduleToUpdate::setDeductions);

        Optional<PersonalSchedule> optionalPersonalSchedule = personalScheduleRepository.findByRelatedTeamScheduleId(teamScheduleId); // findByTeamScheduleId -> findByRelatedTeamScheduleId로 변경

        if (oldAssignedMember != null && newAssignedMember == null) {
            optionalPersonalSchedule.ifPresent(personalScheduleRepository::delete);
        } else if (newAssignedMember != null) {
            if (optionalPersonalSchedule.isPresent()) {
                PersonalSchedule personalSchedule = optionalPersonalSchedule.get();

                if (!personalSchedule.getMember().getId().equals(newAssignedMember.getId())) {
                    personalScheduleRepository.delete(personalSchedule);
                    createAndSavePersonalSchedule(newAssignedMember, teamScheduleToUpdate);
                } else {
                    updatePersonalScheduleContent(personalSchedule, teamScheduleToUpdate);
                }
            } else {
                createAndSavePersonalSchedule(newAssignedMember, teamScheduleToUpdate);
            }
        }

        return TeamScheduleConverter.toUpdateTeamScheduleResult(teamScheduleToUpdate);
    }

    @Override
    @Transactional
    public void deleteTeamSchedule(
            Long teamId,
            Long teamScheduleId,
            String requestMemberIdString) {

        Long requestMemberId = Long.parseLong(requestMemberIdString);

        Member requestMember = memberRepository.findById(requestMemberId)
                .orElseThrow(() -> new ScheduleException(ErrorStatus.NO_SUCH_MEMBER));

        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new ScheduleException(ErrorStatus.TEAM_NOT_FOUND));

        TeamSchedule teamScheduleToDelete = teamScheduleRepository.findById(teamScheduleId)
                .orElseThrow(() -> new ScheduleException(ErrorStatus.SCHEDULE_NOT_FOUND));

        if (!team.getOwnerMember().getId().equals(requestMember.getId())) {
            throw new ScheduleException(ErrorStatus._FORBIDDEN);
        }
        if (!teamScheduleToDelete.getTeam().getId().equals(teamId)) {
            throw new ScheduleException(ErrorStatus._BAD_REQUEST);
        }

        // 팀 스케줄 삭제 시, 연결된 개인 스케줄도 삭제
        personalScheduleRepository.findByRelatedTeamScheduleId(teamScheduleId)
                .ifPresent(personalScheduleRepository::delete);

        teamScheduleRepository.delete(teamScheduleToDelete);
    }

    // 개인 스케줄 생성 헬퍼 메서드
    private void createAndSavePersonalSchedule(Member member, TeamSchedule teamSchedule) {
        PersonalSchedule personalSchedule = PersonalSchedule.builder()
                .member(member)
                .team(teamSchedule.getTeam())
                .scheduleType("team")
                .startTime(teamSchedule.getStartTime())
                .endTime(teamSchedule.getEndTime())
                .name(teamSchedule.getName())
                .memo(teamSchedule.getMemo())
                .color(teamSchedule.getColor())
                .relatedTeamScheduleId(teamSchedule.getId())

                // PersonalSchedule에 존재하는 급여 필드만 복사 (TeamSchedule에 해당 필드가 있다고 가정)
                .hourlyWage(teamSchedule.getScheduleHourlyWage())
                .weeklyAllowance(teamSchedule.getWeeklyAllowance())
                .nightAllowance(teamSchedule.getNightAllowance())
                .nightRate(teamSchedule.getNightRate())
                .overtimeAllowance(teamSchedule.getOvertimeAllowance())
                .overtimeRate(teamSchedule.getOvertimeRate())
                .holidayAllowance(teamSchedule.getHolidayAllowance())
                .holidayRate(teamSchedule.getHolidayRate())
                .deductions(teamSchedule.getDeductions())
                .build();
        personalScheduleRepository.save(personalSchedule);
    }

    // 개인 스케줄 내용 업데이트 헬퍼 메서드
    private void updatePersonalScheduleContent(PersonalSchedule personalSchedule, TeamSchedule teamSchedule) {
        personalSchedule.setStartTime(teamSchedule.getStartTime());
        personalSchedule.setEndTime(teamSchedule.getEndTime());
        personalSchedule.setName(teamSchedule.getName());
        personalSchedule.setMemo(teamSchedule.getMemo());
        personalSchedule.setColor(teamSchedule.getColor());

        // PersonalSchedule에 존재하는 급여 필드만 업데이트 (TeamSchedule에 해당 필드가 있다고 가정)
        personalSchedule.setHourlyWage(teamSchedule.getScheduleHourlyWage());
        personalSchedule.setWeeklyAllowance(teamSchedule.getWeeklyAllowance());
        personalSchedule.setNightAllowance(teamSchedule.getNightAllowance());
        personalSchedule.setNightRate(teamSchedule.getNightRate());
        personalSchedule.setOvertimeAllowance(teamSchedule.getOvertimeAllowance());
        personalSchedule.setOvertimeRate(teamSchedule.getOvertimeRate());
        personalSchedule.setHolidayAllowance(teamSchedule.getHolidayAllowance());
        personalSchedule.setHolidayRate(teamSchedule.getHolidayRate());
        personalSchedule.setDeductions(teamSchedule.getDeductions());

        personalScheduleRepository.save(personalSchedule);
    }
}