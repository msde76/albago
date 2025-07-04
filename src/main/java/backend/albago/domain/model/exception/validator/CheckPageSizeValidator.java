package backend.albago.domain.model.exception.validator;

import backend.albago.domain.model.exception.annotation.CheckPageSize;
import backend.albago.global.error.code.status.ErrorStatus;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CheckPageSizeValidator implements ConstraintValidator<CheckPageSize, Integer> {

    @Override
    public void initialize(CheckPageSize constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Integer pageSize, ConstraintValidatorContext context) {
        boolean isValid = pageSize >= 1;

        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorStatus.PAGE_SIZE_UNDER_ONE.toString()).addConstraintViolation();
        }

        return isValid;

    }
}
