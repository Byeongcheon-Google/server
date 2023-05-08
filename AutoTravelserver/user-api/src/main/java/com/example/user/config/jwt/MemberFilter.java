package com.example.user.config.jwt;



import com.example.jwt.config.JwtTokenProvider;
import com.example.jwt.config.MemberVo;
import com.example.user.exception.SignException;
import com.example.user.service.Impl.MemberServiceImpl;
import com.example.user.type.SignErrorCode;
import lombok.RequiredArgsConstructor;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(urlPatterns = "/sign/*")
@RequiredArgsConstructor
public class MemberFilter implements Filter {

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberServiceImpl memberService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String token = req.getHeader("X-AUTH-TOKEN");
        if (!jwtTokenProvider.validateToken(token)){
            throw new SignException(SignErrorCode.ACCESS_ISNOT_ALLOWED);
        }
        MemberVo vo = jwtTokenProvider.getMemberVo(token);
        memberService.findByIdAndMemberId(vo.getId(), vo.getMemberId());

        chain.doFilter(request,response);
    }
}
