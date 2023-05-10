package com.example.schedule.model.repository;

import com.example.schedule.model.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {


    boolean existsByMemberIdAndScheduleName(String scheduleName, String memberId);

    Integer countScheduleByMemberId(String memberId);

    Optional<List<Schedule>> findByMemberId(String memberId);




}
