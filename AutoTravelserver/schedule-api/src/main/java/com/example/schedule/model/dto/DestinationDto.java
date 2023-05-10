package com.example.schedule.model.dto;

import com.example.schedule.model.entity.Destination;
import com.example.schedule.model.entity.Schedule;
import lombok.*;

import java.time.LocalDate;

public class DestinationDto {

    @Getter
    @Setter
    public static class RequestDestination{
        private String destinationName;
        private String address;
        private Double lat;
        private Double lng;
        private Integer stayTimeHour;
        private LocalDate date;
        private String type;

        public Destination toEntity(Schedule schedule){
            return Destination.builder()
                    .destinationName(this.destinationName)
                    .address(this.address)
                    .lat(this.lat)
                    .lng(this.lng)
                    .stayTimeHour(this.stayTimeHour)
                    .date(this.date)
                    .type(this.type)
                    .schedule(schedule)
                    .build();
        }
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ResponseDestination{
        private String destinationName;
        private String address;
        private Double lat;
        private Double lng;
        private Integer stayTimeHour;
        private LocalDate date;
        private String type;
    }


}
