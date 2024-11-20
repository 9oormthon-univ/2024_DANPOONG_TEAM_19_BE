package org.anyonetoo.anyonetoo.config;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.security.Key;

@Configuration
public class JwtKeyConfig {
    @Bean
    public Key jwtKey() {
        return Keys.secretKeyFor(SignatureAlgorithm.HS256); // 안전한 256비트 키 생성
    }
}