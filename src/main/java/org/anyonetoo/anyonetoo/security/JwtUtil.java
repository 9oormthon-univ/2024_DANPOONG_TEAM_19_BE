package org.anyonetoo.anyonetoo.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtUtil {

    private final Key key; // Secret Key 설정 필요
    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 24;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";


    public String createToken(String userId) {
        return Jwts.builder()
                .setSubject(userId)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }

    public String getTokenFromRequest(HttpServletRequest request) {
        return request.getHeader(AUTHORIZATION_HEADER);
    }

    public String substringToken(String token) {
        return token.startsWith(BEARER_PREFIX) ? token.substring(BEARER_PREFIX.length()) : token;
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Claims getUserInfoFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

//    public void addToken(String userId, HttpServletResponse res) {
//        Date date = new Date();
//        String tokenStr = BEARER_PREFIX +
//                Jwts.builder()
//                        .setSubject(userId)
//                        .setExpiration(new Date(date.getTime() + 30 * 60 * 1000L))
//                        .signWith(key, signatureAlgorithm)
//                        .compact();
//        res.setHeader(AUTHORIZATION_HEADER, tokenStr);
//    }

//    public Long getUserIdFromToken(String token) {
//        Claims claims = Jwts.parser()
//                .setSigningKey(key)
//                .parseClaimsJws(token)
//                .getBody();
//
//        // "userId"가 Integer 타입인 경우
//        return (Long) claims.get("userId");
//    }
}