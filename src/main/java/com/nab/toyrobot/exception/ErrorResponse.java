package com.nab.toyrobot.exception;


import lombok.Data;

@Data
public class ErrorResponse {
    private int errorCode;
    private String message;
    private String field;

}
