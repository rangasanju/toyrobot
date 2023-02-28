package com.nab.toyrobot.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class TableInitializationException extends Exception{

    public TableInitializationException(String message) {

        super(message);

    }
}