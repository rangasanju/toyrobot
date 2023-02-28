package com.nab.toyrobot.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class EdgeDetectedException extends Exception{

    public EdgeDetectedException(String message) {

        super(message);

    }
}