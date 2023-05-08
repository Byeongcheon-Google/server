package com.example.schedule.type;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ScheduleErrorCode {
    DUPLICATE_SCHEDULE_NAME(HttpStatus.BAD_REQUEST,"일정의 이름이 중복되었습니다."),
    MAX_SCHEDULE_PER_MEMBER_10(HttpStatus.BAD_REQUEST,"사용자 최대 일정은 10개 입니다."),
    RANGE_NOT_ALLOWED_REGION(HttpStatus.BAD_REQUEST,"경도, 위도가 대한민국을 벗어났습니다."),
    SCHEDULE_NOT_FOUND(HttpStatus.BAD_REQUEST,"해당 일정을 찾을 수 없습니다."),
    DESTINATION_NOT_FOUND(HttpStatus.BAD_REQUEST,"해당 여행지가 없습니다."),
    NOT_EXIST_DATE(HttpStatus.BAD_REQUEST,"날짜 데이터가 존재하지 않습니다.")
    ;

    private HttpStatus httpStatus;
    private String description;
}
