package backend.albago.domain.team.converter;

import backend.albago.domain.member.domain.entity.Member;
import backend.albago.domain.model.enums.PositionStatus;
import backend.albago.domain.team.domain.entity.Team;
import backend.albago.domain.team.domain.entity.TeamMember;
import backend.albago.domain.team.dto.TeamRequestDTO;
import backend.albago.domain.team.dto.TeamResponseDTO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TeamConverter {

    public static Team toTeam(TeamRequestDTO.TeamCreateDTO request, Member ownerMember) {
        return Team.builder()
                .teamName(request.getTeamName())
                .color(request.getColor())
                .imageUrl(request.getImageUrl())
                .ownerMember(ownerMember)
                .build();
    }

    // Team과 Member를 받아서 TeamMember 엔티티로 변환
    public static TeamMember toTeamMember(Team team, Member member, PositionStatus position, BigDecimal salary) {
        return TeamMember.builder()
                .team(team)
                .member(member)
                .position(position)
                .salary(salary)
                .workHours(0.0)
                .breakHours(0.0)
                .isAccepted(true) // 팀 생성자는 즉시 수락 상태
                // 오너 멤버는 팀 생성 시 TeamMember의 기본 급여 정책을 따라가도록 설정
                .weeklyAllowance(false)
                .nightAllowance(false)
                .nightRate(BigDecimal.ONE)
                .overtimeAllowance(false)
                .overtimeRate(BigDecimal.ONE)
                .holidayAllowance(false)
                .holidayRate(BigDecimal.ONE)
                .deductions(null)
                .dailyPay(BigDecimal.ZERO)
                .hourlyPay(BigDecimal.ZERO)
                .weeklyPay(BigDecimal.ZERO)
                .monthlyPay(BigDecimal.ZERO)
                .build();
    }

    // Team 엔티티를 TeamResponseDTO.TeamCreateResult로 변환
    public static TeamResponseDTO.TeamCreateResult toTeamCreateResult(Team team) {
        return TeamResponseDTO.TeamCreateResult.builder()
                .teamId(team.getId())
                .teamName(team.getTeamName())
                .color(team.getColor())
                .ownerMemberId(team.getOwnerMember().getId())
                .createdAt(team.getCreatedAt())
                .build();
    }

    // TeamMember 엔티티를 TeamResponseDTO.TeamListResult로 변환
    public static TeamResponseDTO.TeamListResult toTeamListResult(TeamMember teamMember) {
        Team team = teamMember.getTeam();
        return TeamResponseDTO.TeamListResult.builder()
                .teamId(team.getId())
                .teamName(team.getTeamName())
                .imageUrl(team.getImageUrl())
                .color(team.getColor())
                .ownerMemberId(team.getOwnerMember().getId())
                .myPosition(teamMember.getPosition())
                .isAccepted(teamMember.getIsAccepted())
                .lastUpdatedAt(teamMember.getUpdatedAt())
                .build();
    }

    // List<TeamMember>를 List<TeamResponseDTO.TeamListResult>로 변환
    public static List<TeamResponseDTO.TeamListResult> toTeamListResultList(List<TeamMember> teamMembers) {
        return teamMembers.stream()
                .map(TeamConverter::toTeamListResult)
                .collect(Collectors.toList());
    }

    // TeamMember 엔티티를 TeamResponseDTO.TeamMemberInfo로 변환
    public static TeamResponseDTO.TeamMemberInfo toTeamMemberInfo(TeamMember teamMember) {
        return TeamResponseDTO.TeamMemberInfo.builder()
                .teamMemberId(teamMember.getId())
                .memberId(teamMember.getMember().getId())
                .name(teamMember.getMember().getName())
                .position(teamMember.getPosition())
                .salary(teamMember.getSalary())
                .isAccepted(teamMember.getIsAccepted())
                .color(teamMember.getColor())

                .weeklyAllowance(teamMember.getWeeklyAllowance())
                .nightAllowance(teamMember.getNightAllowance())
                .nightRate(teamMember.getNightRate())
                .overtimeAllowance(teamMember.getOvertimeAllowance())
                .overtimeRate(teamMember.getOvertimeRate())
                .holidayAllowance(teamMember.getHolidayAllowance())
                .holidayRate(teamMember.getHolidayRate())
                .deductions(teamMember.getDeductions())
                .dailyPay(teamMember.getDailyPay())
                .hourlyPay(teamMember.getHourlyPay())
                .weeklyPay(teamMember.getWeeklyPay())
                .monthlyPay(teamMember.getMonthlyPay())
                .build();
    }

    // Team 엔티티와 List<TeamMember>를 TeamResponseDTO.TeamResult로 변환
    public static TeamResponseDTO.TeamResult toTeamResult(Team team, List<TeamMember> teamMembers) {
        List<TeamResponseDTO.TeamMemberInfo> memberInfos = teamMembers.stream()
                .map(TeamConverter::toTeamMemberInfo)
                .collect(Collectors.toList());

        return TeamResponseDTO.TeamResult.builder()
                .id(team.getId())
                .teamName(team.getTeamName())
                .imageUrl(team.getImageUrl())
                .color(team.getColor())
                .ownerMemberId(team.getOwnerMember().getId())
                .members(memberInfos)
                .createdAt(team.getCreatedAt())
                .updatedAt(team.getUpdatedAt())
                .build();
    }

    // InviteMemberDTO를 받아 TeamMember 엔티티로 변환
    public static TeamMember toTeamMember(Team team, Member member, TeamRequestDTO.InviteMemberDTO inviteRequest) {
        return TeamMember.builder()
                .team(team)
                .member(member)
                .position(inviteRequest.getPosition())
                .salary(inviteRequest.getSalary() != null ? inviteRequest.getSalary() : BigDecimal.ZERO)
                .workHours(inviteRequest.getWorkHours() != null ? inviteRequest.getWorkHours() : 0.0)
                .breakHours(inviteRequest.getBreakHours() != null ? inviteRequest.getBreakHours() : 0.0)
                .isAccepted(false) // 초대 시에는 기본적으로 미수락 상태
                .color(inviteRequest.getColor())
                // TeamMember로 이동된 급여 관련 필드 매핑
                .weeklyAllowance(Optional.ofNullable(inviteRequest.getWeeklyAllowance()).orElse(false))
                .nightAllowance(Optional.ofNullable(inviteRequest.getNightAllowance()).orElse(false))
                .nightRate(Optional.ofNullable(inviteRequest.getNightRate()).orElse(BigDecimal.ONE))
                .overtimeAllowance(Optional.ofNullable(inviteRequest.getOvertimeAllowance()).orElse(false))
                .overtimeRate(Optional.ofNullable(inviteRequest.getOvertimeRate()).orElse(BigDecimal.ONE))
                .holidayAllowance(Optional.ofNullable(inviteRequest.getHolidayAllowance()).orElse(false))
                .holidayRate(Optional.ofNullable(inviteRequest.getHolidayRate()).orElse(BigDecimal.ONE))
                .deductions(inviteRequest.getDeductions())
                .dailyPay(Optional.ofNullable(inviteRequest.getDailyPay()).orElse(BigDecimal.ZERO))
                .hourlyPay(Optional.ofNullable(inviteRequest.getHourlyPay()).orElse(BigDecimal.ZERO))
                .weeklyPay(Optional.ofNullable(inviteRequest.getWeeklyPay()).orElse(BigDecimal.ZERO))
                .monthlyPay(Optional.ofNullable(inviteRequest.getMonthlyPay()).orElse(BigDecimal.ZERO))
                .build();
    }

    // TeamMember 엔티티와 관련 정보를 TeamResponseDTO.TeamInviteResult로 변환 (기존 코드와 동일)
    public static TeamResponseDTO.TeamInviteResult toTeamInviteResult(TeamMember teamMember) {
        return TeamResponseDTO.TeamInviteResult.builder()
                .teamMemberId(teamMember.getId())
                .invitedMemberId(teamMember.getMember().getId())
                .invitedMemberEmail(teamMember.getMember().getEmail())
                .teamId(teamMember.getTeam().getId())
                .teamName(teamMember.getTeam().getTeamName())
                .position(teamMember.getPosition())
                .salary(teamMember.getSalary())
                .isAccepted(teamMember.getIsAccepted())
                .invitedAt(teamMember.getCreatedAt())

                .weeklyAllowance(teamMember.getWeeklyAllowance())
                .nightAllowance(teamMember.getNightAllowance())
                .nightRate(teamMember.getNightRate())
                .overtimeAllowance(teamMember.getOvertimeAllowance())
                .overtimeRate(teamMember.getOvertimeRate())
                .holidayAllowance(teamMember.getHolidayAllowance())
                .holidayRate(teamMember.getHolidayRate())
                .deductions(teamMember.getDeductions())
                .dailyPay(teamMember.getDailyPay())
                .hourlyPay(teamMember.getHourlyPay())
                .weeklyPay(teamMember.getWeeklyPay())
                .monthlyPay(teamMember.getMonthlyPay())
                .build();
    }

    // TeamMember 엔티티와 관련 정보를 TeamResponseDTO.UpdateTeamMemberResult로 변환
    public static TeamResponseDTO.UpdateTeamMemberResult toUpdateTeamMemberResult(TeamMember teamMember) {
        return TeamResponseDTO.UpdateTeamMemberResult.builder()
                .teamMemberId(teamMember.getId())
                .memberId(teamMember.getMember().getId())
                .memberName(teamMember.getMember().getName())
                .memberEmail(teamMember.getMember().getEmail())
                .teamId(teamMember.getTeam().getId())
                .teamName(teamMember.getTeam().getTeamName())
                .position(teamMember.getPosition())
                .salary(teamMember.getSalary())
                .workHours(teamMember.getWorkHours())
                .breakHours(teamMember.getBreakHours())
                .isAccepted(teamMember.getIsAccepted())
                .color(teamMember.getColor())
                .updatedAt(teamMember.getUpdatedAt())

                .weeklyAllowance(teamMember.getWeeklyAllowance())
                .nightAllowance(teamMember.getNightAllowance())
                .nightRate(teamMember.getNightRate())
                .overtimeAllowance(teamMember.getOvertimeAllowance())
                .overtimeRate(teamMember.getOvertimeRate())
                .holidayAllowance(teamMember.getHolidayAllowance())
                .holidayRate(teamMember.getHolidayRate())
                .deductions(teamMember.getDeductions())
                .dailyPay(teamMember.getDailyPay())
                .hourlyPay(teamMember.getHourlyPay())
                .weeklyPay(teamMember.getWeeklyPay())
                .monthlyPay(teamMember.getMonthlyPay())
                .build();
    }

    public static TeamResponseDTO.TeamInviteAcceptResult toTeamInviteAcceptResult(TeamMember teamMember) {
        return TeamResponseDTO.TeamInviteAcceptResult.builder()
                .teamMemberId(teamMember.getId())
                .memberId(teamMember.getMember().getId())
                .memberName(teamMember.getMember().getName())
                .memberEmail(teamMember.getMember().getEmail())
                .teamId(teamMember.getTeam().getId())
                .teamName(teamMember.getTeam().getTeamName())
                .position(teamMember.getPosition())
                .salary(teamMember.getSalary())
                .workHours(teamMember.getWorkHours())
                .breakHours(teamMember.getBreakHours())
                .isAccepted(teamMember.getIsAccepted()) // 이 필드가 true여야 함
                .color(teamMember.getColor())
                .acceptedAt(teamMember.getUpdatedAt())

                .weeklyAllowance(teamMember.getWeeklyAllowance())
                .nightAllowance(teamMember.getNightAllowance())
                .nightRate(teamMember.getNightRate())
                .overtimeAllowance(teamMember.getOvertimeAllowance())
                .overtimeRate(teamMember.getOvertimeRate())
                .holidayAllowance(teamMember.getHolidayAllowance())
                .holidayRate(teamMember.getHolidayRate())
                .deductions(teamMember.getDeductions())
                .dailyPay(teamMember.getDailyPay())
                .hourlyPay(teamMember.getHourlyPay())
                .weeklyPay(teamMember.getWeeklyPay())
                .monthlyPay(teamMember.getMonthlyPay())
                .build();
    }
}
