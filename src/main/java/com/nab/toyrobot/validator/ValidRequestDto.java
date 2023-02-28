package com.nab.toyrobot.validator;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = RequestDtoValidator.class)
@Documented
public @interface ValidRequestDto {
    String message() default "Invalid Request DTO";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}