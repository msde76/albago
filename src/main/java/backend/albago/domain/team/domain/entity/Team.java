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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(name = "pay_daily", columnDefinition = "DECIMAL(10, 2) default 0.00")
    private BigDecimal dailyPay = BigDecimal.ZERO;

    @Column(name = "pay_hourly", columnDefinition = "DECIMAL(10, 2) default 0.00")
    private BigDecimal hourlyPay = BigDecimal.ZERO;

    @Column(name = "pay_weekly", columnDefinition = "DECIMAL(10, 2) default 0.00")
    private BigDecimal weeklyPay = BigDecimal.ZERO;

    @Column(name = "pay_monthly", columnDefinition = "DECIMAL(10, 2) default 0.00")
    private BigDecimal monthlyPay = BigDecimal.ZERO;
}
