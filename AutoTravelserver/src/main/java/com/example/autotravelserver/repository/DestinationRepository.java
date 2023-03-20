package com.example.autotravelserver.repository;

import com.example.autotravelserver.Entity.DestinationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DestinationRepository extends JpaRepository<DestinationEntity, Long> {

    List<DestinationEntity> findAllByMemberEntity_IdAndScheduleEntity_Id(Long memberId, Long scheduleId);

    DestinationEntity findFirstByScheduleEntity_IdOrderByCreatedAtDesc(Long scheduleId);

     DestinationEntity findFirstByScheduleEntity_IdOrderByCreatedAtAsc(Long scheduleId);
   /* LocalDateTime findTopByScheduleEntity_IdAndOrderByCreatedAtDesc(Long scheduleId,
                                                                     @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime date);
    LocalDateTime findTopByScheduleEntity_IdAndOrderByCreatedAtAsc(Long scheduleId,
                                                                    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime date);
*/
}
