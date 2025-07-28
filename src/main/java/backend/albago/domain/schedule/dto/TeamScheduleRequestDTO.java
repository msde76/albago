package backend.albago.domain.schedule.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TeamScheduleRequestDTO {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CreateTeamScheduleDTO {
        private Long memberId;
        private LocalDateTime startTime;
        private LocalDateTime endTime;
        private String name;
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
        private LocalDateTime startTime;
        private LocalDateTime endTime;
        private String name;
        private String memo;
        private String color;

        private BigDecimal scheduleHourlyWage;
        private Boolean weeklyAllowance;
        private Boolean nightAllowance;
        private BigDecimal nightRate;
        private Boolean overtimeAllowance;
        private BigDecimal overtimeRate;
        private Boolean holidayAllowance;
        private BigDecimal holidayRate;
        private String deductions;
    }
}
