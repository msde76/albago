package backend.albago.domain.notification.domain.repository;

import backend.albago.domain.member.domain.entity.Member;
import backend.albago.domain.notification.domain.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByMemberOrderByCreatedAtDesc(Member member);

    List<Notification> findByMemberAndIsReadFalseOrderByCreatedAtDesc(Member member);
}
