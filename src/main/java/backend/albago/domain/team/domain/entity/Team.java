package backend.albago.domain.team.domain.entity;

import backend.albago.domain.member.domain.entity.Member;
import backend.albago.domain.model.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@DynamicUpdate
@DynamicInsert
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Team extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String teamName;

    @Column(length = 1000)
    private String imageUrl;

    @Column(nullable = false, length = 100)
    private String color;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member ownerMember;

    private boolean weeklyAllowance;

    private boolean nightAllowance;
    @Column(columnDefinition = "DECIMAL(5, 2) default 1.00")
    private BigDecimal nightRate = BigDecimal.ONE;

    private boolean overtimeAllowance;
    @Column(columnDefinition = "DECIMAL(5, 2) default 1.00")
    private BigDecimal overtimeRate = BigDecimal.ONE;

    private boolean holidayAllowance;
    @Column(columnDefinition = "DECIMAL(5, 2) default 1.00")
    private BigDecimal holidayRate = BigDecimal.ONE;

    @Column(length = 255)
    private String deductions;

    // 임금 지불 방식 (기존 컬럼들 유지)
    @Column(name = "pay_daily", columnDefinition = "DECIMAL(10, 2) default 0.00")
    private BigDecimal dailyPay = BigDecimal.ZERO;

    @Column(name = "pay_hourly", columnDefinition = "DECIMAL(10, 2) default 0.00")
    private BigDecimal hourlyPay = BigDecimal.ZERO;

    @Column(name = "pay_weekly", columnDefinition = "DECIMAL(10, 2) default 0.00")
    private BigDecimal weeklyPay = BigDecimal.ZERO;

    @Column(name = "pay_monthly", columnDefinition = "DECIMAL(10, 2) default 0.00")
    private BigDecimal monthlyPay = BigDecimal.ZERO;
}