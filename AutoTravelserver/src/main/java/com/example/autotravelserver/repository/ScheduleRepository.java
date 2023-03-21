package com.example.autotravelserver.repository;

import com.example.autotravelserver.Entity.MemberEntity;
import com.example.autotravelserver.Entity.ScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScheduleRepository extends JpaRepository<ScheduleEntity,Long> {

    List<ScheduleEntity> findAllByMemberEntity_Id(Long memberId);

    Long countByMemberEntity(MemberEntity member);

    @Transactional
    void deleteByMemberEntity_IdAndId(Long memberId, Long scheduleId);
}
