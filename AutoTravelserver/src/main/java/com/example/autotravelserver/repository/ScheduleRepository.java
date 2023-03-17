package com.example.autotravelserver.repository;

import com.example.autotravelserver.Entity.ScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScheduleRepository extends JpaRepository<ScheduleEntity,Long> {

    List<ScheduleEntity> findAllByMemberEntity_Id(Long memberId);



}
