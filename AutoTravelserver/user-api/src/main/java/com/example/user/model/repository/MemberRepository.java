package com.example.user.model.repository;


import com.example.user.model.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository  extends JpaRepository<Member, Long> {


    Optional<Member> findByMemberId(String memberId);

    Optional<Member> findByIdAndMemberId(Long id, String memberId);

    boolean existsByMemberId(String memberId);
}
