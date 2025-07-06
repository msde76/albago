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
        if (request.getMemberId() != null) {
            assignedMember = memberRepository.findById(request.getMemberId())
                    .orElseThrow(() -> new ScheduleException(ErrorStatus.NO_SUCH_MEMBER));

            if (!teamMemberRepository.existsByTeamAndMember(team, assignedMember)) {
                throw new ScheduleException(ErrorStatus.MEMBER_NOT_IN_TEAM);
            }
        }

        TeamSchedule teamSchedule = TeamScheduleConverter.toTeamSchedule(team, assignedMember, request);
        TeamSchedule savedTeamSchedule = teamScheduleRepository.save(teamSchedule);

        if (assignedMember != null) {
            PersonalSchedule personalSchedule = PersonalSchedule.builder()
                    .member(assignedMember)
                    .date(savedTeamSchedule.getDate())
                    .startTime(savedTeamSchedule.getStartTime())
                    .endTime(savedTeamSchedule.getEndTime())
                    .title(savedTeamSchedule.getTitle())
                    .memo(savedTeamSchedule.getMemo())
                    .color(savedTeamSchedule.getColor())
                    .teamScheduleId(savedTeamSchedule.getId())
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
                teamScheduleRepository.findByTeamIdAndDateBetweenOrderByDateAscStartTimeAsc(teamId, startDate, endDate);

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

            if (!teamMemberRepository.existsByTeamAndMember(team, newAssignedMember)) {
                throw new ScheduleException(ErrorStatus.MEMBER_NOT_IN_TEAM);
            }
            teamScheduleToUpdate.setMember(newAssignedMember);
        } else {
            teamScheduleToUpdate.setMember(null);
        }

        Optional.ofNullable(request.getDate()).ifPresent(teamScheduleToUpdate::setDate);
        Optional.ofNullable(request.getStartTime()).ifPresent(teamScheduleToUpdate::setStartTime);
        Optional.ofNullable(request.getEndTime()).ifPresent(teamScheduleToUpdate::setEndTime);
        Optional.ofNullable(request.getTitle()).ifPresent(teamScheduleToUpdate::setTitle);
        Optional.ofNullable(request.getMemo()).ifPresent(teamScheduleToUpdate::setMemo);
        Optional.ofNullable(request.getColor()).ifPresent(teamScheduleToUpdate::setColor);

        // 3. 개인 스케줄에 반영 로직
        Optional<PersonalSchedule> optionalPersonalSchedule = personalScheduleRepository.findByTeamScheduleId(teamScheduleId);

        if (optionalPersonalSchedule.isPresent()) {
            PersonalSchedule personalSchedule = optionalPersonalSchedule.get();

            if (oldAssignedMember != null && newAssignedMember == null) {
                // 기존에 할당된 멤버가 있었는데 이제 없어짐 -> 개인 스케줄 삭제
                personalScheduleRepository.delete(personalSchedule);
            } else if (oldAssignedMember == null && newAssignedMember != null) {
                // 기존에 할당된 멤버가 없었는데 새로 할당됨 -> 개인 스케줄 새로 생성
                PersonalSchedule newPersonalSchedule = PersonalSchedule.builder()
                        .member(newAssignedMember)
                        .date(teamScheduleToUpdate.getDate())
                        .startTime(teamScheduleToUpdate.getStartTime())
                        .endTime(teamScheduleToUpdate.getEndTime())
                        .title(teamScheduleToUpdate.getTitle())
                        .memo(teamScheduleToUpdate.getMemo())
                        .color(teamScheduleToUpdate.getColor())
                        .teamScheduleId(teamScheduleToUpdate.getId())
                        .build();
                personalScheduleRepository.save(newPersonalSchedule);
            } else if (oldAssignedMember != null && newAssignedMember != null && !oldAssignedMember.getId().equals(newAssignedMember.getId())) {
                // 할당된 멤버가 다른 멤버로 변경됨 -> 기존 개인 스케줄 삭제 후 새 멤버의 개인 스케줄 생성
                personalScheduleRepository.delete(personalSchedule);
                PersonalSchedule newPersonalSchedule = PersonalSchedule.builder()
                        .member(newAssignedMember)
                        .date(teamScheduleToUpdate.getDate())
                        .startTime(teamScheduleToUpdate.getStartTime())
                        .endTime(teamScheduleToUpdate.getEndTime())
                        .title(teamScheduleToUpdate.getTitle())
                        .memo(teamScheduleToUpdate.getMemo())
                        .color(teamScheduleToUpdate.getColor())
                        .teamScheduleId(teamScheduleToUpdate.getId())
                        .build();
                personalScheduleRepository.save(newPersonalSchedule);
            } else {
                // 할당된 멤버는 동일하거나, 할당된 멤버가 없는데 여전히 없는 경우 -> 내용만 업데이트
                personalSchedule.setDate(teamScheduleToUpdate.getDate());
                personalSchedule.setStartTime(teamScheduleToUpdate.getStartTime());
                personalSchedule.setEndTime(teamScheduleToUpdate.getEndTime());
                personalSchedule.setTitle(teamScheduleToUpdate.getTitle());
                personalSchedule.setMemo(teamScheduleToUpdate.getMemo());
                personalSchedule.setColor(teamScheduleToUpdate.getColor());
                personalScheduleRepository.save(personalSchedule);
            }
        } else if (newAssignedMember != null) {
            // 기존 개인 스케줄이 없는데 새로 멤버가 할당된 경우 (예: teamScheduleId가 null이었다가 채워진 경우)
            PersonalSchedule personalSchedule = PersonalSchedule.builder()
                    .member(newAssignedMember)
                    .date(teamScheduleToUpdate.getDate())
                    .startTime(teamScheduleToUpdate.getStartTime())
                    .endTime(teamScheduleToUpdate.getEndTime())
                    .title(teamScheduleToUpdate.getTitle())
                    .memo(teamScheduleToUpdate.getMemo())
                    .color(teamScheduleToUpdate.getColor())
                    .teamScheduleId(teamScheduleToUpdate.getId())
                    .build();
            personalScheduleRepository.save(personalSchedule);
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

        teamScheduleRepository.delete(teamScheduleToDelete);
    }
}