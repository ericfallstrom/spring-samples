package com.efall.springauthsample;

import com.efall.springauthsample.security.jwt.JwtService;
import com.efall.springauthsample.user.domain.User;
import com.efall.springauthsample.user.domain.UserAuthority;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Set;
import java.util.UUID;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public abstract class IntegrationTest {

    @Autowired
    protected JwtService jwtService;

    @Autowired
    protected MockMvc mockMvc;

    protected ObjectMapper objectMapper;

    protected User user;

    protected User admin;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();

        user = new User("user", Set.of(UserAuthority.user()));
        user.setId(UUID.randomUUID());

        admin = new User("admin", Set.of(UserAuthority.admin()));
        admin.setId(UUID.randomUUID());
    }

    protected JwtAuthenticationToken jwtAuthentication(User user) {
        return new JwtAuthenticationToken(
                jwtService.generateAccessToken(user.getId().toString(), user.getUsername(), user.getAuthorities().stream().map(UserAuthority::getAuthority).toList()),
                user.getAuthorities());
    }

    protected String asString(final Object object) throws Exception {
        return objectMapper.writeValueAsString(object);
    }
}
