package com.example.tour.model.repository;

import com.example.tour.model.entity.Tour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TourRepository extends JpaRepository<Tour, Long> {
    Tour findByContentId(String contentId);
}
