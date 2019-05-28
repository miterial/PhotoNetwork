package com.course.PhotoNetwork.controller.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target( { METHOD, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = NotEmptyMultipartValidator.class)
@Documented
public @interface NotEmptyMultipart {

    String message() default "Файл изображения не должен быть пустым";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};


}
