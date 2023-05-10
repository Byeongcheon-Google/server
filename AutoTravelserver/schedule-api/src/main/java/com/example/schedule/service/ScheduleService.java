package com.example.schedule.service;

import com.example.schedule.exception.ScheduleException;
import com.example.schedule.model.dto.DestinationDto;
import com.example.schedule.model.dto.ScheduleDto;
import com.example.schedule.model.entity.Destination;
import com.example.schedule.model.entity.Schedule;
import com.example.schedule.model.repository.DestinationRepository;
import com.example.schedule.model.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.example.schedule.type.ScheduleErrorCode.*;


@Service
@RequiredArgsConstructor
@Transactional
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final DestinationRepository destinationRepository;

    /**
     * 일정 저장 메소드
     *
     * @param memberId
     * @param requestSchedule
     * @return
     */
    public String saveSchedule(String memberId, ScheduleDto.RequestSchedule requestSchedule) {
        boolean exists = scheduleRepository.existsByMemberIdAndScheduleName(requestSchedule.getScheduleName(), memberId);

        if (exists){
            throw new ScheduleException(DUPLICATE_SCHEDULE_NAME);
        }


        validateSaveSchedule(memberId, requestSchedule.getDestinations());

        Schedule schedule = requestSchedule.toEntity(memberId);
        scheduleRepository.save(schedule);

        for (DestinationDto.RequestDestination requestDestination : requestSchedule.getDestinations()) {
            destinationRepository.save(requestDestination.toEntity(schedule));
        }

        return schedule.getScheduleName();
    }

    /**
     * 저장될 일정이 어플리케이션 정책에 위반하는가? 검사 메소드
     *
     * @param memberId
     * @param destinations
     * @throws ScheduleException
     */
    public void validateSaveSchedule(String memberId, List<DestinationDto.RequestDestination> destinations) throws ScheduleException {
        if (scheduleRepository.countScheduleByMemberId(memberId) >= 10) {
            throw new ScheduleException(MAX_SCHEDULE_PER_MEMBER_10);
        }

        validateUpdateSchedule(destinations);
    }

    /**
     * 한국 범위안인지 체크하는 함수(위도 경도)
     * @param destinations
     */
    private void validateUpdateSchedule(List<DestinationDto.RequestDestination> destinations) {

        if (destinations.size() > 9){
            throw new ScheduleException(MAX_DESTINATION_PRE_SCHEDULE_9);
        }

        for (DestinationDto.RequestDestination destination : destinations) {
            if (!((destination.getLat() > 32.0 && destination.getLat() < 44.0) &&
                    (destination.getLng() > 123.0 && destination.getLng() < 133.0))) {
                throw new ScheduleException(RANGE_NOT_ALLOWED_REGION);
            }
            defaultSet(destination);
        }
    }

    /**
     * 여행지 타입이 null이거나 empty라면 관광지로 저장
     *
     * @param destination
     */
    public void defaultSet(DestinationDto.RequestDestination destination) {
        if ("".equals(destination.getType()) || destination.getType().equals(null)) {
            destination.setType("관광지");
        }
    }

    /**
     * 사용자의 모든 일정 조회
     *
     * @param memberId
     * @return
     */
    public List<ScheduleDto.ResponseSchedule> readSchedules(String memberId) {
        List<Schedule> scheduleList =
                scheduleRepository.findByMemberId(memberId)
                        .orElseThrow(() -> new ScheduleException(SCHEDULE_NOT_FOUND));

        List<ScheduleDto.ResponseSchedule> responseScheduleDtoList = new ArrayList<>();

        for (Schedule schedule : scheduleList) {

            LocalDate start  = destinationRepository
                    .findFirstBySchedule_IdOrderByDateAsc(schedule.getId()).getDate();


            LocalDate end = destinationRepository
                    .findFirstBySchedule_IdOrderByDateDesc(schedule.getId()).getDate();

            ScheduleDto.ResponseSchedule responseSchedule = schedule.toDto(start, end);

            responseScheduleDtoList.add(responseSchedule);
        }

        return responseScheduleDtoList;
    }

    /**
     * 한 개의 일정에 모든 여행지 조회
     * @param scheduleId
     * @return
     */
    public List<DestinationDto.ResponseDestination> readDestinations(Long scheduleId) {

        List<Destination> destinationList =
                destinationRepository.findBySchedule_Id(scheduleId);


        List<DestinationDto.ResponseDestination> responseDestinationList
                = new ArrayList<>();
        for (Destination destination : destinationList){
            responseDestinationList.add(destination.toDto());
        }

        return responseDestinationList;
    }

    /**
     * 일정 삭제
     * @param scheduleId
     */

    public String deleteSchedule(Long scheduleId) {
        String response = null;

        destinationRepository.deleteBySchedule_Id(scheduleId);
        scheduleRepository.deleteById(scheduleId);

        boolean exists = destinationRepository.existsBySchedule_Id(scheduleId);
        if (exists){
            response = "fail";
        }
        else {
            response = "success";
        }
        return response;
    }

    /**
     * 일정 수정
     * @param scheduleId
     * @param requestSchedule
     * @return
     */
    public String updateSchedule(Long scheduleId, ScheduleDto.RequestSchedule requestSchedule) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new ScheduleException(SCHEDULE_NOT_FOUND));

        validateUpdateSchedule(requestSchedule.getDestinations());


        schedule.toUpdate(requestSchedule);
        scheduleRepository.save(schedule);


        List<Destination> destinationList = destinationRepository.findBySchedule_Id(scheduleId);

        for (int i = 0; i <destinationList.size(); i++) {
            destinationList.get(i).toUpdate(requestSchedule.getDestinations().get(i));
            destinationRepository.save(destinationList.get(i));
        }


        return schedule.getScheduleName();
    }

}
