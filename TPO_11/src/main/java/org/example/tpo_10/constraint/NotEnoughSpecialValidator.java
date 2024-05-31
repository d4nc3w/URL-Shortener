package org.example.tpo_10.constraint;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NotEnoughSpecialValidator implements ConstraintValidator<NotEnoughSpecial, String> {
    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        boolean isValid;

        if(password == null || password.isEmpty()){
            return true;
        } else {
            int specialCount = 0;

            for(char ch : password.toCharArray()){
                if(!Character.isLetterOrDigit(ch)){
                    specialCount++;
                }
            }
            isValid = specialCount >= 4;
            return isValid;
        }

    }

    @Override
    public void initialize(NotEnoughSpecial constraintAnnotation) {
    }
}
