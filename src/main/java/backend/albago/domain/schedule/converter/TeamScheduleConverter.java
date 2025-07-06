package backend.albago.domain.schedule.converter;

import backend.albago.domain.member.domain.entity.Member;
import backend.albago.domain.schedule.domain.entity.TeamSchedule;
import backend.albago.domain.schedule.dto.TeamScheduleRequestDTO;
import backend.albago.domain.schedule.dto.TeamScheduleResponseDTO;
import backend.albago.domain.team.domain.entity.Team;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TeamScheduleConverter {

    // CreateTeamScheduleDTO를 TeamSchedule 엔티티로 변환
    public static TeamSchedule toTeamSchedule(Team team, Member member, TeamScheduleRequestDTO.CreateTeamScheduleDTO request) {
        return TeamSchedule.builder()
                .team(team)
                .member(member)
                .date(request.getDate())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .title(request.getTitle())
                .memo(request.getMemo())
                .color(request.getColor())
                .build();
    }

    // TeamSchedule 엔티티를 CreateTeamScheduleResult DTO로 변환
    public static TeamScheduleResponseDTO.CreateTeamScheduleResult toCreateTeamScheduleResult(TeamSchedule teamSchedule) {
        Long memberId = Optional.ofNullable(teamSchedule.getMember()).map(Member::getId).orElse(null);
        return TeamScheduleResponseDTO.CreateTeamScheduleResult.builder()
                .teamScheduleId(teamSchedule.getId())
                .teamId(teamSchedule.getTeam().getId())
                .memberId(memberId)
                .date(teamSchedule.getDate())
                .startTime(teamSchedule.getStartTime())
                .endTime(teamSchedule.getEndTime())
                .title(teamSchedule.getTitle())
                .memo(teamSchedule.getMemo())
                .color(teamSchedule.getColor())
                .createdAt(teamSchedule.getCreatedAt())
                .build();
    }

    // TeamSchedule 엔티티를 TeamScheduleInfo DTO로 변환
    public static TeamScheduleResponseDTO.TeamScheduleInfo toTeamScheduleInfo(TeamSchedule teamSchedule) {
        Long memberId = Optional.ofNullable(teamSchedule.getMember()).map(Member::getId).orElse(null);
        String memberName = Optional.ofNullable(teamSchedule.getMember()).map(Member::getName).orElse(null);
        return TeamScheduleResponseDTO.TeamScheduleInfo.builder()
                .teamScheduleId(teamSchedule.getId())
                .teamId(teamSchedule.getTeam().getId())
                .memberId(memberId)
                .memberName(memberName) // 멤버 이름도 포함
                .date(teamSchedule.getDate())
                .startTime(teamSchedule.getStartTime())
                .endTime(teamSchedule.getEndTime())
                .title(teamSchedule.getTitle())
                .memo(teamSchedule.getMemo())
                .color(teamSchedule.getColor())
                .createdAt(teamSchedule.getCreatedAt())
                .updatedAt(teamSchedule.getUpdatedAt())
                .build();
    }

    // TeamSchedule 리스트를 FindTeamScheduleResult DTO로 변환
    public static TeamScheduleResponseDTO.FindTeamScheduleResult toFindTeamScheduleResult(
            Long teamId, List<TeamSchedule> teamSchedules) {

        List<TeamScheduleResponseDTO.TeamScheduleInfo> scheduleInfoList = teamSchedules.stream()
                .map(TeamScheduleConverter::toTeamScheduleInfo)
                .collect(Collectors.toList());

        return TeamScheduleResponseDTO.FindTeamScheduleResult.builder()
                .teamId(teamId)
                .teamSchedules(scheduleInfoList)
                .totalCount(scheduleInfoList.size())
                .build();
    }

    // TeamSchedule 엔티티를 UpdateTeamScheduleResult DTO로 변환
    public static TeamScheduleResponseDTO.UpdateTeamScheduleResult toUpdateTeamScheduleResult(TeamSchedule teamSchedule) {
        Long memberId = Optional.ofNullable(teamSchedule.getMember()).map(Member::getId).orElse(null);
        return TeamScheduleResponseDTO.UpdateTeamScheduleResult.builder()
                .teamScheduleId(teamSchedule.getId())
                .teamId(teamSchedule.getTeam().getId())
                .memberId(memberId)
                .date(teamSchedule.getDate())
                .startTime(teamSchedule.getStartTime())
                .endTime(teamSchedule.getEndTime())
                .title(teamSchedule.getTitle())
                .memo(teamSchedule.getMemo())
                .color(teamSchedule.getColor())
                .createdAt(teamSchedule.getCreatedAt())
                .updatedAt(teamSchedule.getUpdatedAt())
                .build();
    }
}
