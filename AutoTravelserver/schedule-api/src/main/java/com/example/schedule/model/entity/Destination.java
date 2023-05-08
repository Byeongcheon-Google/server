package com.example.schedule.model.entity;

import com.example.schedule.model.dto.DestinationDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@EntityListeners(AbstractMethodError.class)
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Destination {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String destinationName;
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
    private Schedule schedule;

    public DestinationDto.ResponseDestination toDto() {
        return DestinationDto.ResponseDestination.builder()
                .destinationName(this.destinationName)
                .address(this.address)
                .lat(this.lat)
                .lng(this.lng)
                .stayTimeHour(this.stayTimeHour)
                .date(this.date)
                .type(this.type)
                .build();
    }
}
