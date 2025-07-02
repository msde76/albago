package backend.albago.domain.salary.domain.repository;

import backend.albago.domain.salary.domain.entity.SalaryLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalaryLogRepository extends JpaRepository<SalaryLog, Long> {
}
