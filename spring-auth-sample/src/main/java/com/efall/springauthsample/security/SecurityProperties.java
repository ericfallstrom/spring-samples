package com.efall.springauthsample.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "application.security")
public class SecurityProperties {

    private JwtProperties jwt;
    private CorsProperties cors;

    @Data
    public static class JwtProperties {

        private String secret;
        private TokenProperties accessToken;
        private TokenProperties refreshToken;

        @Data
        public static class TokenProperties {
            private Long expiresIn;
        }
    }

    @Data
    public static class CorsProperties {

        private String allowedOrigins = "*";
        private String allowedMethods = "*";
        private String allowedHeaders = "*";
        private Integer maxAge = 1800;
        private String exposedHeaders = "";
    }
}
