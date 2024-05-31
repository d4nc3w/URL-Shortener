package org.example.tpo_10.constraint;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NotEnoughDigitsValidator implements ConstraintValidator<NotEnoughDigits, String> {
    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        boolean isValid;
        if(password == null || password.isEmpty()){
            return true;
        } else {
            int digitCount = 0;

            for(char ch : password.toCharArray()){
                if(Character.isDigit(ch)){
                    digitCount++;
                }
            }
           isValid = digitCount >= 3;
            return isValid;
        }
    }

    @Override
    public void initialize(NotEnoughDigits constraintAnnotation) {
    }
}

