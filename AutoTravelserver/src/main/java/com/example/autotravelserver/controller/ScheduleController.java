package com.example.autotravelserver.controller;


import com.example.autotravelserver.Service.ScheduleService;
import com.example.autotravelserver.dto.*;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor

public class ScheduleController {

    private final ScheduleService scheduleService;

    @ApiOperation("일정 생성 API")
    @PostMapping("/schedule")
    @PreAuthorize("hasRole('USER')")
    public CreateSchedule.Response createSchedule(
            @RequestBody  @ApiParam(value = "날짜 형식: yyyy-MM-dd") CreateSchedule.Request request
    ){
        return CreateSchedule.Response.from(
                scheduleService.createSchedule(
                request.getId(),
                request.getName(),
                request.getDestinations(),
                request.getStatus()
        ));
    }

    /**
     * 일정 목록 조회
     * @param id memberId
     * @return
     */
    @ApiOperation("사용자의 모든일정 조회 API")
    @GetMapping("/schedule")
    @PreAuthorize("hasRole('USER')")
    public List<ReadSchedule> readSchedules(
            @RequestParam Long id
    ){
        return scheduleService.readSchedules(id);
    }

    /**
     * 한 개의 일정과 그 안에 여행지들 조회
     * @param memberId 유저ID
     * @param scheduleId 일정ID
     * @return
     */
    @ApiOperation("선택한 일정 한개 조회 API")
    @GetMapping("/schedules")
    @PreAuthorize("hasRole('USER')")
    public ScheduleDto readDestinations(
            @RequestParam Long memberId,
            @RequestParam Long scheduleId
    ){
        return scheduleService.readDestinations(memberId, scheduleId);

    }

    /**
     * 삭제 API
     * @param memberId
     * @param scheduleId
     * @return
     */
    @ApiOperation("일정 삭제 API")
    @DeleteMapping("/schedule/delete")
    @PreAuthorize("hasRole('USER')")
    public Long deleteSchedule(
            @RequestParam Long memberId,
            @RequestParam Long scheduleId

    ){
        scheduleService.deleteSchedule(memberId, scheduleId);
        return 1L;
    }


    /**
     * 수정 API
     * @param request
     * @return
     */
    @ApiOperation("일정 수정 API")
    @PutMapping("/schedule/update")
    @PreAuthorize("hasRole('USER')")
    public UpdateSchedule.Response updateSchedule(
            @RequestBody UpdateSchedule.Request request
    ){
        return UpdateSchedule.Response.from(
                scheduleService.updateSchedule(request));
    }
}
