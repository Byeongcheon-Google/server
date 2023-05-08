package com.example.schedule.exception;

import com.example.schedule.type.ScheduleErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ScheduleException extends RuntimeException{

    private ScheduleErrorCode scheduleErrorCode;
    private String errorMessage;
    private HttpStatus httpStatus;

    public ScheduleException(ScheduleErrorCode scheduleErrorCode){
        this.scheduleErrorCode = scheduleErrorCode;
        this.errorMessage = scheduleErrorCode.getDescription();
        this.httpStatus = scheduleErrorCode.getHttpStatus();
    }
}
