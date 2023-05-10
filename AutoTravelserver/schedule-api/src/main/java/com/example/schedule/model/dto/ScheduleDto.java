package com.example.schedule.model.dto;

import com.example.schedule.model.entity.Schedule;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class ScheduleDto {

    @Getter
    public static class RequestSchedule{
        private String scheduleName;
        private List<DestinationDto.RequestDestination> destinations;

        public Schedule toEntity(String memberId){
            return Schedule.builder()
                    .scheduleName(this.scheduleName)
                    .memberId(memberId)
                    .build();

        }
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ResponseSchedule{
        private Long scheduleId;
        private String scheduleName;
        private LocalDate startDate;
        private LocalDate endDate;
        private LocalDateTime updatedAt;


    }
}
