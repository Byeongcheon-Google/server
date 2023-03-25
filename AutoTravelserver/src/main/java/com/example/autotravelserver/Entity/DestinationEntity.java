package com.example.autotravelserver.Entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class DestinationEntity {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String address;
    private Double lat;
    private Double lng;
    private Integer stayTimeHour;
    private LocalDate date;
    private String type;

    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;
    @ManyToOne
    private ScheduleEntity scheduleEntity;

    @ManyToOne
    private MemberEntity memberEntity;


}
