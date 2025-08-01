package backend.albago.domain.schedule.converter;

import backend.albago.domain.member.domain.entity.Member;
import backend.albago.domain.schedule.domain.entity.TeamSchedule;
import backend.albago.domain.schedule.dto.TeamScheduleRequestDTO;
import backend.albago.domain.schedule.dto.TeamScheduleResponseDTO;
import backend.albago.domain.team.domain.entity.Team;
import backend.albago.domain.team.domain.entity.TeamMember;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TeamScheduleConverter {

    // CreateTeamScheduleDTO를 TeamSchedule 엔티티로 변환
    public static TeamSchedule toTeamSchedule(
            Team team,
            Member member,
            TeamMember teamMemberInfo,
            TeamScheduleRequestDTO.CreateTeamScheduleDTO request) {

        // TeamMember의 급여 정보를 TeamSchedule에 복사
        BigDecimal scheduleHourlyWage = (teamMemberInfo != null) ? teamMemberInfo.getHourlyPay() : BigDecimal.ZERO;
        Boolean weeklyAllowance = (teamMemberInfo != null) ? teamMemberInfo.getWeeklyAllowance() : false;
        Boolean nightAllowance = (teamMemberInfo != null) ? teamMemberInfo.getNightAllowance() : false;
        BigDecimal nightRate = (teamMemberInfo != null) ? teamMemberInfo.getNightRate() : BigDecimal.ONE;
        Boolean overtimeAllowance = (teamMemberInfo != null) ? teamMemberInfo.getOvertimeAllowance() : false;
        BigDecimal overtimeRate = (teamMemberInfo != null) ? teamMemberInfo.getOvertimeRate() : BigDecimal.ONE;
        Boolean holidayAllowance = (teamMemberInfo != null) ? teamMemberInfo.getHolidayAllowance() : false;
        BigDecimal holidayRate = (teamMemberInfo != null) ? teamMemberInfo.getHolidayRate() : BigDecimal.ONE;
        String deductions = (teamMemberInfo != null) ? teamMemberInfo.getDeductions() : null;
        BigDecimal dailyPay = (teamMemberInfo != null) ? teamMemberInfo.getDailyPay() : BigDecimal.ZERO;
        BigDecimal hourlyPay = (teamMemberInfo != null) ? teamMemberInfo.getHourlyPay() : BigDecimal.ZERO;
        BigDecimal weeklyPay = (teamMemberInfo != null) ? teamMemberInfo.getWeeklyPay() : BigDecimal.ZERO;
        BigDecimal monthlyPay = (teamMemberInfo != null) ? teamMemberInfo.getMonthlyPay() : BigDecimal.ZERO;


        return TeamSchedule.builder()
                .team(team)
                .member(member) // 할당된 멤버
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .name(request.getName())
                .memo(request.getMemo())
                .color(request.getColor())
                // 급여 관련 필드 설정
                .scheduleHourlyWage(scheduleHourlyWage)
                .weeklyAllowance(weeklyAllowance)
                .nightAllowance(nightAllowance)
                .nightRate(nightRate)
                .overtimeAllowance(overtimeAllowance)
                .overtimeRate(overtimeRate)
                .holidayAllowance(holidayAllowance)
                .holidayRate(holidayRate)
                .deductions(deductions)
                .dailyPay(dailyPay)
                .hourlyPay(hourlyPay)
                .weeklyPay(weeklyPay)
                .monthlyPay(monthlyPay)
                .build();
    }

    // TeamSchedule 엔티티를 CreateTeamScheduleResult DTO로 변환
    public static TeamScheduleResponseDTO.CreateTeamScheduleResult toCreateTeamScheduleResult(TeamSchedule teamSchedule) {
        Long memberId = Optional.ofNullable(teamSchedule.getMember()).map(Member::getId).orElse(null);
        return TeamScheduleResponseDTO.CreateTeamScheduleResult.builder()
                .teamScheduleId(teamSchedule.getId())
                .teamId(teamSchedule.getTeam().getId())
                .memberId(memberId)
                .startTime(teamSchedule.getStartTime())
                .endTime(teamSchedule.getEndTime())
                .name(teamSchedule.getName())
                .memo(teamSchedule.getMemo())
                .color(teamSchedule.getColor())
                .createdAt(teamSchedule.getCreatedAt())
                // 급여 관련 필드 매핑
                .scheduleHourlyWage(teamSchedule.getScheduleHourlyWage())
                .weeklyAllowance(teamSchedule.getWeeklyAllowance())
                .nightAllowance(teamSchedule.getNightAllowance())
                .nightRate(teamSchedule.getNightRate())
                .overtimeAllowance(teamSchedule.getOvertimeAllowance())
                .overtimeRate(teamSchedule.getOvertimeRate())
                .holidayAllowance(teamSchedule.getHolidayAllowance())
                .holidayRate(teamSchedule.getHolidayRate())
                .deductions(teamSchedule.getDeductions())
                .dailyPay(teamSchedule.getDailyPay())
                .hourlyPay(teamSchedule.getHourlyPay())
                .weeklyPay(teamSchedule.getWeeklyPay())
                .monthlyPay(teamSchedule.getMonthlyPay())
                .build();
    }

    // TeamSchedule 엔티티를 TeamScheduleInfo DTO로 변환 (중복 제거 후 단일 버전)
    public static TeamScheduleResponseDTO.TeamScheduleInfo toTeamScheduleInfo(TeamSchedule teamSchedule) {
        Long memberId = Optional.ofNullable(teamSchedule.getMember()).map(Member::getId).orElse(null);
        String memberName = Optional.ofNullable(teamSchedule.getMember()).map(Member::getName).orElse(null);
        return TeamScheduleResponseDTO.TeamScheduleInfo.builder()
                .teamScheduleId(teamSchedule.getId())
                .teamId(teamSchedule.getTeam().getId())
                .memberId(memberId)
                .memberName(memberName)
                .startTime(teamSchedule.getStartTime())
                .endTime(teamSchedule.getEndTime())
                .name(teamSchedule.getName())
                .memo(teamSchedule.getMemo())
                .color(teamSchedule.getColor())
                .createdAt(teamSchedule.getCreatedAt())
                .updatedAt(teamSchedule.getUpdatedAt())
                // 급여 관련 필드 매핑
                .scheduleHourlyWage(teamSchedule.getScheduleHourlyWage())
                .weeklyAllowance(teamSchedule.getWeeklyAllowance())
                .nightAllowance(teamSchedule.getNightAllowance())
                .nightRate(teamSchedule.getNightRate())
                .overtimeAllowance(teamSchedule.getOvertimeAllowance())
                .overtimeRate(teamSchedule.getOvertimeRate())
                .holidayAllowance(teamSchedule.getHolidayAllowance())
                .holidayRate(teamSchedule.getHolidayRate())
                .deductions(teamSchedule.getDeductions())
                .dailyPay(teamSchedule.getDailyPay())
                .hourlyPay(teamSchedule.getHourlyPay())
                .weeklyPay(teamSchedule.getWeeklyPay())
                .monthlyPay(teamSchedule.getMonthlyPay())
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
                .startTime(teamSchedule.getStartTime())
                .endTime(teamSchedule.getEndTime())
                .name(teamSchedule.getName())
                .memo(teamSchedule.getMemo())
                .color(teamSchedule.getColor())
                .createdAt(teamSchedule.getCreatedAt())
                .updatedAt(teamSchedule.getUpdatedAt())
                // 급여 관련 필드 매핑
                .scheduleHourlyWage(teamSchedule.getScheduleHourlyWage())
                .weeklyAllowance(teamSchedule.getWeeklyAllowance())
                .nightAllowance(teamSchedule.getNightAllowance())
                .nightRate(teamSchedule.getNightRate())
                .overtimeAllowance(teamSchedule.getOvertimeAllowance())
                .overtimeRate(teamSchedule.getOvertimeRate())
                .holidayAllowance(teamSchedule.getHolidayAllowance())
                .holidayRate(teamSchedule.getHolidayRate())
                .deductions(teamSchedule.getDeductions())
                .dailyPay(teamSchedule.getDailyPay())
                .hourlyPay(teamSchedule.getHourlyPay())
                .weeklyPay(teamSchedule.getWeeklyPay())
                .monthlyPay(teamSchedule.getMonthlyPay())
                .build();
    }

    // TeamSchedule 리스트를 FindAllTeamScheduleResult DTO로 변환
    public static TeamScheduleResponseDTO.FindAllTeamScheduleResult toFindAllTeamScheduleResult(
            Long teamId, List<TeamSchedule> teamSchedules) {

        List<TeamScheduleResponseDTO.TeamScheduleInfo> scheduleInfoList = teamSchedules.stream()
                .map(TeamScheduleConverter::toTeamScheduleInfo)
                .collect(Collectors.toList());

        return TeamScheduleResponseDTO.FindAllTeamScheduleResult.builder()
                .teamId(teamId)
                .teamSchedules(scheduleInfoList)
                .totalCount(scheduleInfoList.size())
                .build();
    }
}