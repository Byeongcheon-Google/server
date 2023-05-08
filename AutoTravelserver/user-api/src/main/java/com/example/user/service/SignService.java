package com.example.user.service;

import com.example.user.model.dto.SignInDto;
import com.example.user.model.dto.SignUpDto;

public interface SignService {

    SignUpDto.SignUpResponseDto signUp(SignUpDto.SignUpRequestDto member);

    SignInDto.SignInResponseDto signIn(SignInDto.SignInRequestDto sign) throws RuntimeException;
}
