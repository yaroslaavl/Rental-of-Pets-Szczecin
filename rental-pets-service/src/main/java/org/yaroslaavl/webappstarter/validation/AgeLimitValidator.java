package org.yaroslaavl.webappstarter.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class AgeLimitValidator implements ConstraintValidator<AgeLimit, LocalDate> {

    int minAge;
    int minYear;

    @Override
    public void initialize(AgeLimit constraintAnnotation) {
        this.minAge = constraintAnnotation.minAge();
        this.minYear = constraintAnnotation.minYear();
    }

    @Override
    public boolean isValid(LocalDate localeDate, ConstraintValidatorContext constraintValidatorContext) {
        if(localeDate == null){
            return true;
        }
        LocalDate today = LocalDate.now();
        LocalDate minimumAgeYears = today.minusYears(this.minAge);
        LocalDate minYearDate = LocalDate.of(minYear, 1, 1);
        return !localeDate.isAfter(minimumAgeYears) && !localeDate.isBefore(minYearDate);
    }
}
