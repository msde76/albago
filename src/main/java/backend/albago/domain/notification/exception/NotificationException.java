package backend.albago.domain.notification.exception;

import backend.albago.global.error.code.status.BaseErrorCode;
import backend.albago.global.exception.GeneralException;

public class NotificationException extends GeneralException {

    public NotificationException(BaseErrorCode code) { super(code); }
}
