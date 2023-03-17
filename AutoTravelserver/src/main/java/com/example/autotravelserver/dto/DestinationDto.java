package com.example.autotravelserver.dto;

import com.example.autotravelserver.Entity.DestinationEntity;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DestinationDto {

    private String name;
    private String address;
    private Double lat;
    private Double lng;
    private Integer stayTimeHour;
    private LocalDate date;
    private String type;

    public static DestinationDto fromEntity(DestinationEntity destinationEntity) {
        return DestinationDto.builder()
                .name(destinationEntity.getName())
                .address(destinationEntity.getAddress())
                .lat(destinationEntity.getLat())
                .lng(destinationEntity.getLng())
                .stayTimeHour(destinationEntity.getStayTimeHour())
                .date(destinationEntity.getDate())
                .type(destinationEntity.getType())
                .build();
    }
}
