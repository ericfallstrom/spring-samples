package com.efall.springauthsample.test.controller;

import com.efall.springauthsample.test.dto.TestDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/private/test")
@AllArgsConstructor
public class TestControllerPrivate {

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public TestDTO get(JwtAuthenticationToken jwtAuthenticationToken) {
        log.info("You have ROLE_USER access :: authentication={}", jwtAuthenticationToken);

        return new TestDTO("data");
    }
}
