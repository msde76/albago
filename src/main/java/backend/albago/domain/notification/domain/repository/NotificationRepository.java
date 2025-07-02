package backend.albago.domain.notification.domain.repository;

import backend.albago.domain.notification.domain.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {
}
