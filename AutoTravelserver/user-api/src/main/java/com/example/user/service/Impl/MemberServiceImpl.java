package com.example.user.service.Impl;

import com.example.user.exception.SignException;
import com.example.user.model.entity.Member;
import com.example.user.model.repository.MemberRepository;
import com.example.user.service.MemberService;
import com.example.user.type.SignErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    @Override
    public Member findByIdAndMemberId(Long id, String memberId) {
       return memberRepository.findByIdAndMemberId(id,memberId)
                .orElseThrow(()-> new SignException(SignErrorCode.USER_NOT_FOUND));
    }
}
