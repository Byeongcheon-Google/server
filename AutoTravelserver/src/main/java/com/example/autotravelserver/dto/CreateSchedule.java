package com.example.autotravelserver.dto;


import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.util.List;

public class CreateSchedule {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {
        private Long id;
        @NotEmpty
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
        private Long id;
        private String name;

        public static Response from(OnlyScheduleDto scheduleDto){
          return Response.builder()
                    .id(scheduleDto.getId())
                    .name(scheduleDto.getName())
                    .build();
        }
    }
}
