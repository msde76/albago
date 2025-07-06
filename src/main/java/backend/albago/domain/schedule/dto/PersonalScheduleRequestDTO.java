package backend.albago.domain.schedule.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

public class PersonalScheduleRequestDTO {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CreatePersonalScheduleDTO {
        private LocalDate date;
        private LocalTime startTime;
        private LocalTime endTime;
        private String title;
        private String memo;
        private String color;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class UpdatePersonalScheduleDTO {
        private LocalDate date;
        private LocalTime startTime;
        private LocalTime endTime;
        private String title;
        private String memo;
        private String color;
    }
}
