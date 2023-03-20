package com.example.autotravelserver.dto;


import com.example.autotravelserver.Entity.ScheduleEntity;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OnlyScheduleDto {

    private Long id;
    private String name;
    private String status;

    public static OnlyScheduleDto fromEntity(ScheduleEntity scheduleEntity){
        return OnlyScheduleDto.builder()
                .id(scheduleEntity.getId())
                .name(scheduleEntity.getName())
                .status(scheduleEntity.getStatus())
                .build();
    }
}
