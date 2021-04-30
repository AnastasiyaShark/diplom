package ru.netology.diplom;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.netology.diplom.model.FileI;
import ru.netology.diplom.repository.FileRepository;
import ru.netology.diplom.service.FilesStorageServiceImpl;
import ru.netology.diplom.service.SessionService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
@SpringBootTest
public class ServiceTest {
    @Autowired
    @MockBean
    private FilesStorageServiceImpl fileService;
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
    public void getList()  {

        list.add(fileI);
        when(request.getHeader("auth-token")).thenReturn(token);
        when(sessionService.getUserNameByToken(token2)).thenReturn("User2");
        when(fileService.getAll(3,request)).thenReturn(null);
        when(fileRepository.findAll()).thenReturn(list);
        fileService.getAll(3,request);
        verify(fileService).getAll(3,request);

    }




}
