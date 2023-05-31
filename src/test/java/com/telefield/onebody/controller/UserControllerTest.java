package com.telefield.onebody.controller;

import com.telefield.onebody.entity.User;
import com.telefield.onebody.repository.UserRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
class UserControllerTest {

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext,
                      RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                .build();
    }

    @Autowired
    private MockMvc mockMvc;

    public void exampleUserCreate() {
        User user = User.builder()
                .userId("admin1")
                .userName("관리자")
                .phoneNumber("01000000000")
                .password("admin@1")
                .location("경기도 분당시 삼평동")
                .allowed(true)
                .management(true)
                .build();

        userRepository.save(user);
    }

    @Test
    void login() throws Exception {
        exampleUserCreate();
        mockMvc.perform(
                        post("/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\n" +
                                        "\"userId\":\"admin1\",\n" +
                                        "\"password\":\"admin@1\"\n" +
                                        "}")
                ).andDo(print())
                .andDo(document("login"))
                .andExpect(status().isOk());
    }

    @Test
    @Before("allowSignUp")
    void signUp() throws Exception {
        mockMvc.perform(
                        post("/signUp")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\n" +
                                        "\"userId\":\"test\",\n" +
                                        "\"location\":\"경기도 분당구 삼평동\",\n" +
                                        "\"username\":\"ajad\",\n" +
                                        "\"phoneNumber\":\"01000000000\",\n" +
                                        "\"password\":\"123456\"\n" +
                                        "}")
                ).andDo(print())
                .andDo(document("signUp"))
                .andExpect(status().isCreated());
    }

    @Test
    void allowSignUp() throws Exception {
        mockMvc.perform(
                        put("/signUp/allow/{userId}", "test")
                                .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andDo(document("signUp/allow/{userId}rk"))
                .andExpect(status().isOk());
    }
}