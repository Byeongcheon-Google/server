package com.example.schedule.exception;

import com.example.schedule.type.ScheduleErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleErrorResponse {

    private ScheduleErrorCode errorCode;
    private String errorMessage;
    private HttpStatus httpStatus;

}
