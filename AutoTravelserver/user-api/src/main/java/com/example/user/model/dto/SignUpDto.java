package com.example.user.model.dto;

import com.example.user.model.entity.Member;
import lombok.*;

import java.util.List;

public class SignUpDto {

    @Getter
    @Setter
    public static class SignUpRequestDto{
        private String memberId;
        private String password;
        private String memberName;

        public Member toEntity(String password){
            return Member.builder()
                    .memberId(this.memberId)
                    .password(password)
                    .memberName(this.memberName)
                    .build();
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SignUpResponseDto {
        private boolean success;
        private int code;
        private String msg;

    }


}
