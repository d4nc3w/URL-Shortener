package org.example.tpo_10.constraint;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = NotEnoughSpecialValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NotEnoughSpecial {
    String message() default "{org.example.tpo_10.PasswordValidator.missing.special}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}