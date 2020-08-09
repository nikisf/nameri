package com.softuni.web;

import com.softuni.model.entity.User;
import com.softuni.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;

import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc

public class UserControllerTest {

    @Autowired
    MockMvc mockMvc;




    @Test
    public void testLoginPageStatusAndViewName() throws Exception {
        this.mockMvc.perform(get("/users/login")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Test
    public void testRegisterPageStatusAndViewName() throws Exception {
        this.mockMvc.perform(get("/users/register")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("register"));
    }

    @Test
    @WithMockUser(username = "pesho", roles = {"USER", "MODERATOR", "ADMIN"})
    public void testAllPageStatusAndViewName() throws Exception {
        this.mockMvc.perform(get("/users/all")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("user/users-all"));
    }

    @Test
    @WithMockUser(username = "pesho")
    public void testUserProfilePageStatus() throws Exception {
        this.mockMvc.perform(get("/users/profile")).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testFormLoginWithInvalidPassword() throws Exception {
      this.mockMvc.perform(formLogin().password("invalid"))
                .andExpect(unauthenticated());
    }

    @Test
    @WithMockUser(username = "pesho")
    public void testUserEditProfilePageStatus() throws Exception {
        this.mockMvc.perform(get("/users/edit-profile")).andDo(print())
                .andExpect(status().isOk());
    }




}
