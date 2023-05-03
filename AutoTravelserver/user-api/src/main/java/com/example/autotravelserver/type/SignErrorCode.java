package com.example.autotravelserver.type;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SignErrorCode {

    USER_NOT_FOUND(HttpStatus.BAD_REQUEST,"가입되지 않은 사용자입니다."),
    DUPLICATE_USER_ID(HttpStatus.BAD_REQUEST,"중복된 ID 입니다."),
    NOT_SAME_PW(HttpStatus.BAD_REQUEST,"비밀번호가 일치하지 않습니다."),
    ACCESS_ISNOT_ALLOWED(HttpStatus.BAD_REQUEST, "접근이 허용되지 않습니다.")
    ;

    private final HttpStatus httpStatus;
    private final String description;

}
