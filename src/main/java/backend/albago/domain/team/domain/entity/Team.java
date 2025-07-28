package backend.albago.domain.team.domain.entity;

import backend.albago.domain.member.domain.entity.Member;
import backend.albago.domain.model.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

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
    @JoinColumn(name = "memberId", nullable = false)
    private Member ownerMember;

    private Double referenceLatitude;
    private Double referenceLongitude;

    private Double attendanceRadiusMeters;

    @Column(length = 100)
    private String locationName;
}