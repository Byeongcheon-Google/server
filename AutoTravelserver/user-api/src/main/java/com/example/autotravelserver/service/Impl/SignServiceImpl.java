package com.example.autotravelserver.service.Impl;

import com.example.autotravelserver.exception.SignException;
import com.example.autotravelserver.model.dto.SignInDto;
import com.example.autotravelserver.model.dto.SignUpDto;
import com.example.autotravelserver.model.entity.Member;
import com.example.autotravelserver.model.repository.MemberRepository;
import com.example.autotravelserver.security.JwtTokenProvider;
import com.example.autotravelserver.service.SignService;
import com.example.autotravelserver.type.CommonResponse;
import com.example.autotravelserver.type.SignErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignServiceImpl implements SignService {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;


    @Override
    public SignUpDto.SignUpResponseDto signUp(SignUpDto.SignUpRequestDto member) {
        boolean exists = memberRepository.existsByMemberId(member.getMemberId());

        if (exists){
            throw new SignException(SignErrorCode.DUPLICATE_USER_ID);
        }

        member.setPassword(passwordEncoder.encode(member.getPassword()));

        Member saveMember = memberRepository.save(member.toEntity());
        SignUpDto.SignUpResponseDto signUpResponseDto = new SignUpDto.SignUpResponseDto();

        if (!saveMember.getMemberId().isEmpty()){
            setSuccessResult(signUpResponseDto);
        } else {
            setFailResult(signUpResponseDto);
        }

        return signUpResponseDto;
    }

    private void setSuccessResult(SignUpDto.SignUpResponseDto result){
        result.setSuccess(true);
        result.setCode(CommonResponse.SUCCESS.getCode());
        result.setMsg(CommonResponse.SUCCESS.getMsg());
    }

    private void setFailResult(SignUpDto.SignUpResponseDto result){
        result.setSuccess(false);
        result.setCode(CommonResponse.FAIL.getCode());
        result.setMsg(CommonResponse.FAIL.getMsg());
    }

    @Override
    public SignInDto.SignInResponseDto signIn(SignInDto.SignInRequestDto sign) throws RuntimeException {
        Member member = memberRepository.findByMemberId(sign.getMemberId())
                .orElseThrow(() -> new SignException(SignErrorCode.USER_NOT_FOUND));

        if (!passwordEncoder.matches(sign.getPassword(), member.getPassword())){
            throw new SignException(SignErrorCode.NOT_SAME_PW);
        }

        SignInDto.SignInResponseDto signInResponseDto
                = SignInDto.SignInResponseDto.builder()
                        .token(
                                jwtTokenProvider.createToken
                                        (
                                        member.getMemberId(),
                                        member.getRoles()
                                        )
                        )
                        .build();

        setSuccessResult(signInResponseDto);
        return signInResponseDto;
    }
}
