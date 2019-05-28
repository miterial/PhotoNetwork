package com.course.PhotoNetwork.controller.validation;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class BirthdayValidator implements ConstraintValidator<Birthday, String> {

    public boolean isValid(String object, ConstraintValidatorContext constraintContext) {

        try {
            if(object == null || object.isEmpty()) {
                return true;
            }

            Date date = new SimpleDateFormat("dd.MM.yyyy").parse(object.split( " ")[0]);

            Calendar c = Calendar.getInstance();
            c.setTime(new Date());
            c.add(Calendar.YEAR, -6);

            if(date.before(c.getTime()))
                return true;

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;

    }

}