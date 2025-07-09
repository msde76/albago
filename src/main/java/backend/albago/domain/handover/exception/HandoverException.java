package backend.albago.domain.handover.exception;

import backend.albago.global.error.code.status.BaseErrorCode;
import backend.albago.global.exception.GeneralException;

public class HandoverException extends GeneralException {

    public HandoverException(BaseErrorCode code) { super(code); }
}
