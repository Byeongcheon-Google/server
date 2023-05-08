package com.example.schedule.controller;

import com.example.jwt.config.JwtTokenProvider;
import com.example.jwt.config.MemberVo;
import com.example.schedule.model.dto.ScheduleDto;
import com.example.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schedule")
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final JwtTokenProvider jwtTokenProvider;


    @PostMapping
    public ResponseEntity<?> saveSchedule(
            @RequestHeader(name = "X-AUTH-TOKEN") String token,
            @RequestBody ScheduleDto.RequestSchedule requestSchedule
    ){
        MemberVo vo = jwtTokenProvider.getMemberVo(token);
        var result = scheduleService.saveSchedule(vo.getMemberId(), requestSchedule);
        return ResponseEntity.ok(result);
    }

    @GetMapping
    public ResponseEntity<?> readSchedule(
            @RequestHeader(name = "X-AUTH-TOKEN") String token,
            @RequestParam String memberId
    ){
        MemberVo vo = jwtTokenProvider.getMemberVo(token);
        var result = scheduleService.readSchedules(vo.getMemberId());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/destination")
    public ResponseEntity<?> readDestination(
            @RequestHeader(name = "X-AUTH-TOKEN") String token,
            @RequestParam Long scheduleId
    ){
        MemberVo vo = jwtTokenProvider.getMemberVo(token);
        var result = scheduleService.readDestinations(scheduleId);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/change")
    public ResponseEntity<?> updateDestination(
            @RequestHeader(name = "X-AUTH-TOKEN") String token,
            @RequestParam Long scheduleId,
            @RequestBody ScheduleDto.RequestSchedule requestSchedule
    ){
        var result = scheduleService.updateSchedule(scheduleId, requestSchedule);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/remove")
    public ResponseEntity<?> deleteDestination(
            @RequestHeader(name = "X-AUTH-TOKEN") String token,
            @RequestParam Long scheduleId
    ){
        var result = scheduleService.deleteSchedule(scheduleId);
        return ResponseEntity.ok(result);
    }
}
