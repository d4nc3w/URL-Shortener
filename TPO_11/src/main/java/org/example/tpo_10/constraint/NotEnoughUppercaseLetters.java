package org.example.tpo_10.constraint;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = NotEnoughUppercaseValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NotEnoughUppercaseLetters {
    String message() default "{org.example.tpo_10.PasswordValidator.missing.uppercase}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
