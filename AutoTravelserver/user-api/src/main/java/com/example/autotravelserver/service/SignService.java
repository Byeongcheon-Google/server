package com.example.autotravelserver.service;

import com.example.autotravelserver.model.dto.SignInDto;
import com.example.autotravelserver.model.dto.SignUpDto;

public interface SignService {

    SignUpDto.SignUpResponseDto signUp(SignUpDto.SignUpRequestDto member);

    SignInDto.SignInResponseDto signIn(SignInDto.SignInRequestDto sign) throws RuntimeException;
}
