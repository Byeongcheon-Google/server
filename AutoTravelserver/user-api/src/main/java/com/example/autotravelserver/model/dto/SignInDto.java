package com.example.autotravelserver.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;



public class SignInDto {

    @Getter
    public static class SignInRequestDto{
        private String memberId;
        private String password;
    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class SignInResponseDto extends SignUpDto.SignUpResponseDto {
        private String token;
    }
    
}
