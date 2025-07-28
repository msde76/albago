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
import java.util.Optional;

public interface TeamScheduleRepository extends JpaRepository<TeamSchedule,Long> {

    // TeamSchedule 엔티티에 date 필드 대신 startTime(LocalDateTime)이 있으므로,
    // startTime의 날짜 부분을 기준으로 조회하도록 변경합니다.
    @Query("SELECT ts FROM TeamSchedule ts WHERE ts.team.id = :teamId " +
            "AND FUNCTION('DATE', ts.startTime) BETWEEN :startDate AND :endDate " +
            "ORDER BY FUNCTION('DATE', ts.startTime) ASC, ts.startTime ASC")
    List<TeamSchedule> findByTeamIdAndDateRangeOrderByDateAscStartTimeAsc(
            @Param("teamId") Long teamId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

    // MemberId가 추가된 버전도 동일하게 변경
    @Query("SELECT ts FROM TeamSchedule ts WHERE ts.team.id = :teamId " +
            "AND ts.member.id = :memberId " +
            "AND FUNCTION('DATE', ts.startTime) BETWEEN :startDate AND :endDate " +
            "ORDER BY FUNCTION('DATE', ts.startTime) ASC, ts.startTime ASC")
    List<TeamSchedule> findByTeamIdAndMemberIdAndDateRangeOrderByDateAscStartTimeAsc(
            @Param("teamId") Long teamId,
            @Param("memberId") Long memberId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

    // 단일 스케줄 조회 (팀, 날짜, 멤버 기준) - 날짜는 startTime에서 추출
    @Query("SELECT ts FROM TeamSchedule ts WHERE ts.team = :team " +
            "AND FUNCTION('DATE', ts.startTime) = :date " +
            "AND ts.member = :member")
    Optional<TeamSchedule> findByTeamAndDateAndMember(
            @Param("team") Team team,
            @Param("date") LocalDate date,
            @Param("member") Member member
    );

    // 특정 팀의 특정 날짜에 지정된 시간 범위와 겹치는 스케줄 조회
    // date 대신 startTime의 날짜를 추출하여 비교, 시간 비교는 LocalDateTime으로 진행
    @Query("SELECT ts FROM TeamSchedule ts WHERE ts.team = :team " +
            "AND FUNCTION('DATE', ts.startTime) = :date " +
            "AND ((ts.startTime < :endDateTime AND ts.endTime > :startDateTime))")
    List<TeamSchedule> findOverlappingSchedulesForTeam(
            @Param("team") Team team,
            @Param("date") LocalDate date,
            @Param("startDateTime") LocalDateTime startDateTime,
            @Param("endDateTime") LocalDateTime endDateTime
    );

    // 이 메서드는 이미 LocalDateTime을 사용하고 있으므로 유지
    List<TeamSchedule> findByTeamIdAndStartTimeBetweenOrderByStartTimeAsc(
            Long teamId, LocalDateTime startDateTime, LocalDateTime endDateTime);

    // 특정 팀의 특정 날짜에 특정 시간 범위와 겹치는 특정 멤버의 스케줄 조회
    // date 대신 startTime의 날짜를 추출하여 비교, 시간 비교는 LocalDateTime으로 진행
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