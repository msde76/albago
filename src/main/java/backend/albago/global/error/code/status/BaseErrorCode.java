package backend.albago.global.error.code.status;

public interface BaseErrorCode {

    String getCode();

    String getMessage();

    ErrorReasonDTO getReasonHttpStatus();
}
