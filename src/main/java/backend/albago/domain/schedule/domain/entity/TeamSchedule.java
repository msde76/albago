package backend.albago.domain.schedule.domain.entity;

import backend.albago.domain.member.domain.entity.Member;
import backend.albago.domain.model.entity.BaseEntity;
import backend.albago.domain.team.domain.entity.Team;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@DynamicUpdate
@DynamicInsert
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamSchedule extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(nullable = false)
    private String name;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    private String memo;

    private String color;

    // (이 필드들은 TeamSchedule 생성 시 해당 TeamMember의 정보를 복사하여 저장합니다.)
    @Column(columnDefinition = "DECIMAL(10, 2) default 0.00")
    private BigDecimal scheduleHourlyWage = BigDecimal.ZERO;

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
    private String deductions;

    @Column(name = "pay_daily", columnDefinition = "DECIMAL(10, 2) default 0.00")
    private BigDecimal dailyPay = BigDecimal.ZERO;

    @Column(name = "pay_hourly", columnDefinition = "DECIMAL(10, 2) default 0.00")
    private BigDecimal hourlyPay = BigDecimal.ZERO;

    @Column(name = "pay_weekly", columnDefinition = "DECIMAL(10, 2) default 0.00")
    private BigDecimal weeklyPay = BigDecimal.ZERO;

    @Column(name = "pay_monthly", columnDefinition = "DECIMAL(10, 2) default 0.00")
    private BigDecimal monthlyPay = BigDecimal.ZERO;
}