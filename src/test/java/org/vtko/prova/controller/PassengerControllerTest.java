package org.vtko.prova.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
public class PassengerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturnPassengerList() throws Exception {
        mockMvc.perform(get("/passengers"))
               .andExpect(status().isOk());
    }
}
