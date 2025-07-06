package backend.albago.domain.notification.api;

import backend.albago.domain.notification.application.NotificationService;
import backend.albago.domain.notification.dto.NotificationResponseDTO;
import backend.albago.global.common.response.BaseResponse;
import backend.albago.global.error.code.status.SuccessStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notification")
public class NotificationRestController {

    private final NotificationService notificationService;

    @GetMapping("/me")
    @Operation(summary = "알림 목록 조회 API", description = "사용자에게 온 모든 알림 목록을 조회")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "NOTIFICATION_200", description = "OK, 성공적으로 조회되었습니다.")
    })
    public BaseResponse<NotificationResponseDTO.NotificationFindResult> findNotification(
            @RequestHeader(value = "member-id") String memberId
    ) {
        NotificationResponseDTO.NotificationFindResult result = notificationService.findNotification(memberId);
        return BaseResponse.onSuccess(SuccessStatus.NOTIFICATION_FOUND, result);
    }

    @PatchMapping("/{notificationId}/read")
    @Operation(summary = "알림 읽음 처리 API", description = "특정 알림을 읽음 상태로 변경합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "NOTIFICATION_200", description = "OK, 성공적으로 수정되었습니다.")
    })
    public BaseResponse<Void> markNotificationAsRead(
            @RequestHeader(value = "member-id") String memberId,
            @PathVariable("notificationId") Long notificationId
    ) {
        notificationService.markNotificationAsRead(notificationId, memberId);
        return BaseResponse.onSuccess(SuccessStatus.NOTIFICATION_MARKED_AS_READ, null);
    }

    @DeleteMapping("/{notification}")
    @Operation(summary = "알림 삭제 API", description = "특정 알림을 삭제합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "NOTIFICATION_200", description = "OK, 성공적으로 수정되었습니다.")
    })
    public BaseResponse<Void> deleteNotification(
            @RequestHeader(value = "member-id") String memberId,
            @PathVariable(value = "notificationId") Long notificationId
    ) {
        notificationService.deleteNotification(notificationId, memberId);
        return BaseResponse.onSuccess(SuccessStatus.NOTIFICATION_DELETE, null);
    }
}
