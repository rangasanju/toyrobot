package com.nab.toyrobot.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class CollisionException extends Exception {

    public CollisionException(String message) {

        super(message);

    }
}