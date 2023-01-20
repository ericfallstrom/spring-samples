package com.efall.springauthsample.security.oauth;

import jakarta.servlet.http.Cookie;
import org.springframework.security.oauth2.jwt.Jwt;

import java.time.Duration;
import java.time.Instant;

public class CookieUtils {

    public static final String REFRESH_COOKIE_NAME = "Refresh-Token";

    public static Cookie refreshTokenCookie(final Jwt refreshToken) {
        Cookie cookie = new Cookie("Refresh-Token", refreshToken.getTokenValue());
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setMaxAge((int) Duration.between(Instant.now(), refreshToken.getExpiresAt()).toSeconds());
        cookie.setPath("/refresh-token");

        return cookie;
    }
}
