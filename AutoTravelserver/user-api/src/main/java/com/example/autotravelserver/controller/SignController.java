package com.example.autotravelserver.controller;

import com.example.autotravelserver.exception.SignException;
import com.example.autotravelserver.model.dto.SignInDto;
import com.example.autotravelserver.model.dto.SignUpDto;
import com.example.autotravelserver.service.Impl.SignServiceImpl;
import com.example.autotravelserver.type.SignErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sign")
@RequiredArgsConstructor
public class SignController {

    private final SignServiceImpl signService;
    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody SignUpDto.SignUpRequestDto member){
        var result = signService.signUp(member);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@RequestBody SignInDto.SignInRequestDto sign){
        var result = signService.signIn(sign);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/exception")
    public ResponseEntity<?> exception(){
        throw new SignException(SignErrorCode.ACCESS_ISNOT_ALLOWED);
    }

}
