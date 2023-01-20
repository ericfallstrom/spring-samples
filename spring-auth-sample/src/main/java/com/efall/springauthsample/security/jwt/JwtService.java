package com.efall.springauthsample.security.jwt;

import com.efall.springauthsample.security.SecurityProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jose.jws.JwsAlgorithms;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtEncoder jwtEncoder;

    private final JwtDecoder jwtDecoder;

    private final SecurityProperties securityProperties;

    public Jwt generateAccessToken(String userId, String username, List<String> authorities) throws IllegalArgumentException {
        JwsHeader jwsHeader = JwsHeader.with(() -> JwsAlgorithms.HS256).build();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .subject(userId)
                .claim("username", username)
                .claim("auth", String.join(",", authorities))
                .expiresAt(Instant.now().plusSeconds(securityProperties.getJwt().getAccessToken().getExpiresIn()))
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claims));
    }

    public Jwt generateRefreshToken(String userId, String username) throws IllegalArgumentException {
        JwsHeader jwsHeader = JwsHeader.with(() -> JwsAlgorithms.HS256).build();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .subject(userId)
                .claim("username", username)
                .expiresAt(Instant.now().plusSeconds(securityProperties.getJwt().getRefreshToken().getExpiresIn()))
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claims));
    }

    public Jwt decodeToken(String token) {
        return jwtDecoder.decode(token);
    }
}
