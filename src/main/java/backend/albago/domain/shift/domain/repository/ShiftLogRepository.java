package backend.albago.domain.shift.domain.repository;

import backend.albago.domain.shift.domain.entity.ShiftLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShiftLogRepository extends JpaRepository<ShiftLog,Long> {
}
