package backend.albago.domain.schedule.domain.repository;

import backend.albago.domain.member.domain.entity.Member;
import backend.albago.domain.schedule.domain.entity.PersonalSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PersonalScheduleRepository extends JpaRepository<PersonalSchedule,Long> {

    // LocalDateTime에서 날짜만 추출하여 비교하는 쿼리
    @Query("SELECT ps FROM PersonalSchedule ps WHERE ps.member.id = :memberId " +
            "AND FUNCTION('DATE', ps.startTime) BETWEEN :startDate AND :endDate " + // H2/MySQL의 경우 DATE 함수, PostgreSQL은 CAST(ps.startTime AS DATE)
            "ORDER BY ps.startTime ASC")
    List<PersonalSchedule> findByMemberIdAndDateRangeOrderByStartTimeAsc(
            @Param("memberId") Long memberId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

    // 특정 멤버의 특정 날짜 개인 시간표 중, 주어진 시간 범위와 겹치는 스케줄 조회
    @Query("SELECT ps FROM PersonalSchedule ps WHERE ps.member = :member " +
            "AND FUNCTION('DATE', ps.startTime) = :date " + // startTime의 날짜 부분만 사용
            "AND ((ps.startTime < :endDateTime AND ps.endTime > :startDateTime))") // LocalDateTime으로 시간 범위 비교
    List<PersonalSchedule> findOverlappingSchedulesForMember(
            @Param("member") Member member,
            @Param("date") LocalDate date,
            @Param("startDateTime") LocalDateTime startDateTime,
            @Param("endDateTime") LocalDateTime endDateTime
    );

    Optional<PersonalSchedule> findByRelatedTeamScheduleId(Long relatedTeamScheduleId);

    List<PersonalSchedule> findByMemberOrderByStartTimeDesc(Member member);
}
