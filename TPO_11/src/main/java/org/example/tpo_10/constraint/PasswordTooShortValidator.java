package org.example.tpo_10.constraint;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordTooShortValidator implements ConstraintValidator<PasswordTooShort, String> {
    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        boolean isValid;

        if(password == null || password.isEmpty()){
            return true;
        } else {
            int counter = 0;
            for(char c : password.toCharArray()){
                counter++;
            }

            isValid = counter >= 10;
            return isValid;
        }
    }

    @Override
    public void initialize(PasswordTooShort constraintAnnotation) {
    }
}
