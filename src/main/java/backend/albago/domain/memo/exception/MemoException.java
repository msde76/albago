package backend.albago.domain.memo.exception;

import backend.albago.global.error.code.status.BaseErrorCode;
import backend.albago.global.exception.GeneralException;

public class MemoException extends GeneralException {

    public MemoException(BaseErrorCode code) { super(code); }
}
