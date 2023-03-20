package com.example.autotravelserver.security;


import com.example.autotravelserver.Entity.MemberEntity;
import com.example.autotravelserver.Service.MemberService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class TokenProvider {
    private static final long TOKEN_EXPIRE_TIME = 1000 * 60 * 60;
    private final MemberService memberService;

    @Value("${spring.jwt.secret}")
    private String secretKey;

    /**
     * 토큰 생성 (발급)
     *
     * @return
     */
    public String generateToken(MemberEntity memberEntity) {
        Claims claims = Jwts.claims()
                .setSubject(memberEntity.getUsername());

        claims.put("roles", memberEntity.getRole());

        var now = new Date();
        var expireDate = new Date(now.getTime() + TOKEN_EXPIRE_TIME);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now) //토큰 생성 시간
                .setExpiration(expireDate)// 토큰 만료 시간
                .signWith(SignatureAlgorithm.HS512, this.secretKey) //사용 암호와 알고리즘, 비밀키
                .compact();
    }

    public Authentication getAuthentication(String jwt) {
        UserDetails userDetails = this.memberService.loadUserByUsername(this.getUsername(jwt));
        return new UsernamePasswordAuthenticationToken(userDetails, jwt, userDetails.getAuthorities());
    }

    public String getUsername(String token) {
        return this.parseClaims(token).getSubject();
    }

    public boolean validateToken(String token) {
        if (!StringUtils.hasText(token)) return false;

        var claims = this.parseClaims(token);
        return !claims.getExpiration().before(new Date());
    }

    private Claims parseClaims(String token) {
        try {
            return Jwts.parser().setSigningKey(this.secretKey).parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}
