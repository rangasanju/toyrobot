package com.nab.toyrobot.validator;

import com.nab.toyrobot.request.RequestDto;
import com.nab.toyrobot.util.CommandUtil;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class RequestDtoValidator implements ConstraintValidator<ValidRequestDto, RequestDto> {

    @Override
    public boolean isValid(RequestDto requestDto, ConstraintValidatorContext context) {
        if (requestDto == null) {
            return false;
        }

        // Validate the command field
        if (requestDto.getCommand() == null || requestDto.getCommand().isEmpty())
            return false;

        if (requestDto.getCommand().equals(CommandUtil.getCommands().get(0))) {
            // Validate the x and y fields
            if (requestDto.getX() == null || requestDto.getY() == null) {
                return false;
            }
            // Validate the direction field
            if (requestDto.getDirection() == null) {
                return false;
            }
        }
        return true;
    }

}