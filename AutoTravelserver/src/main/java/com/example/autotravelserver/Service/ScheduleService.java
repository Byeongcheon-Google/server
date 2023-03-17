package com.example.autotravelserver.Service;

import com.example.autotravelserver.Entity.DestinationEntity;
import com.example.autotravelserver.Entity.MemberEntity;
import com.example.autotravelserver.Entity.ScheduleEntity;
import com.example.autotravelserver.dto.DestinationDto;
import com.example.autotravelserver.dto.OnlyScheduleDto;
import com.example.autotravelserver.dto.ScheduleDto;
import com.example.autotravelserver.repository.DestinationRepository;
import com.example.autotravelserver.repository.MemberRepository;
import com.example.autotravelserver.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ScheduleService {

    private final MemberRepository memberRepository;
    private final ScheduleRepository scheduleRepository;
    private final DestinationRepository destinationRepository;

    public OnlyScheduleDto createSchedule(Long id, String name, List<DestinationDto> destinations, String status) {
        MemberEntity member = memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException());

        ScheduleEntity scheduleEntity = ScheduleEntity.builder()
                .memberEntity(member)
                .name(name)
                .status(status)
                .build();

        List<DestinationEntity> destinationEntities = new ArrayList<>();
        for (DestinationDto destination : destinations) {
            DestinationEntity destinationEntity = DestinationEntity.builder()
                    .name(destination.getName())
                    .address(destination.getAddress())
                    .lat(destination.getLat())
                    .lng(destination.getLng())
                    .stayTimeHour(destination.getStayTimeHour())
                    .date(destination.getDate())
                    .type(destination.getType())
                    .build();
            destinationEntity.setMemberEntity(member);
            destinationEntity.setScheduleEntity(scheduleEntity);
            destinationEntities.add(destinationEntity);

            destinationRepository.save(destinationEntity);
        }

        scheduleRepository.save(scheduleEntity);

        return OnlyScheduleDto.fromEntity(scheduleEntity);
    }

    public List<OnlyScheduleDto> readSchedules(Long id) {
        List<ScheduleEntity> scheduleEntityList =
                scheduleRepository.findAllByMemberEntity_Id(id);
        List<OnlyScheduleDto> onlyScheduleDtoList = new ArrayList<>();

        for (ScheduleEntity scheduleEntity : scheduleEntityList) {
            onlyScheduleDtoList.add(OnlyScheduleDto.fromEntity(scheduleEntity));
        }
        return onlyScheduleDtoList;
    }

    public ScheduleDto readDestinations(Long memberId, Long scheduleId) {
        ScheduleEntity scheduleEntity = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new RuntimeException());

        List<DestinationEntity> destinationEntityList =
                destinationRepository.findAllByMemberEntity_IdAndScheduleEntity_Id(
                        memberId,
                        scheduleId
                );

        DestinationEntity startDestinationEntity = destinationRepository
                .findFirstByScheduleEntity_IdOrderByCreatedAtAsc(
                        scheduleId

                );

        LocalDate startDate =  startDestinationEntity.getCreatedAt().toLocalDate();

        DestinationEntity endDestinationEntity = destinationRepository
                .findFirstByScheduleEntity_IdOrderByCreatedAtDesc(
                        scheduleId

                );

        LocalDate endDate = endDestinationEntity.getCreatedAt().toLocalDate();


        List<DestinationDto> destinationDtoList = new ArrayList<>();


        for (DestinationEntity destinationEntity : destinationEntityList) {
            destinationDtoList.add(DestinationDto.fromEntity(destinationEntity));
        }

        return ScheduleDto.fromEntity(scheduleEntity, destinationDtoList, startDate, endDate);
    }


    public void deleteSchedule(Long memberId, Long scheduleId) {
        
    }
}
