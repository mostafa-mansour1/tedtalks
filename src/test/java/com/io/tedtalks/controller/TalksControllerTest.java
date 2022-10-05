package com.io.tedtalks.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.io.tedtalks.entity.Talks;
import com.io.tedtalks.interceptor.SecurityInterceptor;
import com.io.tedtalks.repository.TalksRepository;

// @WebMvcTest
@WebMvcTest(excludeAutoConfiguration = { SecurityAutoConfiguration.class })
public class TalksControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TalksRepository talksRepository;

    @MockBean
    private SecurityInterceptor securityInterceptor;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
  void initTest() throws Exception {
    // disable basic security in test
    when(securityInterceptor.preHandle(any(), any(), any())).thenReturn(true);
  }

    @Test
    public void givenTalkObject_whenCreateTalks_thenReturnSavedTalks() throws Exception {

        // given - precondition or setup
        Talks newTalk = new Talks(
                "talk title",
                "mostafa",
                Date.valueOf("2021-12-12"),
                Long.valueOf(1000),
                Long.valueOf(1000),
                "http://google.com");
        Talks talk = talksRepository.save(newTalk);

        given(talk)
                .willAnswer((invocation) -> invocation.getArgument(0));

        // when - action or behaviour that we are going test
        ResultActions response = mockMvc.perform(post("/api/v1/talks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newTalk)));
        // then - verify the result or output using assert statements
        response.andDo(print()).andExpect(status().isCreated())
                .andExpect(jsonPath("$.author",
                        is(newTalk.getAuthor())))
                .andExpect(jsonPath("$.title",
                        is(newTalk.getTitle())))
                .andExpect(jsonPath("$.link",
                        is(newTalk.getLink())));

    }

}
