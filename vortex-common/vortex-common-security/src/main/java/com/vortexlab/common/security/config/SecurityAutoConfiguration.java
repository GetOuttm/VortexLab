package com.vortexlab.common.security.config;

import com.vortexlab.common.security.jwt.JwtPropeties;
import com.vortexlab.common.security.jwt.JwtUtil;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
@EnableConfigurationProperties(JwtPropeties.class)
public class SecurityAutoConfiguration {

    @Bean
    public JwtUtil jwtUtil(JwtPropeties propeties) {
        return new JwtUtil(propeties.getSecret(),
                propeties.getExpire());
    }
}
