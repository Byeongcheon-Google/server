package com.example.autotravelserver.dto;


import lombok.*;

import java.util.List;

public class UpdateSchedule {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {
        private Long memberId;
        private Long scheduleId;
        private String name;
        private List<DestinationDto> destinations;
        private String status;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response{
        private Long scheduleId;
        private String name;

        public static Response from(OnlyScheduleDto scheduleDto){
            return Response.builder()
                    .scheduleId(scheduleDto.getId())
                    .name(scheduleDto.getName())
                    .build();
        }
    }
}
