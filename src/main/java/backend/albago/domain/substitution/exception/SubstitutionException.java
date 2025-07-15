package backend.albago.domain.substitution.exception;

import backend.albago.global.error.code.status.BaseErrorCode;
import backend.albago.global.exception.GeneralException;

public class SubstitutionException extends GeneralException {

    public SubstitutionException(BaseErrorCode code) { super(code); }
}
