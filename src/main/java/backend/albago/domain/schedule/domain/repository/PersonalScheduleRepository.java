package backend.albago.domain.schedule.domain.repository;

import backend.albago.domain.schedule.domain.entity.PersonalSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PersonalScheduleRepository extends JpaRepository<PersonalSchedule,Long> {

    List<PersonalSchedule> findByMemberIdAndDateBetweenOrderByDateAscStartTimeAsc(Long memberId, LocalDate startDate, LocalDate endDate);

    Optional<PersonalSchedule> findByTeamScheduleId(Long teamScheduleId);
}
