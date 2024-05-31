package org.example.tpo_10.constraint;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NotEnoughLowercaseValidator implements ConstraintValidator<NotEnoughLowercaseLetters, String> {
    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        boolean isValid;

        if(password == null || password.isEmpty()){
            return true;
        } else {
            int lowerCount = 0;

            for(char ch : password.toCharArray()){
                if(Character.isLowerCase(ch)){
                    lowerCount++;
                }
            }
            isValid = lowerCount >= 1;
            return isValid;
        }
    }

    @Override
    public void initialize(NotEnoughLowercaseLetters constraintAnnotation) {
    }
}
