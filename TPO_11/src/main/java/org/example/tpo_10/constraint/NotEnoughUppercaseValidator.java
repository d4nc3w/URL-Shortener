package org.example.tpo_10.constraint;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NotEnoughUppercaseValidator implements ConstraintValidator<NotEnoughUppercaseLetters, String> {
    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        boolean isValid;

        if(password == null || password.isEmpty()){
            return true;
        } else {
            int upperCount = 0;

            for(char ch : password.toCharArray()){
                if(Character.isUpperCase(ch)){
                    upperCount++;
                }
            }
            isValid = upperCount >= 2;
            return isValid;
        }
    }

    @Override
    public void initialize(NotEnoughUppercaseLetters constraintAnnotation) {
    }
}
