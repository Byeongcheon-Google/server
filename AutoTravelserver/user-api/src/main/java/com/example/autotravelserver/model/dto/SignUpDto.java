package com.example.autotravelserver.model.dto;

import com.example.autotravelserver.model.entity.Member;
import lombok.*;

import java.util.List;

public class SignUpDto {

    @Getter
    @Setter
    public static class SignUpRequestDto{
        private String memberId;
        private String password;
        private String memberName;
        private List<String> roles;

        public Member toEntity(){
            return Member.builder()
                    .memberId(this.memberId)
                    .password(this.password)
                    .memberName(this.memberName)
                    .roles(this.roles)
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
