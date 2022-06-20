package com.engine.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class InvalidReviewException extends Exception{

    private static final long serialVersionUID = 1L;

    public InvalidReviewException(String message){
        super(message);
    }
}