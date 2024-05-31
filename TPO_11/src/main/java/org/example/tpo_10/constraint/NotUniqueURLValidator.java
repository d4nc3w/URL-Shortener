package org.example.tpo_10.constraint;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.example.tpo_10.service.URLService;

import java.util.List;

public class NotUniqueURLValidator implements ConstraintValidator<NotUniqueURL, String> {


    @Override
    public void initialize(NotUniqueURL constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if (value == null) {
            return false;
        }

        List<String> allUrls = URLService.getAllUrls();
        boolean isUnique;
        if (allUrls.contains(value)) {
            isUnique = false;
        } else {
            isUnique = true;
        }
        return isUnique;
    }
        /*if(value == null){
            return false;
        }

        List<String> allUrls = URLService.getAllUrls();
        boolean isUnique;
        if(allUrls.contains(value)){
            isUnique = false;
        } else {
            isUnique = true;
        }
        return isUnique;
    }*/
}

