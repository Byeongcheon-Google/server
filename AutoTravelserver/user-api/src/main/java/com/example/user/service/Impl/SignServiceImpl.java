package com.example.user.service.Impl;

import com.example.jwt.config.JwtTokenProvider;
import com.example.jwt.type.Role;
import com.example.jwt.util.Aes256Util;
import com.example.user.exception.SignException;
import com.example.user.model.dto.SignInDto;
import com.example.user.model.dto.SignUpDto;
import com.example.user.model.entity.Member;
import com.example.user.model.repository.MemberRepository;

import com.example.user.service.SignService;
import com.example.user.type.CommonResponse;
import com.example.user.type.SignErrorCode;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignServiceImpl implements SignService {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;



    @Override
    public SignUpDto.SignUpResponseDto signUp(SignUpDto.SignUpRequestDto member) {
        boolean exists = memberRepository.existsByMemberId(member.getMemberId());

        if (exists) {
            throw new SignException(SignErrorCode.DUPLICATE_USER_ID);
        }



        Member saveMember = memberRepository.save(member.toEntity(Aes256Util.encrypt(member.getPassword())));
        SignUpDto.SignUpResponseDto signUpResponseDto = new SignUpDto.SignUpResponseDto();

        if (!saveMember.getMemberId().isEmpty()) {
            setSuccessResult(signUpResponseDto);
        } else {
            setFailResult(signUpResponseDto);
        }

        return signUpResponseDto;
    }

    private void setSuccessResult(SignUpDto.SignUpResponseDto result) {
        result.setSuccess(true);
        result.setCode(CommonResponse.SUCCESS.getCode());
        result.setMsg(CommonResponse.SUCCESS.getMsg());
    }

    private void setFailResult(SignUpDto.SignUpResponseDto result) {
        result.setSuccess(false);
        result.setCode(CommonResponse.FAIL.getCode());
        result.setMsg(CommonResponse.FAIL.getMsg());
    }

    @Override
    public SignInDto.SignInResponseDto signIn(SignInDto.SignInRequestDto sign) throws RuntimeException {
        Member member = memberRepository.findByMemberId(sign.getMemberId())
                .orElseThrow(() -> new SignException(SignErrorCode.USER_NOT_FOUND));

        if (Aes256Util.decrypt(sign.getPassword()).equals(member.getPassword())) {
            throw new SignException(SignErrorCode.NOT_SAME_PW);
        }

        SignInDto.SignInResponseDto signInResponseDto
                = SignInDto.SignInResponseDto.builder()
                .token(
                        jwtTokenProvider.createToken
                                (
                                        member.getMemberId(),
                                        member.getId(),
                                        Role.USER
                                )
                )
                .build();

        setSuccessResult(signInResponseDto);
        return signInResponseDto;
    }
}
