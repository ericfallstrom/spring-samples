package com.efall.springauthsample.test;

import com.efall.springauthsample.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TestControllerAdminIT extends IntegrationTest {

    @Test
    void testGet_with_anon() throws Exception {
        // Execute
        mockMvc.perform(get("/api/admin/test").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testGet_with_user() throws Exception {
        // Execute
        mockMvc.perform(get("/api/admin/test").contentType(MediaType.APPLICATION_JSON)
                        .with(authentication(jwtAuthentication(user))))
                .andExpect(status().isForbidden());
    }

    @Test
    void testGet_with_admin() throws Exception {
        // Execute
        mockMvc.perform(get("/api/admin/test").contentType(MediaType.APPLICATION_JSON)
                        .with(authentication(jwtAuthentication(admin))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("data").exists());
    }
}
