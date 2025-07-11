package backend.albago.global.error.code.status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SuccessStatus implements BaseCode {

    // Common
    OK(HttpStatus.OK, "COMMON_200", "성공입니다."),

    // Team
    TEAM_CREATED(HttpStatus.CREATED, "TEAM_200", "팀이 성공적으로 조회되었습니다."),
    TEAM_LIST_FINED(HttpStatus.OK, "TEAM_201", "팀 목록이 성공적으로 조회되었습니다."),
    TEAM_FINED(HttpStatus.OK, "TEAM_202", "팀 정보가 성공적으로 조회되었습니다."),
    TEAM_UPDATE(HttpStatus.OK, "TEAM_203", "팀 정보가 성공적으로 수정되었습니다."),
    TEAM_DELETE(HttpStatus.OK, "TEAM_204", "팀이 성공적으로 삭제되었습니다."),
    TEAM_INVITE(HttpStatus.OK, "TEAM_205", "팀에 성공적으로 초대되었습니다."),
    TEAM_MEMBER_UPDATE(HttpStatus.OK, "TEAM_206", "팀 멤버 정보가 성공적으로 수정되었습니다."),
    TEAM_MEMBER_DELETE(HttpStatus.OK, "TEAM_207", "팀 멤버가 성공적으로 삭제되었습니다."),
    TEAM_INVITE_ACCEPT(HttpStatus.OK, "TEAM_208", "초대가 성공적으로 수락되었습니다."),
    TEAM_INVITE_REJECT(HttpStatus.OK, "TEAM_209", "초대가 성공적으로 거절되었습니다."),

    // Schedule
    PERSONAL_SCHEDULE_CREATED(HttpStatus.CREATED, "SCHEDULE_200", "개인 스케줄이 성공적으로 생성되었습니다."),
    PERSONAL_SCHEDULE_FOUND(HttpStatus.OK, "SCHEDULE_201", "특정 기간의 개인 스케줄이 성공적으로 조회되었습니다."),
    PERSONAL_SCHEDULE_UPDATE(HttpStatus.OK, "SCHEDULE_202", "개인 스케줄이 성공적으로 수정되었습니다."),
    PERSONAL_SCHEDULE_DELETE(HttpStatus.OK, "SCHEDULE_203", "개인 스케줄이 성공적으로 삭제되었습니다."),

    // Team Schedule
    TEAM_SCHEDULE_CREATED(HttpStatus.CREATED, "SCHEDULE_2005", "팀 스케줄이 성공적으로 생성되었습니다."),
    TEAM_SCHEDULE_FOUND(HttpStatus.OK, "SCHEDULE_2006", "팀 스케줄을 성공적으로 조회했습니다."),
    TEAM_SCHEDULE_UPDATE(HttpStatus.OK, "SCHEDULE_2007", "팀 스케줄이 성공적으로 수정되었습니다."),
    TEAM_SCHEDULE_DELETE(HttpStatus.OK, "SCHEDULE_2008", "팀 스케줄이 성공적으로 삭제되었습니다."),

    // Notification
    NOTIFICATION_FOUND(HttpStatus.OK, "SCHEDULE_200", "알람 목록이 성공적으로 조회되었습니다."),
    NOTIFICATION_MARKED_AS_READ(HttpStatus.OK, "SCHEDULE_201", "알람이 성공적으로 읽음 처리되었습니다."),
    NOTIFICATION_DELETE(HttpStatus.OK, "SCHEDULE_202", "알람이 성공적으로 삭제되었습니다."),

    // Shift
    CLOCK_IN(HttpStatus.OK, "SHIFT_200", "출근 기록이 성공적으로 처리되었습니다."),
    CLOCK_OUT(HttpStatus.OK, "SHIFT_201", "퇴근 기록이 성공적으로 처리되었습니다."),
    GET_SHIFT_LOGS(HttpStatus.OK, "SHIFT_2003", "근무 기록을 성공적으로 조회되었습니다."),

    // Post
    GET_POSTS(HttpStatus.OK, "POST_200", "게시물 목록이 성공적으로 조회되었습니다."),
    GET_LIKE_POSTS(HttpStatus.OK, "POST_201", "인기 게시물 목록이 성공적으로 조회되었습니다."),
    GET_POST(HttpStatus.OK, "POST_202", "게시물이 성공적으로 조회되었습니다."),
    POST_UPDATED(HttpStatus.OK, "POST_203", "게시물이 성공적으로 수정되었습니다."),
    POST_DELETED(HttpStatus.NO_CONTENT, "POST_204", "게시물이 성공적으로 삭제되었습니다."),
    POST_CREATED(HttpStatus.CREATED, "POST_205", "게시물이 성공적으로 생성되었습니다."),

    // Like
    POST_LIKE(HttpStatus.OK, "Like_200", "좋아요가 성공적으로 생성되었습니다."),
    POST_UNLIKE(HttpStatus.OK, "Like_201", "좋아요가 성공적으로 취소되었습니다."),

    // Handover
    HANDOVER_CREATED(HttpStatus.CREATED, "HANDOVER_200", "인수인계 노트가 성공적으로 생성되었습니다."),
    HANDOVERS_FINED(HttpStatus.OK, "HANDOVER_201", "인수인계 노트 목록이 성공적으로 조회되었습니다."),
    HANDOVER_FINED(HttpStatus.OK, "HANDOVER_202", "인수인계 노트 세부 정보가 성공적으로 조회되었습니다."),
    HANDOVER_UPDATE(HttpStatus.OK, "HANDOVER_203", "인수인계 노트가 성공적으로 수정되었습니다."),
    HANDOVER_DELETE(HttpStatus.OK, "HANDOVER_204", "인수인계 노트가 성공적으로 삭제되었습니다."),

    // TeamPost
    TEAM_POSTS_FINED(HttpStatus.OK, "TEAM_POST_200", "팀 게시물 목록이 성공적으로 조회되었습니다."),
    TEAM_POST_FINED(HttpStatus.OK, "TEAM_POST_201", "팀 게시물의 상세 정보가 성공적으로 조회되었습니다."),
    TEAM_POSTS_CREATE(HttpStatus.CREATED, "TEAM_POST_202", "팀 게시물이 성공적으로 생성되었습니다."),
    TEAM_POSTS_UPDATE(HttpStatus.OK, "TEAM_POST_203", "팀 게시물이 성공적으로 수정되었습니다."),
    TEAM_POSTS_DELETE(HttpStatus.OK, "TEAM_POST_204", "팀 게시물이 성공적으로 삭제되었습니다."),

    // TeamPostComment
    TEAM_POSTS_COMMENT_FINED(HttpStatus.OK, "TEAM_POST_COMMENT_200", "팀 게시물의 댓글 목록이 성공적으로 조회되엇습니다."),
    TEAM_POSTS_COMMENT_CREATE(HttpStatus.CREATED, "TEAM_POST_COMMENT_201", "팀 게시물에 댓글이 성공적으로 생성되엇습니다."),
    TEAM_POSTS_COMMENT_UPDATE(HttpStatus.OK, "TEAM_POST_COMMENT_202", "팀 게시물에 댓글이 성공적으로 수정되엇습니다."),
    TEAM_POSTS_COMMENT_DELETE(HttpStatus.OK, "TEAM_POST_COMMENT_203", "팀 게시물에 댓글이 성공적으로 삭제되엇습니다."),

    //Cloth
    CLOTH_VIEW_SUCCESS(HttpStatus.OK,"CLOTH_200","옷이 성공적으로 조회되었습니다."),
    CLOTH_CREATED(HttpStatus.CREATED, "CLOTH_201"," 옷이 성공적으로 생성되었습니다."),
    CLOTH_DELETED(HttpStatus.NO_CONTENT,"CLOTH_202","옷이 성공적으로 삭제되었습니다"),

    //history
    HISTORY_MONTH(HttpStatus.OK, "HISTORY_200", "월별 기록이 성공적으로 조회되었습니다."),
    HISTORY_DAY(HttpStatus.OK, "HISTORY_201", "일별 기록이 성공적으로 조회되었습니다."),
    HISTORY_CREATED(HttpStatus.CREATED, "HISTORY_202", "기록이 성공적으로 생성되었습니다."),
    HISTORY_UPDATE(HttpStatus.OK, "HISTORY_203", "기록이 성공적으로 수정되었습니다."),
    HISTORY_DELETE(HttpStatus.OK, "HISTORY_204", "기록이 성곡적으로 삭제되었습니다."),
    HISTORY_LIKE(HttpStatus.OK, "HISTORY_205", "좋아요가 상태가 변경되었습니다."),
    HISTORY_LIKED_MEMBER(HttpStatus.OK, "HISTORY_206", "좋아요를 누른 유저가 조회되었습니다."),

    //comment
    COMMENT_CREATED(HttpStatus.CREATED, "COMMENT_200", "댓글/대댓글이 성공적으로 작성되었습니다."),
    COMMENT_FINED(HttpStatus.OK, "COMMENT_201", "댓글/대댓글이 성공적으로 조회되었습니다."),
    COMMENT_UPDATE(HttpStatus.OK, "COMMENT_202", "댓글/대댓글이 성공적으로 수정되었습니다."),
    COMMENT_DELETE(HttpStatus.OK, "COMMENT_203", "댓들/대댓글이 성공적으로 삭제되었습니다.");

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
    public ReasonDTO getReasonHttpStatus() {
        return ReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(true)
                .httpStatus(httpStatus)
                .build();
    }
}