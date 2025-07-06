package backend.albago.domain.schedule.converter;

import backend.albago.domain.member.domain.entity.Member;
import backend.albago.domain.schedule.domain.entity.PersonalSchedule;
import backend.albago.domain.schedule.dto.PersonalScheduleRequestDTO;
import backend.albago.domain.schedule.dto.PersonalScheduleResponseDTO;

import java.util.List;
import java.util.stream.Collectors;

public class PersonalScheduleConverter {

    // CreatePersonalScheduleDTO를 PersonalSchedule 엔티티로 변환
    public static PersonalSchedule toPersonalSchedule(Member member, PersonalScheduleRequestDTO.CreatePersonalScheduleDTO request) {
        return PersonalSchedule.builder()
                .member(member)
                .date(request.getDate())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .title(request.getTitle())
                .memo(request.getMemo())
                .color(request.getColor())
                .build();
    }

    // PersonalSchedule 엔티티를 CreatePersonalScheduleResult DTO로 변환
    public static PersonalScheduleResponseDTO.CreatePersonalScheduleResult toCreatePersonalScheduleResult(PersonalSchedule personalSchedule) {
        return PersonalScheduleResponseDTO.CreatePersonalScheduleResult.builder()
                .personalScheduleId(personalSchedule.getId())
                .memberId(personalSchedule.getMember().getId())
                .date(personalSchedule.getDate())
                .startTime(personalSchedule.getStartTime())
                .endTime(personalSchedule.getEndTime())
                .title(personalSchedule.getTitle())
                .memo(personalSchedule.getMemo())
                .color(personalSchedule.getColor())
                .createdAt(personalSchedule.getCreatedAt())
                .build();
    }

    // PersonalSchedule 엔티티를 PersonalScheduleInfo DTO로 변환
    public static PersonalScheduleResponseDTO.PersonalScheduleInfo toPersonalScheduleInfo(PersonalSchedule personalSchedule) {
        return PersonalScheduleResponseDTO.PersonalScheduleInfo.builder()
                .personalScheduleId(personalSchedule.getId())
                .memberId(personalSchedule.getMember().getId())
                .date(personalSchedule.getDate())
                .startTime(personalSchedule.getStartTime())
                .endTime(personalSchedule.getEndTime())
                .title(personalSchedule.getTitle())
                .memo(personalSchedule.getMemo())
                .color(personalSchedule.getColor())
                .createdAt(personalSchedule.getCreatedAt())
                .updatedAt(personalSchedule.getUpdatedAt()) // BaseEntity 상속 시
                .build();
    }

    // PersonalSchedule 리스트와 Member ID를 FindPersonalScheduleResult DTO로 변환
    public static PersonalScheduleResponseDTO.FindPersonalScheduleResult toFindPersonalScheduleResult(
            Long memberId, List<PersonalSchedule> personalSchedules) {

        List<PersonalScheduleResponseDTO.PersonalScheduleInfo> scheduleInfoList = personalSchedules.stream()
                .map(PersonalScheduleConverter::toPersonalScheduleInfo) // 각 엔티티를 Info DTO로 변환
                .collect(Collectors.toList());

        return PersonalScheduleResponseDTO.FindPersonalScheduleResult.builder()
                .memberId(memberId)
                .personalSchedules(scheduleInfoList)
                .totalCount(scheduleInfoList.size())
                .build();
    }

    // PersonalSchedule 엔티티를 UpdatePersonalScheduleResult DTO로 변환
    public static PersonalScheduleResponseDTO.UpdatePersonalScheduleResult toUpdatePersonalScheduleResult(PersonalSchedule personalSchedule) {
        return PersonalScheduleResponseDTO.UpdatePersonalScheduleResult.builder()
                .personalScheduleId(personalSchedule.getId())
                .memberId(personalSchedule.getMember().getId())
                .date(personalSchedule.getDate())
                .startTime(personalSchedule.getStartTime())
                .endTime(personalSchedule.getEndTime())
                .title(personalSchedule.getTitle())
                .memo(personalSchedule.getMemo())
                .color(personalSchedule.getColor())
                .createdAt(personalSchedule.getCreatedAt())
                .updatedAt(personalSchedule.getUpdatedAt())
                .build();
    }
}