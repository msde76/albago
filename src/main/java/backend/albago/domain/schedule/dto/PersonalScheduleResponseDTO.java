package backend.albago.domain.schedule.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
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
        private String name;
        private String scheduleType;
        private Long teamId;
        private LocalDateTime startTime;
        private LocalDateTime endTime;
        private String memo;
        private String color;
        // 추가된 급여 및 수당 관련 필드
        private BigDecimal hourlyWage;
        private Boolean weeklyAllowance;
        private Boolean nightAllowance;
        private BigDecimal nightRate;
        private Boolean overtimeAllowance;
        private BigDecimal overtimeRate;
        private Boolean holidayAllowance;
        private BigDecimal holidayRate;
        private String deductions;
        private LocalDateTime createdAt;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class PersonalScheduleInfo {
        private Long personalScheduleId;
        private Long memberId;
        private String name;
        private String scheduleType;
        private Long teamId;
        private LocalDateTime startTime;
        private LocalDateTime endTime;
        private String memo;
        private String color;
        // 추가된 급여 및 수당 관련 필드
        private BigDecimal hourlyWage;
        private Boolean weeklyAllowance;
        private Boolean nightAllowance;
        private BigDecimal nightRate;
        private Boolean overtimeAllowance;
        private BigDecimal overtimeRate;
        private Boolean holidayAllowance;
        private BigDecimal holidayRate;
        private String deductions;
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
        private String name;
        private String scheduleType;
        private Long teamId;
        private LocalDateTime startTime;
        private LocalDateTime endTime;
        private String memo;
        private String color;
        // 추가된 급여 및 수당 관련 필드
        private BigDecimal hourlyWage;
        private Boolean weeklyAllowance;
        private Boolean nightAllowance;
        private BigDecimal nightRate;
        private Boolean overtimeAllowance;
        private BigDecimal overtimeRate;
        private Boolean holidayAllowance;
        private BigDecimal holidayRate;
        private String deductions;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class FindAllPersonalScheduleResult {
        private Long memberId;
        private List<PersonalScheduleInfo> personalSchedules;
        private Integer totalCount;
    }
}