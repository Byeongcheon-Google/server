package com.example.user.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CommonResponse {

    SUCCESS(1, "Success"),
    FAIL(-1, "FAIL")
    ;

    private final int code;
    private final String msg;
}
