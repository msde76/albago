package backend.albago.domain.team.dto;

import backend.albago.domain.model.enums.PositionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class TeamResponseDTO {

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TeamCreateResult {
        private Long teamId;
        private String teamName;
        private String color;
        private Long ownerMemberId;
        private LocalDateTime createdAt;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TeamListResult {
        private Long teamId;
        private String teamName;
        private String imageUrl;
        private String color;
        private Long ownerMemberId;
        private PositionStatus myPosition;
        private Boolean isAccepted;
        private LocalDateTime lastUpdatedAt;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class TeamResult {
        private Long id;
        private String teamName;
        private String imageUrl;
        private String color;
        private Long ownerMemberId;

        // 팀 멤버 목록 (간략 정보)
        private List<TeamMemberInfo> members;

        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }

    // TeamResult 내부에 포함될 팀 멤버의 간략 정보 DTO
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class TeamMemberInfo {
        private Long teamMemberId;
        private Long memberId;
        private String name;
        private PositionStatus position;
        private BigDecimal salary;
        private Boolean isAccepted;
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

    // 팀 멤버 초대 결과 응답 DTO
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class TeamInviteResult {
        private Long teamMemberId;
        private Long invitedMemberId;
        private String invitedMemberEmail;
        private Long teamId;
        private String teamName;
        private PositionStatus position;
        private BigDecimal salary;
        private Boolean isAccepted;
        private LocalDateTime invitedAt;

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

    // 팀 멤버 수정 결과 응답 DTO
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class UpdateTeamMemberResult {
        private Long teamMemberId;
        private Long memberId;
        private String memberName;
        private String memberEmail;
        private Long teamId;
        private String teamName;
        private PositionStatus position;
        private BigDecimal salary;
        private Double workHours;
        private Double breakHours;
        private Boolean isAccepted;
        private String color;
        private LocalDateTime updatedAt;

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

    // 팀 초대 수락 결과 응답 DTO
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class TeamInviteAcceptResult {
        private Long teamMemberId;
        private Long memberId;
        private String memberName;
        private String memberEmail;
        private Long teamId;
        private String teamName;
        private PositionStatus position;
        private BigDecimal salary;
        private Double workHours;
        private Double breakHours;
        private Boolean isAccepted;
        private String color;
        private LocalDateTime acceptedAt;

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
