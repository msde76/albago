package backend.albago.domain.team.dto;

import backend.albago.domain.model.enums.PositionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

public class TeamRequestDTO {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class TeamCreateDTO {
        private String teamName;
        private String imageUrl;
        private String color;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class TeamUpdateDTO {
        private String teamName;
        private String imageUrl;
        private String color;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class InviteMemberDTO {
        private String memberEmail;
        private PositionStatus position;
        private BigDecimal salary;
        private Double workHours;
        private Double breakHours;
        private String color;

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
    public static class UpdateTeamMemberDTO {
        private PositionStatus position;
        private BigDecimal salary;
        private Double workHours;
        private Double breakHours;
        private String color;

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
}
