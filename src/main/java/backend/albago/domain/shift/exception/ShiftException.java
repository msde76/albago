package backend.albago.domain.shift.exception;

import backend.albago.global.error.code.status.BaseErrorCode;
import backend.albago.global.exception.GeneralException;

public class ShiftException extends GeneralException {

    public ShiftException(BaseErrorCode code) { super(code); }
}
