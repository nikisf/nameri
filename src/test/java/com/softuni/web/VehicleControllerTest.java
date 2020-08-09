package com.softuni.web;


import com.softuni.model.entity.User;
import com.softuni.model.entity.Vehicle;
import com.softuni.repository.VehicleRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
public class VehicleControllerTest {


    @Autowired
    MockMvc mockMvc;




    @Test
    @WithMockUser(username = "pesho")
    public void testVehicleAddPageStatusAndViewName() throws Exception {
        this.mockMvc.perform(get("/vehicles/add")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("vehicles/vеhicle-add"));
    }

}
