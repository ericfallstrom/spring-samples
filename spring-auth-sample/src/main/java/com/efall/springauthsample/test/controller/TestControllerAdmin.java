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
@RequestMapping("/api/admin/test")
@AllArgsConstructor
public class TestControllerAdmin {

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public TestDTO get(JwtAuthenticationToken jwtAuthenticationToken) {
        log.info("You have ROLE_ADMIN access :: authentication={}", jwtAuthenticationToken);

        return new TestDTO("data");
    }
}
