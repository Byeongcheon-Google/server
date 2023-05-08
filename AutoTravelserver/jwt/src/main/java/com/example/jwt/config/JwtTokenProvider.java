package com.example.jwt.config;

import com.example.jwt.common.ReadFile;
import com.example.jwt.type.Role;
import com.example.jwt.util.Aes256Util;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


import java.io.File;
import java.util.Date;


public class JwtTokenProvider {

    File file = new File("D:\\JwtSecretKey.txt");
    private final String secretKey = ReadFile.readFile(file);

    private long tokenValidTime = 1000L * 60 * 60 *24;

    public String createToken(String memberId,Long id, Role role){
        Claims claims = Jwts.claims().setSubject(Aes256Util.encrypt(memberId))
                .setId(Aes256Util.encrypt(id.toString()));
        claims.put("roles",role);
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime()+tokenValidTime))
                .signWith(SignatureAlgorithm.HS256,secretKey)
                .compact();
    }

    public boolean validateToken(String jwtToken){
        try{
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
            return !claimsJws.getBody().getExpiration().before(new Date());
        } catch (Exception e){
            return false;
        }
    }

    public MemberVo getMemberVo(String token){
        Claims c = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        return new MemberVo(Long.valueOf(Aes256Util.decrypt(c.getId())),Aes256Util.decrypt(c.getSubject()));
    }
}
