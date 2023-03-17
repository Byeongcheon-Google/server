package com.example.autotravelserver.dto;

import com.example.autotravelserver.Entity.MemberEntity;
import lombok.Data;

public class Auth {

    @Data
    public static class SignIn{
        private String username;
        private String password;
    }


    @Data
    public static class SignUp{
        private String username;
        private String password;

        public MemberEntity toEntity(){
            return MemberEntity.builder()
                            .username(this.username)
                            .password(this.password)
                            .build();
        }
    }
}
