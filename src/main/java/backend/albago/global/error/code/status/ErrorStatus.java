package backend.albago.global.error.code.status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseErrorCode {

    // 기본 에러
    _INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "서버 에러, 관리자에게 문의 바랍니다."),
    _BAD_REQUEST(HttpStatus.BAD_REQUEST, "COMMON400", "잘못된 요청입니다."),
    _UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "COMMON401", "인증이 필요합니다."),
    _FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON403", "금지된 요청입니다."),

    // TEAM
    TEAM_NOT_FOUND(HttpStatus.BAD_REQUEST, "TEAM_4001", "존재하지 않는 팀입니다."),
    CANNOT_INVITE_SELF(HttpStatus.BAD_REQUEST, "TEAM_4002", "자기자신은 팀에 초대할 수 없습니다."),
    ALREADY_TEAM_MEMBER(HttpStatus.BAD_REQUEST, "TEAM_4003", "이미 팀에 존재하는 멤버입니다."),

    // TEAM MEMBER
    TEAM_MEMBER_NOT_FOUND(HttpStatus.BAD_REQUEST, "TEAM_MEMBER_4001", "존재하지 않는 팀 멤버입니다."),
    TEAM_MEMBER_NOT_IN_TEAM(HttpStatus.BAD_REQUEST, "TEAM_MEMBER_4002", "팀에 존재하지 않는 멤버입니다."),
    CANNOT_DELETE_OWNER_MEMBER(HttpStatus.FORBIDDEN, "TEAM_MEMBER_4003", "팀장은 자기 자신을 팀 멤버에서 삭제할 수 없습니다. 팀 자체를 삭제해야 합니다."),
    TEAM_INVITE_ALREADY_ACCEPTED(HttpStatus.BAD_REQUEST, "TEAM_MEMBER_4004", "이미 초대를 수락했습니다."),
    TEAM_INVITE_ALREADY_REJECTED(HttpStatus.CONFLICT, "TEAM_INVITE_4005", "이미 거절된 팀 초대입니다."),
    MEMBER_NOT_IN_TEAM(HttpStatus.BAD_REQUEST, "TEAM4002", "해당 멤버는 팀에 속해있지 않습니다."),

    // SCHEDULE
    SCHEDULE_NOT_FOUND(HttpStatus.BAD_REQUEST, "SCHEDULE_4001", "존재하지 않는 스케줄입니다."),

    // Notification
    NOTIFICATION_NOT_FOUND(HttpStatus.NOT_FOUND, "NOTIFICATION4001", "존재하지 않는 알림입니다."),

    // Shift
    ALREADY_CLOCK_IN(HttpStatus.CONFLICT, "SHIFT_4001", "이미 출근 상태입니다. 퇴근을 먼저 해주세요."),
    NOT_CLOCK_IN_STATE(HttpStatus.CONFLICT, "SHIFT_4002", "아직 출근 상태가 아닙니다. 먼저 출근을 기록해주세요."),
    INVALID_DATE_RANGE(HttpStatus.BAD_REQUEST, "COMMON4003", "시작일이 종료일보다 늦을 수 없습니다."),

    // GPS
    LOCATION_NOT_CONFIGURED(HttpStatus.BAD_REQUEST, "SHIFT_4003", "팀의 출퇴근 위치가 설정되지 않았습니다."),
    OUT_OF_ATTENDANCE_RADIUS(HttpStatus.FORBIDDEN, "SHIFT_4004", "현재 위치가 출퇴근 허용 반경을 벗어났습니다."),

    // Member
    NO_SUCH_MEMBER(HttpStatus.BAD_REQUEST,"MEMBER_4001","멤버가 존재하지 않습니다."),
    NO_SUCH_MEMBER_BY_EMAIL(HttpStatus.BAD_REQUEST, "MEMBER_4002", "유저가 존재하지 않는 이메일입니다."),

    // Page
    PAGE_UNDER_ONE(HttpStatus.BAD_REQUEST,"PAGE_4001","페이지는 1이상으로 입력해야 합니다."),
    PAGE_SIZE_UNDER_ONE(HttpStatus.BAD_REQUEST,"PAGE_4002","페이지 사이즈는 1이상으로 입력해야 합니다."),

    // Category
    NO_SUCH_CATEGORY(HttpStatus.BAD_REQUEST, "CLOTH_4003", "카테고리가 존재하지 않습니다."),

    // history
    WRONG_DATE_FORM(HttpStatus.BAD_REQUEST, "HISTORY_4002", "날짜 형태는 YYYY-MM 이어야 합니다."),
    HISTORY_NOT_FOUND(HttpStatus.BAD_REQUEST, "HISTORY_4003", "존재하지 않는 HistoryId 입니다."),

    // Like
    ALREADY_LIKED(HttpStatus.CONFLICT, "LIKE_4002", "이미 좋아요를 누른 기록입니다."),
    NOT_LIKED_YET(HttpStatus.BAD_REQUEST, "LIKE_4003", "아직 좋아요를 누르지 않은 기록입니다."),

    //Comment
    COMMENT_NOT_FOUND(HttpStatus.BAD_REQUEST, "COMMENT_4003", "존재하지 않는 CommentId 입니다."),


    // AWS S3
    S3_FILE_UPLOAD_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "S3_5001", "S3 파일 업로드에 실패했습니다."),
    S3_FILE_DELETE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "S3_5002", "S3 파일 삭제에 실패했습니다."),
    S3_FILE_URL_PARSE_FAILED(HttpStatus.BAD_REQUEST, "S3_4001", "S3 파일 URL 파싱에 실패했습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public ErrorReasonDTO getReasonHttpStatus() {
        return ErrorReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .httpStatus(httpStatus)
                .build();
    }
}
