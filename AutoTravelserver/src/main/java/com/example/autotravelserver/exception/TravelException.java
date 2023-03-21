package com.example.autotravelserver.exception;

import com.example.autotravelserver.type.ErrorCode;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TravelException extends RuntimeException {
    private ErrorCode errorCode;
    private String errorMessage;

    public TravelException(ErrorCode errorCode){
        this.errorCode = errorCode;
        this.errorMessage = errorCode.getDescription();
    }
}
