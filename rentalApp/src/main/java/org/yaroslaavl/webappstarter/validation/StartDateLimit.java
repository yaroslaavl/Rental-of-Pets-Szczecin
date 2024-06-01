package org.yaroslaavl.webappstarter.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = StartDateLimitValidator.class)
public @interface StartDateLimit {

    String message() default "The date should be plus 1 day from today";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
