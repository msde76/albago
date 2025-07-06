package backend.albago.domain.schedule.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class TeamScheduleResponseDTO {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class TeamScheduleInfo {
        private Long teamScheduleId;
        private Long teamId;
        private Long memberId;
        private String memberName;
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
    public static class CreateTeamScheduleResult {
        private Long teamScheduleId;
        private Long teamId;
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
    public static class FindTeamScheduleResult {
        private Long teamId;
        private List<TeamScheduleInfo> teamSchedules;
        private Integer totalCount;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class UpdateTeamScheduleResult {
        private Long teamScheduleId;
        private Long teamId;
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
