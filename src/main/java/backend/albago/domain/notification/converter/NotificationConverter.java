package backend.albago.domain.notification.converter;

import backend.albago.domain.notification.domain.entity.Notification;
import backend.albago.domain.notification.dto.NotificationResponseDTO;
import backend.albago.domain.team.domain.entity.Team;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class NotificationConverter {

    public static NotificationResponseDTO.NotificationInfo toNotificationInfo(Notification notification) {
        Long teamId = Optional.ofNullable(notification.getTeam()).map(Team::getId).orElse(null);
        return NotificationResponseDTO.NotificationInfo.builder()
                .notificationId(notification.getId())
                .teamId(teamId)
                .title(notification.getTitle())
                .body(notification.getBody())
                .isRead(notification.getIsRead())
                .notificationType(notification.getNotificationType())
                .relatedEntityId(notification.getRelatedEntityId())
                .createdAt(notification.getCreatedAt())
                .build();
    }

    public static NotificationResponseDTO.NotificationFindResult toNotificationFindResult(Long memberId, List<Notification> notifications) {
        List<NotificationResponseDTO.NotificationInfo> notificationInfoList = notifications.stream()
                .map(NotificationConverter::toNotificationInfo)
                .collect(Collectors.toList());

        long unreadCount = notifications.stream()
                .filter(notification -> !notification.getIsRead())
                .count();

        return NotificationResponseDTO.NotificationFindResult.builder()
                .memberId(memberId)
                .notifications(notificationInfoList)
                .totalNotifications(notificationInfoList.size())
                .unreadNotifications((int) unreadCount)
                .build();
    }
}
