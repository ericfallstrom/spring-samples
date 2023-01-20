package com.efall.springauthsample.test.controller;

import com.efall.springauthsample.test.dto.TestDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/public/test")
@AllArgsConstructor
public class TestControllerPublic {

    @GetMapping
    public TestDTO get() {
        log.info("You have anonymous access");

        return new TestDTO("data");
    }
}
