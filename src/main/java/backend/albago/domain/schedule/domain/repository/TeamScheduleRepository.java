package backend.albago.domain.schedule.domain.repository;

import backend.albago.domain.member.domain.entity.Member;
import backend.albago.domain.schedule.domain.entity.TeamSchedule;
import backend.albago.domain.team.domain.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface TeamScheduleRepository extends JpaRepository<TeamSchedule,Long> {

    List<TeamSchedule> findByTeamIdAndDateBetweenOrderByDateAscStartTimeAsc(Long teamId, LocalDate startDate, LocalDate endDate);

    List<TeamSchedule> findByTeamIdAndMemberIdAndDateBetweenOrderByDateAscStartTimeAsc(Long teamId, Long memberId, LocalDate startDate, LocalDate endDate);

    Optional<TeamSchedule> findByTeamAndDateAndMember(Team team, LocalDate date, Member member);

    // 특정 팀의 특정 날짜에 지정된 시간 범위와 겹치는 스케줄 조회
    @Query("SELECT ts FROM TeamSchedule ts WHERE ts.team = :team " +
            "AND ts.date = :date " +
            "AND ((ts.startTime < :endTime AND ts.endTime > :startTime))")
    List<TeamSchedule> findOverlappingSchedulesForTeam(
            @Param("team") Team team,
            @Param("date") LocalDate date,
            @Param("startTime") LocalTime startTime,
            @Param("endTime") LocalTime endTime
    );

    // 특정 팀의 특정 날짜에 특정 시간 범위와 겹치는 특정 멤버의 스케줄 조회
    @Query("SELECT ts FROM TeamSchedule ts WHERE ts.team = :team " +
            "AND ts.date = :date " +
            "AND ts.member = :member " +
            "AND ((ts.startTime < :endTime AND ts.endTime > :startTime))")
    List<TeamSchedule> findOverlappingSchedulesForTeamMember(
            @Param("team") Team team,
            @Param("date") LocalDate date,
            @Param("member") Member member,
            @Param("startTime") LocalTime startTime,
            @Param("endTime") LocalTime endTime
    );
}
