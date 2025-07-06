package backend.albago.domain.notification.dto;

import backend.albago.domain.model.enums.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class NotificationResponseDTO {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class NotificationInfo {
        private Long notificationId;
        private Long teamId;
        private String title;
        private String body;
        private Boolean isRead;
        private NotificationType notificationType;
        private Long relatedEntityId;
        private LocalDateTime createdAt;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class NotificationFindResult {
        private Long memberId;
        private List<NotificationInfo> notifications;
        private Integer totalNotifications;
        private Integer unreadNotifications;
    }
}
