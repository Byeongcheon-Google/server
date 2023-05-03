package com.example.autotravelserver.exception;

import com.example.autotravelserver.type.SignErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class SignException extends RuntimeException {
    private SignErrorCode errorCode;
    private String errorMessage;
    private HttpStatus httpStatus;

    public SignException(SignErrorCode errorCode){
        this.errorCode = errorCode;
        this.errorMessage = errorCode.getDescription();
        this.httpStatus = errorCode.getHttpStatus();
    }
}
