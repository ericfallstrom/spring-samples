package com.efall.springauthsample.auth;

import com.efall.springauthsample.IntegrationTest;
import com.efall.springauthsample.auth.dto.LoginDTO;
import com.efall.springauthsample.auth.dto.RegisterDTO;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class AuthControllerIT extends IntegrationTest {

    @Test
    void testRegister_with_login() throws Exception {
        // Execute - register
        RegisterDTO registerDTO = new RegisterDTO("new-user@email.xyz", "password");
        mockMvc.perform(post("/api/auth/register").content(asString(registerDTO)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // Execute - login
        LoginDTO loginDTO = new LoginDTO("new-user@email.xyz", "password");
        mockMvc.perform(post("/api/auth/login").content(asString(loginDTO)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("accessToken").exists())
                .andExpect(cookie().exists("Refresh-Token"));
    }

    @Test
    void testLogin_missing_user() throws Exception {
        // Execute - login
        LoginDTO loginDTO = new LoginDTO("missing-user@email.xyz", "password");
        mockMvc.perform(post("/api/auth/login").content(asString(loginDTO)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("accessToken").doesNotExist())
                .andExpect(cookie().doesNotExist("Refresh-Token"));
    }

    @Test
    void testRefreshToken() throws Exception {
        // Execute - login
        LoginDTO loginDTO = new LoginDTO("user", "password");
        MvcResult mvcResult = mockMvc.perform(post("/api/auth/login").content(asString(loginDTO)).contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        // Execute - refresh-token
        Cookie refreshTokenCookie = mvcResult.getResponse().getCookie("Refresh-Token");
        mockMvc.perform(post("/api/auth/refresh-token").cookie(refreshTokenCookie).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("accessToken").exists())
                .andExpect(cookie().exists("Refresh-Token"));
    }
}
