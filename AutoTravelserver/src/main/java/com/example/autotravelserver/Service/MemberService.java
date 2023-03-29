package com.example.autotravelserver.Service;

import com.example.autotravelserver.dto.Auth;
import com.example.autotravelserver.Entity.MemberEntity;
import com.example.autotravelserver.exception.TravelException;
import com.example.autotravelserver.repository.MemberRepository;
import com.example.autotravelserver.type.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class MemberService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.memberRepository.findByUsername(username)
                .orElseThrow(() -> new TravelException(ErrorCode.USER_NOT_FOUND));
    }

    public MemberEntity register(Auth.SignUp member) {
        boolean exists = this.memberRepository.existsByUsername(member.getUsername());
        if (exists) {
            throw new TravelException(ErrorCode.DUPLICATE_USER_ID);
        }

        member.setPassword(this.passwordEncoder.encode(member.getPassword()));
        var result = this.memberRepository.save(member.toEntity());
        return result;
    }

    public MemberEntity authenticate(Auth.SignIn member) {

        var user = this.memberRepository.findByUsername(member.getUsername())
                                    .orElseThrow(() -> new TravelException(ErrorCode.USER_NOT_FOUND));

        if (!this.passwordEncoder.matches(member.getPassword(), user.getPassword())){
            throw new TravelException(ErrorCode.NOT_SAME_PW);
        }

        return user;
    }
}
