package com.example.autotravelserver.tour;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TourEntity {

    @Id
    @GeneratedValue
    private Long Id;

    private String contentId;
    private String type;
    private String title;
    private String address;
    private String lng;
    private String lat;
    private String description;

}
