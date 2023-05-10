package com.example.schedule.model.entity;

import com.example.schedule.model.dto.DestinationDto;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@Entity
@Getter
@Setter
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

    public Destination toUpdate(DestinationDto.RequestDestination destination){
        this.setDestinationName(destination.getDestinationName());
        this.setAddress(destination.getAddress());
        this.setLat(destination.getLat());
        this.setLng(destination.getLng());
        this.setStayTimeHour(destination.getStayTimeHour());
        this.setDate(destination.getDate());
        this.setType(destination.getType());

        return this;
    }
}
