package backend.albago.domain.schedule.converter;

import backend.albago.domain.member.domain.entity.Member;
import backend.albago.domain.schedule.domain.entity.PersonalSchedule;
import backend.albago.domain.schedule.dto.PersonalScheduleRequestDTO;
import backend.albago.domain.schedule.dto.PersonalScheduleResponseDTO;
import backend.albago.domain.team.domain.entity.Team;

import java.util.List;
import java.util.stream.Collectors;

public class PersonalScheduleConverter {

    public static PersonalSchedule toPersonalSchedule(Member member, Team team, PersonalScheduleRequestDTO.CreatePersonalScheduleDTO request) {
        return PersonalSchedule.builder()
                .member(member)
                .team(team)
                .name(request.getName())
                .scheduleType(request.getScheduleType())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .memo(request.getMemo())
                .color(request.getColor())
                .hourlyWage(request.getHourlyWage())
                .weeklyAllowance(request.getWeeklyAllowance())
                .nightAllowance(request.getNightAllowance())
                .nightRate(request.getNightRate())
                .overtimeAllowance(request.getOvertimeAllowance())
                .overtimeRate(request.getOvertimeRate())
                .holidayAllowance(request.getHolidayAllowance())
                .holidayRate(request.getHolidayRate())
                .deductions(request.getDeductions())
                .build();
    }

    public static PersonalScheduleResponseDTO.CreatePersonalScheduleResult toCreatePersonalScheduleResult(PersonalSchedule personalSchedule) {
        return PersonalScheduleResponseDTO.CreatePersonalScheduleResult.builder()
                .personalScheduleId(personalSchedule.getId())
                .memberId(personalSchedule.getMember().getId())
                .name(personalSchedule.getName())
                .scheduleType(personalSchedule.getScheduleType())
                .teamId(personalSchedule.getTeam().getId())
                .startTime(personalSchedule.getStartTime())
                .endTime(personalSchedule.getEndTime())
                .memo(personalSchedule.getMemo())
                .color(personalSchedule.getColor())
                .hourlyWage(personalSchedule.getHourlyWage())
                .weeklyAllowance(personalSchedule.getWeeklyAllowance())
                .nightAllowance(personalSchedule.getNightAllowance())
                .nightRate(personalSchedule.getNightRate())
                .overtimeAllowance(personalSchedule.getOvertimeAllowance())
                .overtimeRate(personalSchedule.getOvertimeRate())
                .holidayAllowance(personalSchedule.getHolidayAllowance())
                .holidayRate(personalSchedule.getHolidayRate())
                .deductions(personalSchedule.getDeductions())
                .createdAt(personalSchedule.getCreatedAt())
                .build();
    }

    public static PersonalScheduleResponseDTO.PersonalScheduleInfo toPersonalScheduleInfo(PersonalSchedule personalSchedule) {
        return PersonalScheduleResponseDTO.PersonalScheduleInfo.builder()
                .personalScheduleId(personalSchedule.getId())
                .memberId(personalSchedule.getMember().getId())
                .name(personalSchedule.getName())
                .scheduleType(personalSchedule.getScheduleType())
                .teamId(personalSchedule.getTeam().getId())
                .startTime(personalSchedule.getStartTime())
                .endTime(personalSchedule.getEndTime())
                .memo(personalSchedule.getMemo())
                .color(personalSchedule.getColor())
                .hourlyWage(personalSchedule.getHourlyWage())
                .weeklyAllowance(personalSchedule.getWeeklyAllowance())
                .nightAllowance(personalSchedule.getNightAllowance())
                .nightRate(personalSchedule.getNightRate())
                .overtimeAllowance(personalSchedule.getOvertimeAllowance())
                .overtimeRate(personalSchedule.getOvertimeRate())
                .holidayAllowance(personalSchedule.getHolidayAllowance())
                .holidayRate(personalSchedule.getHolidayRate())
                .deductions(personalSchedule.getDeductions())
                .createdAt(personalSchedule.getCreatedAt())
                .updatedAt(personalSchedule.getUpdatedAt())
                .build();
    }

    public static PersonalScheduleResponseDTO.FindPersonalScheduleResult toFindPersonalScheduleResult(
            Long memberId, List<PersonalSchedule> personalSchedules) {

        List<PersonalScheduleResponseDTO.PersonalScheduleInfo> scheduleInfoList = personalSchedules.stream()
                .map(PersonalScheduleConverter::toPersonalScheduleInfo)
                .collect(Collectors.toList());

        return PersonalScheduleResponseDTO.FindPersonalScheduleResult.builder()
                .memberId(memberId)
                .personalSchedules(scheduleInfoList)
                .totalCount(scheduleInfoList.size())
                .build();
    }

    public static PersonalScheduleResponseDTO.UpdatePersonalScheduleResult toUpdatePersonalScheduleResult(PersonalSchedule personalSchedule) {
        return PersonalScheduleResponseDTO.UpdatePersonalScheduleResult.builder()
                .personalScheduleId(personalSchedule.getId())
                .memberId(personalSchedule.getMember().getId())
                .name(personalSchedule.getName())
                .scheduleType(personalSchedule.getScheduleType())
                .teamId(personalSchedule.getTeam().getId())
                .startTime(personalSchedule.getStartTime())
                .endTime(personalSchedule.getEndTime())
                .memo(personalSchedule.getMemo())
                .color(personalSchedule.getColor())
                .hourlyWage(personalSchedule.getHourlyWage())
                .weeklyAllowance(personalSchedule.getWeeklyAllowance())
                .nightAllowance(personalSchedule.getNightAllowance())
                .nightRate(personalSchedule.getNightRate())
                .overtimeAllowance(personalSchedule.getOvertimeAllowance())
                .overtimeRate(personalSchedule.getOvertimeRate())
                .holidayAllowance(personalSchedule.getHolidayAllowance())
                .holidayRate(personalSchedule.getHolidayRate())
                .deductions(personalSchedule.getDeductions())
                .createdAt(personalSchedule.getCreatedAt())
                .updatedAt(personalSchedule.getUpdatedAt())
                .build();
    }
}