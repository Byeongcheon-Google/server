package com.example.autotravelserver.service.Impl;



import com.example.autotravelserver.exception.SignException;
import com.example.autotravelserver.model.repository.MemberRepository;
import com.example.autotravelserver.type.SignErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberDetailServiceImpl implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String memberId) throws UsernameNotFoundException {
       return memberRepository.findByMemberId(memberId)
               .orElseThrow(()-> new SignException(SignErrorCode.USER_NOT_FOUND));
    }
}
