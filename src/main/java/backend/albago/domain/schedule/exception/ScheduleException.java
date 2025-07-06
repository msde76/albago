package backend.albago.domain.schedule.exception;

import backend.albago.global.error.code.status.BaseErrorCode;
import backend.albago.global.exception.GeneralException;

public class ScheduleException extends GeneralException {

    public ScheduleException(BaseErrorCode code) { super(code); }
}
