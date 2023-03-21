package com.example.autotravelserver.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    USER_NOT_FOUND("사용자가 없습니다."),
    MAX_SCHEDULE_PER_MEMBER_10("사용자 최대 일정은 10개 입니다."),
    RANGE_NOT_ALLOWED_REGION("경도, 위도가 대한민국을 벗어났습니다.")
    ;

    private final String description;
}
