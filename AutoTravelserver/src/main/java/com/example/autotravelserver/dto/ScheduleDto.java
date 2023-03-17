package com.example.autotravelserver.dto;


import com.example.autotravelserver.Entity.ScheduleEntity;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScheduleDto {

    private Long id;
    private String name;
    private String status;
    private List<DestinationDto> destinationDtoList;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDate startDate;
    private LocalDate endDate;

    public static ScheduleDto fromEntity(
            ScheduleEntity scheduleEntity,
            List<DestinationDto> destinationDtoList,
            LocalDate startDate,
            LocalDate endDate
    ){
        return ScheduleDto.builder()
                .id(scheduleEntity.getId())
                .name(scheduleEntity.getName())
                .status(scheduleEntity.getStatus())
                .createdAt(scheduleEntity.getCreatedAt())
                .updatedAt(scheduleEntity.getUpdatedAt())
                .startDate(startDate)
                .endDate(endDate)
                .destinationDtoList(destinationDtoList)
                .build();
    }
}
