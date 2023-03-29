package com.example.autotravelserver.dto;


import com.example.autotravelserver.Entity.ScheduleEntity;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OnlyScheduleDto {

    private Long id;
    private String name;
    private String status;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    private LocalDateTime updateAt;
    private LocalDateTime createAt;

    public static OnlyScheduleDto fromEntity(ScheduleEntity scheduleEntity){
        return OnlyScheduleDto.builder()
                .id(scheduleEntity.getId())
                .name(scheduleEntity.getName())
                .status(scheduleEntity.getStatus())
                .build();
    }
}
