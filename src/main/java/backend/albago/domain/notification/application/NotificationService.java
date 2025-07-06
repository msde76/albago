package backend.albago.domain.notification.application;

import backend.albago.domain.model.enums.NotificationType;
import backend.albago.domain.notification.dto.NotificationResponseDTO;

public interface NotificationService {

    NotificationResponseDTO.NotificationFindResult findNotification(String memberId);

    void markNotificationAsRead(Long notificationId, String memberIdString);

    void deleteNotification(Long notificationId, String memberId);

    void createNotification(Long memberId, Long teamId, String title, String body, NotificationType type, Long relatedEntityId);
}
