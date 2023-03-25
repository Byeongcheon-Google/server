package com.example.autotravelserver.Service;

import com.example.autotravelserver.Entity.DestinationEntity;
import com.example.autotravelserver.Entity.MemberEntity;
import com.example.autotravelserver.Entity.ScheduleEntity;
import com.example.autotravelserver.dto.*;
import com.example.autotravelserver.exception.TravelException;
import com.example.autotravelserver.repository.DestinationRepository;
import com.example.autotravelserver.repository.MemberRepository;
import com.example.autotravelserver.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.example.autotravelserver.type.ErrorCode.*;

@Service
@Transactional
@RequiredArgsConstructor
public class ScheduleService {

    private final MemberRepository memberRepository;
    private final ScheduleRepository scheduleRepository;
    private final DestinationRepository destinationRepository;

    /**
     * 일정 저장 로직함수
     * @param id memberId
     * @param name 일정이름
     * @param destinations List<Destinations> 여행지 리스트
     * @param status 일정상태
     * @return 일정 ID, 일정 이름
     */
    public OnlyScheduleDto createSchedule(Long id, String name, List<DestinationDto> destinations, String status) {
        MemberEntity member = memberRepository.findById(id)
                .orElseThrow(() -> new TravelException(USER_NOT_FOUND));

        validateCreateSchedule(member,destinations);

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

    public void validateCreateSchedule(MemberEntity memberEntity,List<DestinationDto> destinations){
        if (scheduleRepository.countByMemberEntity(memberEntity) >= 10){
            throw new TravelException(MAX_SCHEDULE_PER_MEMBER_10);
        }

        for (DestinationDto destination :destinations){
            if (!((destination.getLat() > 32.0 && destination.getLat() < 44.0)&&
                    (destination.getLng() > 123.0 && destination.getLng() < 133.0))){
                throw new TravelException(RANGE_NOT_ALLOWED_REGION);
            }
            if ("".equals(destination.getType()) || destination.getType().equals(null)){
                destination.setType("여행지");
            }
        }
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
                .orElseThrow(() -> new TravelException(SCHEDULE_NOT_FOUND));

        List<DestinationEntity> destinationEntityList =
                destinationRepository.findAllByMemberEntity_IdAndScheduleEntity_Id(
                        memberId,
                        scheduleId
                );

        DestinationEntity startDestinationEntity = destinationRepository
                .findFirstByScheduleEntity_IdOrderByDateAsc(
                        scheduleId)
                .orElseThrow(() -> new TravelException(NOT_EXIST_DATE));

        LocalDate startDate =  startDestinationEntity.getCreatedAt().toLocalDate();

        DestinationEntity endDestinationEntity = destinationRepository.findFirstByScheduleEntity_IdOrderByDateDesc(scheduleId)
                .orElseThrow(() -> new TravelException(NOT_EXIST_DATE));
        LocalDate endDate = endDestinationEntity.getCreatedAt().toLocalDate();


        List<DestinationDto> destinationDtoList = new ArrayList<>();


        for (DestinationEntity destinationEntity : destinationEntityList) {
            destinationDtoList.add(DestinationDto.fromEntity(destinationEntity));
        }

        return ScheduleDto.fromEntity(scheduleEntity, destinationDtoList, startDate, endDate);
    }


    public void deleteSchedule(Long memberId, Long scheduleId) {
        destinationRepository.deleteByMemberEntity_IdAndScheduleEntity_Id(memberId,scheduleId);
        scheduleRepository.deleteByMemberEntity_IdAndId(memberId,scheduleId);
    }


    public OnlyScheduleDto updateSchedule(UpdateSchedule.Request request) {
        MemberEntity member = memberRepository.findById(request.getMemberId())
                .orElseThrow(() -> new TravelException(USER_NOT_FOUND));

        ScheduleEntity scheduleEntity= scheduleRepository.findById(request.getScheduleId())
                .orElseThrow(()-> new TravelException(SCHEDULE_NOT_FOUND));

        scheduleEntity.setName(request.getName());
        scheduleEntity.setStatus(request.getStatus());


        for (DestinationDto destination : request.getDestinations()) {
            DestinationEntity destinationEntity = destinationRepository.findById(destination.getId())
                    .orElseThrow(()->new TravelException(DESTINATION_NOT_FOUND));
            destinationEntity.setName(destination.getName());
            destinationEntity.setAddress(destination.getAddress());
            destinationEntity.setLat(destination.getLat());
            destinationEntity.setLng(destination.getLng());
            destinationEntity.setStayTimeHour(destination.getStayTimeHour());
            destinationEntity.setDate(destination.getDate());
            destinationEntity.setType(destination.getType());
            destinationRepository.save(destinationEntity);
        }

        scheduleRepository.save(scheduleEntity);

        return OnlyScheduleDto.fromEntity(scheduleEntity);
    }
}
