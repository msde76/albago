package backend.albago.domain.member.exception;

import backend.albago.global.error.code.status.BaseErrorCode;
import backend.albago.global.exception.GeneralException;

public class MemberException extends GeneralException {

    public MemberException(BaseErrorCode code) { super(code); }
}
