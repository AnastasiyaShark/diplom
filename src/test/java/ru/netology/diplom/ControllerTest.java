//package ru.netology.diplom;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.mock.web.MockMultipartFile;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.multipart.MultipartFile;
//import ru.netology.diplom.controller.FileController;
//import ru.netology.diplom.model.FileI;
//import ru.netology.diplom.model.ListResponse;
//import ru.netology.diplom.repository.FileRepository;
//import ru.netology.diplom.service.SessionService;
//
//import javax.servlet.http.HttpServletRequest;
//import java.io.File;
//import java.nio.file.Files;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//
//public class ControllerTest {
//    @Autowired
//    private ObjectMapper objectMapper;
//    @Autowired
//    MockMvc mockMvc;
//    String token = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJVc2VyMiJ9.VEgjm0RsXKBEmxxh6rZ5tFaHDZCT" +
//            "sddIhwofdkr4C4hy5g43t1MWHCCwQZ21woIkzQuus66dpMs_jImF5CeuVQ";
//
//    String token2 = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJVc2VyMiJ9.VEgjm0RsXKBEmxxh6rZ5tFaHDZCT" +
//            "sddIhwofdkr4C4hy5g43t1MWHCCwQZ21woIkzQuus66dpMs_jImF5CeuVQ";
//    @MockBean
//    SessionService sessionService;
//    @MockBean
//    HttpServletRequest request;
//    @MockBean
//    FileRepository fileRepository;
//
//    @Test
//    public void firstUploadWordsTest() throws Exception {
//        when(request.getHeader("auth-token")).thenReturn(token);
//        when(sessionService.getUserNameByToken(token2)).thenReturn("User2");
//        String text ="Text to be uploaded.";
//        MockMultipartFile file = new MockMultipartFile("file","test.txt", "text/plain", text.getBytes());
//        mockMvc.perform(MockMvcRequestBuilders.multipart("/file")
//                .file(file)
//                .characterEncoding("UTF-8"))
//                .andExpect(MockMvcResultMatchers.status().isOk());
//    }
//
////    @PostMapping("/file")
////    public ResponseEntity upload(@RequestParam("filename") String fileName,
////                                 @RequestParam("file") MultipartFile file,
////                                 HttpServletRequest request) {
////
////        storageService.upload(file, request);
////        return ResponseEntity.ok().body("Success uploads");
////    }
//}
