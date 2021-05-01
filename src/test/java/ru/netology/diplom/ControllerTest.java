package ru.netology.diplom;


import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.MediaType;

import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import ru.netology.diplom.model.LoginForm;
import ru.netology.diplom.model.RequestNewName;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ControllerTest {

    @Autowired
    MockMvc mockMvc;

    private static final Gson gson = new Gson();
    private static final String fileName = "twxy.txt";
    private static final String user = "User2";
    private static final String path = "/file";

    @WithMockUser(username =user)
    @Test
    void givenCorrectCredentials_whenLogin() throws Exception {
        LoginForm validJwtRequest = new LoginForm(user, "password2");

        mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(validJwtRequest))).andExpect(status().isOk())
                .andDo(result -> mockMvc.perform(MockMvcRequestBuilders.get("/list"))).andExpect(status().isOk());
    }


    @WithMockUser(username = user)
    @Test
    void givenInCorrectCredentials_whenDeleteFile() throws Exception {
        mockMvc.perform(delete(path).param("fileName", fileName)).
                andDo(print()).andExpect(status().isBadRequest());

    }

    @WithMockUser(username = user)
    @Test
    void givenInCorrectCredentials_whenDowloadFile() throws Exception {

        mockMvc.perform(get(path).param("fileName", fileName)).
                andDo(print()).andExpect(status().isBadRequest());

    }


    @WithMockUser(username = user)
    @Test
    void givenInInCorrectCredentials_whenEditFile() throws Exception {

        RequestNewName newName1 = new RequestNewName("newname.txt");
        mockMvc.perform(put(path).param("fileName", fileName).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }

}


//    @PutMapping("/file")
//    public ResponseEntity edit(@RequestParam("filename") String fileName,
//                               @RequestBody RequestNewName newName,
//                               HttpServletRequest request) {
//        storageService.changeFileName(fileName, newName,request);
//        return ResponseEntity.ok().body("Success upload");
//    }


//login - version1
//    @Test
//    void givenCorrectCredentials_whenLogin() throws Exception {
//        LoginForm validJwtRequest = new LoginForm("User2","password2");
//
//        mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON)
//                .content(gson.toJson(validJwtRequest))).andExpect(status().isOk());
//    }


