package com.example.user.exception;


import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class SignExceptionHandler {

    @ExceptionHandler(SignException.class)
    public SignErrorResponse handleSingException(SignException e){
        return new SignErrorResponse(
                e.getErrorCode(),
                e.getErrorMessage(),
                e.getHttpStatus()
        );
    }
}
