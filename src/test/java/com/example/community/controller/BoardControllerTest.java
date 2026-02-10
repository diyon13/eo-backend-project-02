package com.example.community.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
@Transactional
class BoardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final String BOARD_URI = "/board";

    @Test
    @WithMockUser
    public void testList() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(BOARD_URI))
                .andExpect(status().isOk())
                .andExpect(view().name("board/list"))
                .andExpect(model().attributeExists("boardList"))
                .andDo(print());

        log.info("Board list test passed");
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testWrite() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post(BOARD_URI + "/write")
                        .with(csrf())
                        .param("title", "공지사항"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(BOARD_URI))
                .andExpect(flash().attributeExists("result"))
                .andDo(print());

        log.info("Board write test passed");
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testUpdate() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(BOARD_URI + "/update")
                        .with(csrf())
                        .param("id", "1")
                        .param("title", "수정 게시판"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(BOARD_URI))
                .andDo(print());

        log.info("Board update test passed");
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testDelete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(BOARD_URI + "/delete")
                        .param("id", "1")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(BOARD_URI))
                .andDo(print());

        log.info("Board delete test passed");
    }
}