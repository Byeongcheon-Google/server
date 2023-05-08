package com.example.schedule.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ScheduleExceptionHandler {

    @ExceptionHandler(ScheduleException.class)
    public ScheduleErrorResponse handleScheduleException(ScheduleException e){

        return new ScheduleErrorResponse(
                e.getScheduleErrorCode(),
                e.getErrorMessage(),
                e.getHttpStatus()
        );
    }

}
