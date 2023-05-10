package com.example.tour.model.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Tour {

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