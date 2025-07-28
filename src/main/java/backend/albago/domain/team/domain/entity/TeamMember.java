package backend.albago.domain.team.domain.entity;

import backend.albago.domain.member.domain.entity.Member;
import backend.albago.domain.model.entity.BaseEntity;
import backend.albago.domain.model.enums.PositionStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.math.BigDecimal;

@Entity
@Getter @Setter
@DynamicUpdate
@DynamicInsert
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamMember extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teamId", nullable = false)
    private Team team;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private PositionStatus position;

    @Column(nullable = false, columnDefinition = "DECIMAL(10, 2) default 0.00")
    private BigDecimal salary = BigDecimal.ZERO;

    @Column(nullable = false)
    private Double workHours = 0.0;

    @Column(nullable = false)
    private Double breakHours = 0.0;

    @Column(nullable = false)
    private Boolean isAccepted = false;

    @Column(length = 7)
    private String color;

    // --- Team 엔티티에서 이동된 급여 관련 필드 ---
    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean weeklyAllowance = false;

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean nightAllowance = false;
    @Column(columnDefinition = "DECIMAL(5, 2) default 1.00")
    private BigDecimal nightRate = BigDecimal.ONE;

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean overtimeAllowance = false;
    @Column(columnDefinition = "DECIMAL(5, 2) default 1.00")
    private BigDecimal overtimeRate = BigDecimal.ONE;

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean holidayAllowance = false;
    @Column(columnDefinition = "DECIMAL(5, 2) default 1.00")
    private BigDecimal holidayRate = BigDecimal.ONE;

    @Column(length = 255)
    private String deductions; // null 허용 (기본값 설정 불필요)

    @Column(name = "pay_daily", columnDefinition = "DECIMAL(10, 2) default 0.00")
    private BigDecimal dailyPay = BigDecimal.ZERO;

    @Column(name = "pay_hourly", columnDefinition = "DECIMAL(10, 2) default 0.00")
    private BigDecimal hourlyPay = BigDecimal.ZERO;

    @Column(name = "pay_weekly", columnDefinition = "DECIMAL(10, 2) default 0.00")
    private BigDecimal weeklyPay = BigDecimal.ZERO;

    @Column(name = "pay_monthly", columnDefinition = "DECIMAL(10, 2) default 0.00")
    private BigDecimal monthlyPay = BigDecimal.ZERO;
}