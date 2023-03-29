package com.example.autotravelserver.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    USER_NOT_FOUND("해당 사용자가 없습니다."),
    DUPLICATE_USER_ID("이미 존재하는 ID입니다."),
    MAX_SCHEDULE_PER_MEMBER_10("사용자 최대 일정은 10개 입니다."),
    RANGE_NOT_ALLOWED_REGION("경도, 위도가 대한민국을 벗어났습니다."),
    SCHEDULE_NOT_FOUND("해당 일정을 찾을 수 없습니다."),
    DESTINATION_NOT_FOUND("해당 여행지가 없습니다."),
    NOT_EXIST_DATE("날짜 데이터가 존재하지 않습니다."),
    NOT_SAME_PW("비밀번호가 일치하지 않습니다.");

    private final String description;
}
