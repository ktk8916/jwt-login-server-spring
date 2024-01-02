package com.nitsoft.login.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nitsoft.login.auth.domain.request.SignupRequest;
import com.nitsoft.login.auth.domain.response.LoginResponse;
import com.nitsoft.login.auth.service.AuthService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@ActiveProfiles("test")
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("정상 요청 회원가입")
    void signup() throws Exception {
        // given
        SignupRequest request = SignupRequest.builder()
                .username("username")
                .password("password")
                .nickname("nickname")
                .build();

        LoginResponse response = LoginResponse.builder()
                .accessToken("aaaa.bbbb.cccc")
                .build();

        when(authService.signup(request)).thenReturn(response);

        String body = objectMapper.writeValueAsString(request);

        // when, then
        mockMvc.perform(
                        post("/api/auth/signup")
                                .content(body)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.accessToken").isString())
                .andDo(print());
    }
}