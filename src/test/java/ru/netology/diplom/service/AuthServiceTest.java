//package ru.netology.diplom.service;
//import com.google.gson.Gson;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//
//import org.springframework.test.web.servlet.MockMvc;
//
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//import ru.netology.diplom.model.LoginForm;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//public class AuthServiceTest {
//
//    @Autowired
//    MockMvc mockMvc;
//    private static final Gson gson = new Gson();
//
//
//    @Test
//    public void testGivenCorrectCredential () throws Exception {
//
//        LoginForm loginForm = new LoginForm("User2","password2");
//
//        mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON)
//                .content(gson.toJson(loginForm))).andExpect(status().isOk());
//
//    }
//
//
//
//
//}
