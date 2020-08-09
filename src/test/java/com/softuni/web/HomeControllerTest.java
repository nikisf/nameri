package com.softuni.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
public class HomeControllerTest {

    @Autowired
    MockMvc mockMvc;


    @Test
    public void testIndexPageStatusAndViewName() throws Exception {
        this.mockMvc.perform(get("/index")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }


    @Test
    @WithMockUser(username = "pesho")
    public void testHomePageStatusAndViewName() throws Exception {
        this.mockMvc.perform(get("/home")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("home"));;
    }

    @Test
    public void testDefaultPageStatusAndViewName() throws Exception {
        this.mockMvc.perform(get("/")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("index"));;
    }


}
