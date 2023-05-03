package com.example.autotravelserver.exception;

import com.example.autotravelserver.type.SignErrorCode;
import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SignErrorResponse {
    private SignErrorCode errorCode;
    private String errorMessage;
    private HttpStatus httpStatus;
}
