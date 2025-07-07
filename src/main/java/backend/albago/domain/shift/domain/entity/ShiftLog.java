package backend.albago.domain.shift.domain.entity;

import backend.albago.domain.member.domain.entity.Member;
import backend.albago.domain.model.entity.BaseEntity;
import backend.albago.domain.team.domain.entity.Team;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@DynamicUpdate
@DynamicInsert
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShiftLog extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;

    @Column(nullable = false)
    private LocalDate shiftDate;

    private LocalDateTime clockInTime;

    private LocalDateTime clockOutTime;

    private Double recordedClockInLatitude;
    private Double recordedClockInLongitude;
    private Double recordedClockOutLatitude;
    private Double recordedClockOutLongitude;

    @Column(length = 100)
    private String payLocation;

    public void clockOut(LocalDateTime clockOutTime, String payLocation) {
        this.clockOutTime = clockOutTime;
        this.payLocation = payLocation;
    }
}
