package org.yaroslaavl.webappstarter.validation;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class StartDateLimitValidator implements ConstraintValidator<StartDateLimit, LocalDate> {

    @Override
    public void initialize(StartDateLimit constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(LocalDate localDate, ConstraintValidatorContext constraintValidatorContext) {
        LocalDate today = LocalDate.now();
        LocalDate minimumStartOrderDate= today.plusDays(1);
        return !minimumStartOrderDate.isAfter(localDate);
    }
}
