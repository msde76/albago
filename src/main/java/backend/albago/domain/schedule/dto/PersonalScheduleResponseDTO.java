package backend.albago.domain.schedule.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.util.List;

public class PersonalScheduleResponseDTO {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CreatePersonalScheduleResult {
        private Long personalScheduleId;
        private Long memberId;
        private LocalDate date;
        private LocalTime startTime;
        private LocalTime endTime;
        private String title;
        private String memo;
        private String color;
        private LocalDateTime createdAt;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class PersonalScheduleInfo {
        private Long personalScheduleId;
        private Long memberId;
        private LocalDate date;
        private LocalTime startTime;
        private LocalTime endTime;
        private String title;
        private String memo;
        private String color;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class FindPersonalScheduleResult {
        private Long memberId;
        private List<PersonalScheduleInfo> personalSchedules;
        private Integer totalCount;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class UpdatePersonalScheduleResult {
        private Long personalScheduleId;
        private Long memberId;
        private LocalDate date;
        private LocalTime startTime;
        private LocalTime endTime;
        private String title;
        private String memo;
        private String color;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }
}