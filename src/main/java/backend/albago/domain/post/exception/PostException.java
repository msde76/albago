package backend.albago.domain.post.exception;

import backend.albago.global.error.code.status.BaseErrorCode;
import backend.albago.global.exception.GeneralException;

public class PostException extends GeneralException {

    public PostException(BaseErrorCode code) { super(code); }
}
