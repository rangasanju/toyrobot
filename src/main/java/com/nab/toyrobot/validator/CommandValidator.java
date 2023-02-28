package com.nab.toyrobot.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class CommandValidator implements ConstraintValidator<ValidateCommand, String> {

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {

        String[] commands = s.split(" ");

        if(commands.length < 1)
            return false;
//
//        try {
//            Commands.valueOf(commands[0]);
//        } catch (IllegalArgumentException ex) {
//            return false;
//        }
//
//        // Validation for PLACE Command
//        if (commands[0].equals(Commands.PLACE.toString())) {
//
//            if(commands.length < 4)
//                return false;
//
//            // Validate the x and y fields
//
//            try {
//                Integer.parseInt(commands[1]);
//                Integer.parseInt(commands[2]);
//            } catch (NumberFormatException e) {
//                return false;
//            }
//
//        }

        return true;
    }
}