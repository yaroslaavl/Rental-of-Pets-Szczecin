package org.yaroslaavl.webappstarter.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AgeLimitValidator.class)
public @interface AgeLimit {

    int minAge() default 16;
    int minYear() default 1920;
    String message() default "User must be at least 16 years old";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};


}
