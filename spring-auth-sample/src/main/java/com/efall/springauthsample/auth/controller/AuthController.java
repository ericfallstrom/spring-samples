package com.efall.springauthsample.auth.controller;

import com.efall.springauthsample.auth.domain.RefreshToken;
import com.efall.springauthsample.auth.dto.AccessTokenDTO;
import com.efall.springauthsample.auth.dto.LoginDTO;
import com.efall.springauthsample.auth.dto.RegisterDTO;
import com.efall.springauthsample.auth.service.AuthService;
import com.efall.springauthsample.security.jwt.JwtService;
import com.efall.springauthsample.security.oauth.CookieUtils;
import com.efall.springauthsample.user.domain.User;
import com.efall.springauthsample.user.domain.UserAuthority;
import com.efall.springauthsample.user.dto.UserAuthorityDTO;
import com.efall.springauthsample.user.dto.UserDTO;
import com.efall.springauthsample.user.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    private final UserService userService;

    private final JwtService jwtService;

    @PostMapping("/register")
    public void register(@RequestBody RegisterDTO registerDTO) {
        User user = new User(registerDTO.getUsername(), Set.of(UserAuthority.user()));

        userService.register(user, registerDTO.getPassword());
    }

    @PostMapping("/login")
    public ResponseEntity<AccessTokenDTO> login(@RequestBody LoginDTO loginDTO, HttpServletResponse response) {
        // Authenticate user
        User user = authService.login(loginDTO);

        // Create access-token
        Jwt accessToken = jwtService.generateAccessToken(user.getId().toString(), user.getUsername(), user.getAuthorities().stream().map(UserAuthority::getAuthority).toList());
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO(accessToken.getTokenValue());

        // Create refresh-token
        Jwt refreshToken = jwtService.generateRefreshToken(user.getId().toString(), user.getUsername());
        authService.saveRefreshToken(refreshToken, user.getId());

        // Set refresh-token cookie
        Cookie refreshTokenCookie = CookieUtils.refreshTokenCookie(refreshToken);
        response.addCookie(refreshTokenCookie);

        return ResponseEntity.ok(accessTokenDTO);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<AccessTokenDTO> refreshToken(@CookieValue(CookieUtils.REFRESH_COOKIE_NAME) String token, HttpServletResponse response) {
        // Get refresh-token
        Jwt jwt = jwtService.decodeToken(token);
        RefreshToken refreshToken = authService.getAndDeleteRefreshToken(jwt);

        // Get user
        UserDTO user = userService.findById(refreshToken.getUserId()).orElseThrow();

        // Create access-token
        Jwt accessToken = jwtService.generateAccessToken(user.getId(), user.getUsername(), user.getAuthorities().stream().map(UserAuthorityDTO::getName).toList());
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO(accessToken.getTokenValue());

        // Create new refresh-token
        Jwt newRefreshToken = jwtService.generateRefreshToken(user.getId(), user.getUsername());
        authService.saveRefreshToken(newRefreshToken, UUID.fromString(user.getId()));

        // Set refresh-token cookie
        response.addCookie(CookieUtils.refreshTokenCookie(newRefreshToken));

        return ResponseEntity.ok(accessTokenDTO);
    }

    @GetMapping
    public UserDTO user(final JwtAuthenticationToken auth) {
        return userService.findById(UUID.fromString(auth.getName()))
                .orElseThrow(() -> new IllegalArgumentException("User not found :: email=" + auth.getName()));
    }
}
