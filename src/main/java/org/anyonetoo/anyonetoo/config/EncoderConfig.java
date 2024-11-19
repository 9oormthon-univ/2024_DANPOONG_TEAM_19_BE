package org.anyonetoo.anyonetoo.config;

import org.anyonetoo.anyonetoo.security.Encoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class EncoderConfig {
    @Bean
    public Encoder encoder() {
        return new Encoder();
    }
}