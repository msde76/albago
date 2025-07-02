package backend.albago.domain.schedule.domain.repository;

import backend.albago.domain.schedule.domain.entity.PersonalSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamSchedulRepository extends JpaRepository<PersonalSchedule,Long> {
}
