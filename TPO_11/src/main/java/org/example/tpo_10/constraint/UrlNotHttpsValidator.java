package org.example.tpo_10.constraint;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UrlNotHttpsValidator implements ConstraintValidator<UrlNotHttps, String> {

        @Override
        public boolean isValid(String value, ConstraintValidatorContext context) {

           if(value == null){
              return false;
           }

           String type = "https://";
           boolean isType = value.startsWith(type);
              if(!isType){
                  return false;
              }
              return true;
        }

        @Override
        public void initialize(UrlNotHttps constraintAnnotation) {
        }
}
