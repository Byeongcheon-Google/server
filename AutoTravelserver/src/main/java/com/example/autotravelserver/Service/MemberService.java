package com.example.autotravelserver.Service;

import com.example.autotravelserver.dto.Auth;
import com.example.autotravelserver.Entity.MemberEntity;
import com.example.autotravelserver.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.security.sasl.AuthenticationException;

@Slf4j
@Service
@AllArgsConstructor
public class MemberService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.memberRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("coludn't not find user->" + username));
    }

    public MemberEntity register(Auth.SignUp member) {
        boolean exists = this.memberRepository.existsByUsername(member.getUsername());
        if (exists) {
            throw new IllegalArgumentException("이미 사용 중인 아이디 입니다.");
        }

        member.setPassword(this.passwordEncoder.encode(member.getPassword()));
        var result = this.memberRepository.save(member.toEntity("ROLE_USER"));
        return result;
    }

    public MemberEntity authenticate(Auth.SignIn member) throws AuthenticationException {

        var user = this.memberRepository.findByUsername(member.getUsername())
                                    .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 ID입니다."));

        if (!this.passwordEncoder.matches(member.getPassword(), user.getPassword())){
            throw new AuthenticationException("비밀번호가 일치하지 않습니다.");
        }

        return user;
    }
}
