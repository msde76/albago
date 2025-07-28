package backend.albago.domain.schedule.domain.repository;

import backend.albago.domain.member.domain.entity.Member;
import backend.albago.domain.schedule.domain.entity.TeamSchedule;
import backend.albago.domain.team.domain.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;

import java.util.List;

public interface TeamScheduleRepository extends JpaRepository<TeamSchedule,Long> {

    // 이 메서드는 이미 LocalDateTime을 사용하고 있으므로 유지
    List<TeamSchedule> findByTeamIdAndStartTimeBetweenOrderByStartTimeAsc(
            Long teamId, LocalDateTime startDateTime, LocalDateTime endDateTime);

    // 특정 팀의 특정 날짜에 특정 시간 범위와 겹치는 특정 멤버의 스케줄 조회
    @Query("SELECT ts FROM TeamSchedule ts WHERE ts.team = :team " +
            "AND FUNCTION('DATE', ts.startTime) = :date " +
            "AND ts.member = :member " +
            "AND ((ts.startTime < :endDateTime AND ts.endTime > :startDateTime))") // LocalDateTime으로 시간 범위 비교
    List<TeamSchedule> findOverlappingSchedulesForTeamMember(
            @Param("team") Team team,
            @Param("date") LocalDate date,
            @Param("member") Member member,
            @Param("startDateTime") LocalDateTime startDateTime,
            @Param("endDateTime") LocalDateTime endDateTime
    );
}