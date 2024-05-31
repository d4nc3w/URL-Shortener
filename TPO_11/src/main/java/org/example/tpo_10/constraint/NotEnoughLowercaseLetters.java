package org.example.tpo_10.constraint;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = NotEnoughLowercaseValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NotEnoughLowercaseLetters {
    String message() default "{org.example.tpo_10.PasswordValidator.missing.lowercase}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
