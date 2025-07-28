package backend.albago.domain.substitution.domain.repository;

import backend.albago.domain.member.domain.entity.Member;
import backend.albago.domain.substitution.domain.entity.SubstitutionRequest;
import backend.albago.domain.team.domain.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface SubstitutionRequestRepository extends JpaRepository<SubstitutionRequest, Long> {

    List<SubstitutionRequest> findByTeamAndRequesterOrderByCreatedAtDesc(Team team, Member requester);

    List<SubstitutionRequest> findByTeamAndSubstituteOrderByCreatedAtDesc(Team team, Member substitute);

    Optional<SubstitutionRequest> findByTeamAndId(Team team, Long id);

    // 주어진 시간 범위와 겹치고 PENDING 상태인 대타 요청 조회
    @Query("SELECT sr FROM SubstitutionRequest sr WHERE sr.team = :team " +
            "AND (sr.requester = :member OR sr.substitute = :member) " +
            "AND sr.status = 'PENDING' " +
            "AND ((sr.timeRangeStart < :endTime AND sr.timeRangeEnd > :startTime))")
    List<SubstitutionRequest> findPendingOverlappingRequestsForMember(
            @Param("team") Team team,
            @Param("member") Member member,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime
    );

    // 주어진 시간 범위와 겹치고 PENDING 상태인 대타 요청들을 CANCELED 상태로 변경
    @Modifying
    @Query("UPDATE SubstitutionRequest sr SET sr.status = 'CANCELED' WHERE sr.id IN :requestIds")
    int updateStatusToCanceledByIdIn(@Param("requestIds") List<Long> requestIds);
}
