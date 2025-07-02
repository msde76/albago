package backend.albago.domain.substitution.domain.repository;

import backend.albago.domain.shift.domain.entity.ShiftLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubstitutionRequestRepository extends JpaRepository<ShiftLog,Long> {
}
