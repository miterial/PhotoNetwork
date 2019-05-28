package com.course.PhotoNetwork.controller.validation;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.io.IOException;

public class NotEmptyMultipartValidator implements ConstraintValidator<NotEmptyMultipart, MultipartFile> {

    public boolean isValid(MultipartFile object, ConstraintValidatorContext constraintContext) {

        try {
            return !object.getName().isEmpty() && object.getBytes().length != 0;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;

    }

}