package com.example.user.controller;

import com.example.user.exception.SignException;
import com.example.user.model.dto.SignInDto;
import com.example.user.model.dto.SignUpDto;
import com.example.user.service.Impl.SignServiceImpl;
import com.example.user.type.SignErrorCode;
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


}
