package org.example.tpo_10.constraint;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UrlNotValidValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UrlNotValid {
    String message() default "{org.example.tpo_10.UrlNotValid.noDomain}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
