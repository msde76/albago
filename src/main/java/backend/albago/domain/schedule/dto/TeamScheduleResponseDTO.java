package backend.albago.domain.schedule.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
        private LocalDateTime startTime;
        private LocalDateTime endTime;
        private String name;
        private String memo;
        private String color;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        // TeamSchedule에 추가된 급여 관련 필드들
        private BigDecimal scheduleHourlyWage;
        private Boolean weeklyAllowance;
        private Boolean nightAllowance;
        private BigDecimal nightRate;
        private Boolean overtimeAllowance;
        private BigDecimal overtimeRate;
        private Boolean holidayAllowance;
        private BigDecimal holidayRate;
        private String deductions;
        private BigDecimal dailyPay;
        private BigDecimal hourlyPay;
        private BigDecimal weeklyPay;
        private BigDecimal monthlyPay;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CreateTeamScheduleResult {
        private Long teamScheduleId;
        private Long teamId;
        private Long memberId;
        private LocalDateTime startTime;
        private LocalDateTime endTime;
        private String name;
        private String memo;
        private String color;
        private LocalDateTime createdAt;

        // TeamSchedule에 추가된 급여 관련 필드들
        private BigDecimal scheduleHourlyWage;
        private Boolean weeklyAllowance;
        private Boolean nightAllowance;
        private BigDecimal nightRate;
        private Boolean overtimeAllowance;
        private BigDecimal overtimeRate;
        private Boolean holidayAllowance;
        private BigDecimal holidayRate;
        private String deductions;
        private BigDecimal dailyPay;
        private BigDecimal hourlyPay;
        private BigDecimal weeklyPay;
        private BigDecimal monthlyPay;
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
        private LocalDateTime startTime;
        private LocalDateTime endTime;
        private String name;
        private String memo;
        private String color;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        // TeamSchedule에 추가된 급여 관련 필드들
        private BigDecimal scheduleHourlyWage;
        private Boolean weeklyAllowance;
        private Boolean nightAllowance;
        private BigDecimal nightRate;
        private Boolean overtimeAllowance;
        private BigDecimal overtimeRate;
        private Boolean holidayAllowance;
        private BigDecimal holidayRate;
        private String deductions;
        private BigDecimal dailyPay;
        private BigDecimal hourlyPay;
        private BigDecimal weeklyPay;
        private BigDecimal monthlyPay;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class FindAllTeamScheduleResult {
        private Long teamId;
        private List<TeamScheduleInfo> teamSchedules;
        private Integer totalCount;
    }
}