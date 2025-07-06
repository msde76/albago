package backend.albago.domain.schedule.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

public class TeamScheduleRequestDTO {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CreateTeamScheduleDTO {
        private Long memberId;
        private LocalDate date;
        private LocalTime startTime;
        private LocalTime endTime;
        private String title;
        private String memo;
        private String color;
    }

    // 팀 스케줄 수정 요청 DTO
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class UpdateTeamScheduleDTO {
        private Long memberId;
        private LocalDate date;
        private LocalTime startTime;
        private LocalTime endTime;
        private String title;
        private String memo;
        private String color;
    }
}
