package backend.albago.domain.shift.domain.repository;

import backend.albago.domain.member.domain.entity.Member;
import backend.albago.domain.shift.domain.entity.ShiftLog;
import backend.albago.domain.team.domain.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ShiftLogRepository extends JpaRepository<ShiftLog,Long> {

    Optional<ShiftLog> findTopByMemberAndTeamAndClockOutTimeIsNullOrderByClockInTimeDesc(Member member, Team team);

    List<ShiftLog> findByMemberAndTeamAndShiftDateBetweenAndClockOutTimeIsNotNullOrderByShiftDateAscClockInTimeAsc(
            Member member, Team team, LocalDate startDate, LocalDate endDate
    );

    List<ShiftLog> findByTeamAndShiftDateBetweenAndClockOutTimeIsNotNullOrderByShiftDateAscClockInTimeAsc(
            Team team, LocalDate startDate, LocalDate endDate
    );

}
