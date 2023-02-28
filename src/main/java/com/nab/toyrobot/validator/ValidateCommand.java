package com.nab.toyrobot.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CommandValidator.class)
@Documented
public @interface ValidateCommand {
    String message() default "Invalid Command";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}