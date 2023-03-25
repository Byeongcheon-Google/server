package com.example.autotravelserver.controller;

import com.example.autotravelserver.Service.MemberService;
import com.example.autotravelserver.dto.Auth;
import com.example.autotravelserver.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final MemberService memberService;

    private final TokenProvider tokenProvider;

    @PostMapping(value = "/signup")
    public ResponseEntity<?> signup(@RequestBody Auth.SignUp request){
        //회원가입 API
        var result = this.memberService.register(request);
        return ResponseEntity.ok(result);
    }

    @PostMapping(value ="/signin")
    public ResponseEntity<?> signin(@RequestBody Auth.SignIn request){
        //로그인 API
        var member = this.memberService.authenticate(request);
        var token = this.tokenProvider.generateToken(member.getUsername(), member.getRoles());
        return ResponseEntity.ok(token);

    }
}
