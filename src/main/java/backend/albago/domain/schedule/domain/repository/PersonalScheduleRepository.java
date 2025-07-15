package backend.albago.domain.schedule.domain.repository;

import backend.albago.domain.member.domain.entity.Member;
import backend.albago.domain.schedule.domain.entity.PersonalSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface PersonalScheduleRepository extends JpaRepository<PersonalSchedule,Long> {

    List<PersonalSchedule> findByMemberIdAndDateBetweenOrderByDateAscStartTimeAsc(Long memberId, LocalDate startDate, LocalDate endDate);

    Optional<PersonalSchedule> findByTeamScheduleId(Long teamScheduleId);

    // 특정 멤버의 특정 날짜 개인 시간표 중, 주어진 시간 범위와 겹치는 스케줄 조회
    @Query("SELECT ps FROM PersonalSchedule ps WHERE ps.member = :member " +
            "AND ps.date = :date " +
            "AND ((ps.startTime < :endTime AND ps.endTime > :startTime))")
    List<PersonalSchedule> findOverlappingSchedulesForMember(
            @Param("member") Member member,
            @Param("date") LocalDate date,
            @Param("startTime") LocalTime startTime,
            @Param("endTime") LocalTime endTime
    );
}
