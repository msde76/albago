package backend.albago.domain.notification.application;

import backend.albago.domain.member.domain.entity.Member;
import backend.albago.domain.member.domain.repository.MemberRepository;
import backend.albago.domain.model.enums.NotificationType;
import backend.albago.domain.notification.converter.NotificationConverter;
import backend.albago.domain.notification.domain.entity.Notification;
import backend.albago.domain.notification.domain.repository.NotificationRepository;
import backend.albago.domain.notification.dto.NotificationResponseDTO;
import backend.albago.domain.notification.exception.NotificationException;
import backend.albago.domain.team.domain.repository.TeamRepository;
import backend.albago.global.error.code.status.ErrorStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    private final MemberRepository memberRepository;
    private final NotificationRepository notificationRepository;
    private final TeamRepository teamRepository;

    @Override
    @Transactional(readOnly = true)
    public NotificationResponseDTO.NotificationFindResult findNotification(String memberId){

        Long memberIdLong = Long.parseLong(memberId);

        Member member = memberRepository.findById(memberIdLong)
                .orElseThrow(() -> new NotificationException(ErrorStatus.NO_SUCH_MEMBER));

        List<Notification> notifications = notificationRepository.findByMemberOrderByCreatedAtDesc(member);

        return NotificationConverter.toNotificationFindResult(memberIdLong, notifications);
    }

    @Override
    @Transactional
    public void markNotificationAsRead(Long notificationId, String memberId) {

        Long memberIdLong = Long.parseLong(memberId);

        Member member = memberRepository.findById(memberIdLong)
                .orElseThrow(() -> new NotificationException(ErrorStatus.NO_SUCH_MEMBER));

        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new NotificationException(ErrorStatus.NOTIFICATION_NOT_FOUND)); // 알림 존재하지 않음

        if (!notification.getMember().getId().equals(member.getId())) {
            throw new NotificationException(ErrorStatus._FORBIDDEN);
        }

        notification.markAsRead();
        notificationRepository.save(notification);
    }

    @Override
    @Transactional
    public void deleteNotification(Long notificationId, String memberId){

        Long memberIdLong = Long.parseLong(memberId);

        Member requestMember = memberRepository.findById(memberIdLong)
                .orElseThrow(() -> new NotificationException(ErrorStatus.NO_SUCH_MEMBER));

        Notification notificationToDelete = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new NotificationException(ErrorStatus.NOTIFICATION_NOT_FOUND));

        if (!notificationToDelete.getMember().getId().equals(requestMember.getId())) {
            throw new NotificationException(ErrorStatus._FORBIDDEN);
        }

        notificationRepository.delete(notificationToDelete);
    }

    @Override
    @Transactional
    public void createNotification(Long memberId, Long teamId, String title, String body, NotificationType type, Long relatedEntityId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotificationException(ErrorStatus.NO_SUCH_MEMBER));

        Notification notification = Notification.builder()
                .member(member)
                .title(title)
                .body(body)
                .isRead(false)
                .notificationType(type)
                .relatedEntityId(relatedEntityId)
                .build();

        notificationRepository.save(notification);
    }

}
