package com.example.autotravelserver.dto;

import lombok.*;

import java.util.List;

public class ReadSchedule {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Response{
        private Long scheduleId;
        private String name;
        private String status;
        private List<DestinationDto> destinationList;

    }
}
