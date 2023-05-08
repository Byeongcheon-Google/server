package com.example.schedule.model.entity;


import com.example.schedule.model.dto.ScheduleDto;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String scheduleName;
    private String scheduleStatus;
    private String memberId;

    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

    public ScheduleDto.ResponseSchedule toDto(LocalDate startDate, LocalDate endDate){
        return ScheduleDto.ResponseSchedule.builder()
                .scheduleId(this.getId())
                .scheduleName(this.getScheduleName())
                .updatedAt(this.getUpdatedAt())
                .startDate(startDate)
                .endDate(endDate)
                .build();
    }
}
