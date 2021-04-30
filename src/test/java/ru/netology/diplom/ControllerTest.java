package ru.netology.diplom;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.netology.diplom.model.FileI;
import ru.netology.diplom.model.ListResponse;
import ru.netology.diplom.repository.FileRepository;
import ru.netology.diplom.service.SessionService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ControllerTest {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    MockMvc mockMvc;

    @MockBean
    SessionService sessionService;
    @MockBean
    HttpServletRequest request;
    @MockBean
    FileRepository fileRepository;

    String token = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJVc2VyMiJ9.VEgjm0RsXKBEmxxh6rZ5tFaHDZCT" +
            "sddIhwofdkr4C4hy5g43t1MWHCCwQZ21woIkzQuus66dpMs_jImF5CeuVQ";

    String token2 = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJVc2VyMiJ9.VEgjm0RsXKBEmxxh6rZ5tFaHDZCT" +
            "sddIhwofdkr4C4hy5g43t1MWHCCwQZ21woIkzQuus66dpMs_jImF5CeuVQ";
    List<FileI> list = new ArrayList<>();
    FileI fileI = new FileI(1, "originalName", "generatedName",
            "path", 1256, "User2");

    @Test
    public void givenPersons_whenGetPersons_thenStatus200() throws Exception {
//        Person p1 = createTestPerson("Jane");
//        Person p2 =createTestPerson( "Joe");
//        mockMvc.perform(
//                get("/persons"))
//                .andExpect(status().isOk())
//                .andExpect(content().json(objectMapper.writeValueAsString(Arrays.asList(p1, p2))));
        ;

//        mockMvc.perform(MockMvcRequestBuilders.post("/file"))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//
//                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(Arrays.asList(fileI))));
        list.add(fileI);
        when(request.getHeader("auth-token")).thenReturn(token);
      when(sessionService.getUserNameByToken(token2)).thenReturn("User2");
        when(fileRepository.findAll()).thenReturn(list);

    }


//    Override
//    public List<ListResponse> getAll(int limit, HttpServletRequest request) {
//        String authHeader = request.getHeader("auth-token");
//        String newAuthHeader = authHeader.replace("Bearer ", "");
//
//        String userName = sessionService.getUserNameByToken(newAuthHeader);
//        List<FileI> allFile = fileRepository.findAll();
//        List<ListResponse> newList = new ArrayList<>();
//        for (FileI file : allFile) {
//            if (file.getUsersLogin().equals(userName)) {
//                newList.add(new ListResponse(file.getGeneratedName(), file.getSize()));
//            }
//        }
//        return newList;
//    }
}
