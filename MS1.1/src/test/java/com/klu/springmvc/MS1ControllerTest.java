package com.klu.springmvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.klu.springmvc.service.UserService;

@WebMvcTest(MS1Controller.class)
public class MS1ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService us;

    // RIGHT CASE
    @Test
    void testAddRightCase() throws Exception {
        mockMvc.perform(get("/ms1/add")
                .param("a", "5")
                .param("b", "10"))
                .andExpect(status().isOk())
                .andExpect(content().string("MS1.1 addition15"));
    }
 // WRONG CASE
    @Test
    void testAddWrongCase() throws Exception {
        mockMvc.perform(get("/ms1/add")
                .param("a", "5")
                .param("b", "10"))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.not("MS1.1 addition20")));
    }
}