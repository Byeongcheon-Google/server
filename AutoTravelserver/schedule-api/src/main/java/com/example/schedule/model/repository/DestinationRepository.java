package com.example.schedule.model.repository;

import com.example.schedule.model.entity.Destination;
import com.example.schedule.model.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface DestinationRepository extends JpaRepository<Destination, Long> {


     Destination findFirstBySchedule_IdOrderByDateAsc(Long scheduleId);

     Destination findFirstBySchedule_IdOrderByDateDesc(Long scheduleId);

    List<Destination> findBySchedule_Id(Long scheduleId);

    @Transactional
    void deleteBySchedule_Id(Long scheduleId);


    boolean existsBySchedule_Id(Long scheduleId);
}
