package com.example.user.exception;

import com.example.user.type.SignErrorCode;
import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SignErrorResponse {
    private SignErrorCode errorCode;
    private String errorMessage;
    private HttpStatus httpStatus;
}
