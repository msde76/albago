package backend.albago.domain.schedule.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PersonalScheduleRequestDTO {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CreatePersonalScheduleDTO {
        private String name;
        private String scheduleType;
        private Long teamId;
        private LocalDateTime startTime;
        private LocalDateTime endTime;
        private String memo;
        private String color;
        // 새로 추가된 급여 및 수당 관련 필드
        private BigDecimal hourlyWage;
        private Boolean weeklyAllowance;
        private Boolean nightAllowance;
        private BigDecimal nightRate;
        private Boolean overtimeAllowance;
        private BigDecimal overtimeRate;
        private Boolean holidayAllowance;
        private BigDecimal holidayRate;
        private String deductions;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class UpdatePersonalScheduleDTO {
        private String name;
        private String scheduleType;
        private Long teamId;
        private LocalDateTime startTime;
        private LocalDateTime endTime;
        private String memo;
        private String color;
        // 새로 추가된 급여 및 수당 관련 필드
        private BigDecimal hourlyWage;
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