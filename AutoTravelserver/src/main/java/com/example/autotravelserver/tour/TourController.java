package com.example.autotravelserver.tour;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tour")
public class TourController {

    private final TourService tourService;
    @GetMapping
    public void saveTour(){
        tourService.saveTour();
    }

    @GetMapping("/description")
    public void saveDescriptionTour(){
        tourService.saveTourDescription();
    }
}
