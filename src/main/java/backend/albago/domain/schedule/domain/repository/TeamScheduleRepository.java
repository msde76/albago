package backend.albago.domain.schedule.domain.repository;

import backend.albago.domain.schedule.domain.entity.TeamSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TeamScheduleRepository extends JpaRepository<TeamSchedule,Long> {

    List<TeamSchedule> findByTeamIdAndDateBetweenOrderByDateAscStartTimeAsc(Long teamId, LocalDate startDate, LocalDate endDate);

    List<TeamSchedule> findByTeamIdAndMemberIdAndDateBetweenOrderByDateAscStartTimeAsc(Long teamId, Long memberId, LocalDate startDate, LocalDate endDate);
}
