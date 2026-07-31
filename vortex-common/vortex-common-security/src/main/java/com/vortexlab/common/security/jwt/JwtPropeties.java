package com.vortexlab.common.security.jwt;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "vortex.jwt")
public class JwtPropeties {

    /**
     * 密钥
     */
    private String secret =
            "vortexlab-default-secret-key-2026";

    /**
     * 过期时间
     */
    private Long expire = 7 * 24 * 6060 * 1000L;
}
