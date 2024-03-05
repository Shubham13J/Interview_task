package com.innoventes.test.app.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EvenNumberOrZeroValidator implements ConstraintValidator<EvenNumberOrZero, Number> {

    @Override
    public void initialize(EvenNumberOrZero constraintAnnotation) {

    }

    @Override
    public boolean isValid(Number value, ConstraintValidatorContext context) {

        return value == null || value.intValue() % 2 == 0;
    }
}
