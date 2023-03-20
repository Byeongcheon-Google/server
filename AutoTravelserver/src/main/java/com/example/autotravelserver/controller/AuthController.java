package com.example.autotravelserver.controller;

import com.example.autotravelserver.Service.MemberService;
import com.example.autotravelserver.dto.Auth;
import com.example.autotravelserver.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.security.sasl.AuthenticationException;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final MemberService memberService;

    private final TokenProvider tokenProvider;

    @PostMapping(value = "/signup")
    public ResponseEntity<?> signup(@RequestBody Auth.SignUp request) {
        //회원가입 API
        try {
            var result = this.memberService.register(request);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping(value = "/signin")
    public ResponseEntity<?> signin(@RequestBody Auth.SignIn request) {
        //로그인 API
        try {
            var member = this.memberService.authenticate(request);
            var token = this.tokenProvider.generateToken(member);
            return ResponseEntity.ok(token);
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }


    @GetMapping(value = "/test")
    public ResponseEntity<?> test() {
        return ResponseEntity.ok("OK");
    }
}
