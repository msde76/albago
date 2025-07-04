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
        private String color; // optional

        private BigDecimal dailyPay;
        private BigDecimal hourlyPay;
        private BigDecimal weeklyPay;
        private BigDecimal monthlyPay;

        private Boolean weeklyAllowance;
        private Boolean nightAllowance;
        private BigDecimal nightRate;
        private Boolean overtimeAllowance;
        private BigDecimal overtimeRate;
        private Boolean holidayAllowance;
        private BigDecimal holidayRate;
        private String deductions; // 4대 보험 같은 텍스트
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class TeamUpdateDTO {
        private String teamName;
        private String imageUrl;
        private String color;

        private BigDecimal dailyPay;
        private BigDecimal hourlyPay;
        private BigDecimal weeklyPay;
        private BigDecimal monthlyPay;

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
    public static class InviteMemberDTO {
        private String memberEmail;
        private PositionStatus position;
        private BigDecimal salary;
        private Double workHours;
        private Double breakHours;
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
    }
}
