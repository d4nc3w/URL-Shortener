package org.example.tpo_10.constraint;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.ArrayList;
import java.util.List;

public class UrlNotValidValidator implements ConstraintValidator<UrlNotValid, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if(value == null){
            return false;
        }
        //(".com", ".net", ".org", ".edu", ".gov", ".pl");
        boolean isType = value.endsWith(".com") || value.endsWith(".net") || value.endsWith(".org") ||
                value.endsWith(".edu") || value.endsWith(".pl") || value.endsWith(".gov") ||
                value.endsWith(".com/") || value.endsWith(".pl/");
        return isType;
    }

    @Override
    public void initialize(UrlNotValid constraintAnnotation) {
    }
}

