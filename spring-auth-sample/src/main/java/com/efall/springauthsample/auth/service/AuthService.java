package com.efall.springauthsample.auth.service;

import com.efall.springauthsample.auth.domain.RefreshToken;
import com.efall.springauthsample.auth.dto.LoginDTO;
import com.efall.springauthsample.auth.repository.RefreshTokenRepository;
import com.efall.springauthsample.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

    private final RefreshTokenRepository refreshTokenRepository;

    private final AuthenticationManager authenticationManager;

    public User login(@RequestBody LoginDTO loginDTO) {
        UsernamePasswordAuthenticationToken authInputToken = new UsernamePasswordAuthenticationToken(
                loginDTO.getUsername(),
                loginDTO.getPassword());

        // Authenticate user
        Authentication authentication = authenticationManager.authenticate(authInputToken);
        User user = (User)authentication.getPrincipal();
        log.info("User logged in :: username={}", loginDTO.getUsername());

        return user;
    }

    public RefreshToken getAndDeleteRefreshToken(Jwt jwt) {
        log.info("Get and delete refresh token :: userId={}", jwt.getSubject());

        RefreshToken refreshToken = refreshTokenRepository.findOneByToken(jwt.getTokenValue()).orElseThrow();
        refreshTokenRepository.delete(refreshToken);

        return refreshToken;
    }

    public void saveRefreshToken(final Jwt jwt, UUID userId) {
        log.trace("Save refresh token :: userId={}", userId);

        RefreshToken refreshToken = new RefreshToken(jwt.getTokenValue(), jwt.getExpiresAt(), userId);
        refreshTokenRepository.save(refreshToken);
    }
}
