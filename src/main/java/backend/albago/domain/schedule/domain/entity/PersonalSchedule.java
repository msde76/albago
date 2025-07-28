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
public class PersonalSchedule extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String scheduleType; // "personal" 또는 "team"

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    private String memo;

    private String color;

    @Column(columnDefinition = "DECIMAL(10, 2) default 0.00")
    private BigDecimal hourlyWage = BigDecimal.ZERO; // "hourlyWage" 필드 추가

    private Boolean weeklyAllowance; // "weeklyAllowance" 필드 추가

    private Boolean nightAllowance; // "nightAllowance" 필드 추가
    @Column(columnDefinition = "DECIMAL(5, 2) default 0.00")
    private BigDecimal nightRate = BigDecimal.ZERO; // "nightRate" 필드 추가

    private Boolean overtimeAllowance; // "overtimeAllowance" 필드 추가
    @Column(columnDefinition = "DECIMAL(5, 2) default 0.00")
    private BigDecimal overtimeRate = BigDecimal.ZERO; // "overtimeRate" 필드 추가

    private Boolean holidayAllowance; // "holidayAllowance" 필드 추가
    @Column(columnDefinition = "DECIMAL(5, 2) default 0.00")
    private BigDecimal holidayRate = BigDecimal.ZERO; // "holidayRate" 필드 추가

    @Column(length = 255)
    private String deductions; // "deductions" 필드 추가

    @Column(name = "related_team_schedule_id")
    private Long relatedTeamScheduleId;
}