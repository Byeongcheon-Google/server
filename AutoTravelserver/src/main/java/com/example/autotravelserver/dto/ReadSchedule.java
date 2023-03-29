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
public class ReadSchedule {

        private Long scheduleId;
        private String name;
        private String status;
        private LocalDate startDate;
        private LocalDate endDate;

        public static ReadSchedule from(ScheduleEntity scheduleEntity, LocalDate pstartDate, LocalDate pendDate){
            return ReadSchedule.builder()
                    .scheduleId(scheduleEntity.getId())
                    .name(scheduleEntity.getName())
                    .status(scheduleEntity.getStatus())
                    .startDate(pstartDate)
                    .endDate(pendDate)
                    .build();
        }
}

