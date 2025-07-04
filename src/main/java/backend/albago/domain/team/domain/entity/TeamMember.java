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
    @JoinColumn(name = "user_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", nullable = false)
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
}
