package com.efall.springauthsample.security.oauth;

import com.efall.springauthsample.auth.dto.AccessTokenDTO;
import com.efall.springauthsample.security.jwt.JwtService;
import com.efall.springauthsample.security.oauth.user.OAuth2UserDetails;
import com.efall.springauthsample.user.domain.User;
import com.efall.springauthsample.user.domain.UserAuthority;
import com.efall.springauthsample.user.dto.UserAuthorityDTO;
import com.efall.springauthsample.user.dto.UserDTO;
import com.efall.springauthsample.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class Oauth2ResponseHandler {

    private final JwtService jwtService;

    private final UserService userService;

    private final ObjectMapper objectMapper;

    public void oauthSuccessResponse(final HttpServletRequest request, final HttpServletResponse response, final Authentication authentication) {
        OAuth2AuthenticationToken oAuth2AuthenticationToken = (OAuth2AuthenticationToken)authentication;
        OAuth2UserDetails oAuth2UserDetails = OAuth2UserDetails.of(oAuth2AuthenticationToken.getAuthorizedClientRegistrationId(), authentication);
        log.info("Successful oauth2 authentication :: email={}", oAuth2UserDetails.email());

        try {
            UserDTO user = userService.findByUsername(oAuth2UserDetails.email()).orElseThrow();

            // Set access token
            Jwt accessToken = jwtService.generateAccessToken(user.getId(), user.getUsername(), user.getAuthorities().stream().map(UserAuthorityDTO::getName).toList());
            response.getWriter().write(objectMapper.writeValueAsString(new AccessTokenDTO(accessToken.getTokenValue())));

            // Set refresh token
            Jwt refreshToken = jwtService.generateRefreshToken(user.getId(), user.getUsername());
            response.addCookie(CookieUtils.refreshTokenCookie(refreshToken));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void oauthFailureResponse(final HttpServletRequest request, final HttpServletResponse response, final AuthenticationException exception) {
        log.info("Failed oauth2 authentication :: message={}", exception.getMessage());

        try {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().write("{ \"status\": \"failure\" }");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @SneakyThrows
    public void oauthSuccessCallback(final OAuth2AuthorizedClient client, final Authentication authentication) {
        log.info("Oauth2ResponseHandler.oauthSuccessCallback :: client={}, authentication={}", client.getClientRegistration().getRegistrationId(), authentication);
        OAuth2UserDetails oAuth2UserDetails = OAuth2UserDetails.of(client.getClientRegistration().getRegistrationId(), authentication);

        if(!userService.existsByUsername(oAuth2UserDetails.email())) {
            registerNewUser(oAuth2UserDetails);
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private void registerNewUser(final OAuth2UserDetails oAuth2UserDetails) {
        log.info("Registering new user from oauth :: username={}", oAuth2UserDetails.email());

        User user = new User(oAuth2UserDetails.email(), Set.of(UserAuthority.user()));
        userService.register(user, null);
    }
}
