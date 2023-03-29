package com.example.autotravelserver.dto;

import com.example.autotravelserver.Entity.DestinationEntity;
import lombok.*;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DestinationDto {


    private Long id;
    @NotEmpty
    private String name;
    @NotEmpty
    private String address;
    @NotNull
    private Double lat;

    @NotNull
    private Double lng;
    private Integer stayTimeHour;
    @NotNull
    private LocalDate date;
    private String type;

    public static DestinationDto fromEntity(DestinationEntity destinationEntity) {
        return DestinationDto.builder()
                .id(destinationEntity.getId())
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
