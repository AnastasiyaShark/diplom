package ru.netology.diplom.controller;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.netology.diplom.model.LoginForm;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {

    @Autowired
    MockMvc mockMvc;
    private static final Gson gson = new Gson();
    LoginForm loginForm = new LoginForm("User2","password2");

    @Test
    public void testGivenCorrectCredential () throws Exception {



        mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(loginForm))).andExpect(status().isOk());

    }
}
