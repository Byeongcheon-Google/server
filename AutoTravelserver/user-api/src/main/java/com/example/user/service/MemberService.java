package com.example.user.service;

import com.example.user.model.entity.Member;
import com.example.user.model.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;



public interface MemberService {
    Member findByIdAndMemberId(Long id, String memberId);

}
