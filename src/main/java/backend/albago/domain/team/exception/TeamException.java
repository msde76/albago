package backend.albago.domain.team.exception;

import backend.albago.global.error.code.status.BaseErrorCode;
import backend.albago.global.exception.GeneralException;

public class TeamException extends GeneralException {

    public TeamException(BaseErrorCode code) { super(code); }
}
