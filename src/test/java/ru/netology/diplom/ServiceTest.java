package ru.netology.diplom;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;
import org.testcontainers.shaded.com.google.common.io.Files;
import ru.netology.diplom.exeption.ErrorInputData;
import ru.netology.diplom.exeption.ErrorUnauthorized;
import ru.netology.diplom.model.FileI;
import ru.netology.diplom.model.RequestNewName;
import ru.netology.diplom.model.Session;
import ru.netology.diplom.repository.FileRepository;
import ru.netology.diplom.service.FilesStorageServiceImpl;
import ru.netology.diplom.service.SessionService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

import static org.mockito.Mockito.*;

@SpringBootTest
public class ServiceTest {

    @InjectMocks
    FilesStorageServiceImpl fileService;

    @Mock
    SessionService sessionService;
    @Mock
    HttpServletRequest request;
    @Mock
    FileRepository fileRepository;

    String token = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJVc2VyMiJ9.VEgjm0RsXKBEmxxh6rZ5tFaHDZCT" +
            "sddIhwofdkr4C4hy5g43t1MWHCCwQZ21woIkzQuus66dpMs_jImF5CeuVQ";

    String token2 = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJVc2VyMiJ9.VEgjm0RsXKBEmxxh6rZ5tFaHDZCT" +
            "sddIhwofdkr4C4hy5g43t1MWHCCwQZ21woIkzQuus66dpMs_jImF5CeuVQ";
    List<FileI> list = new ArrayList<>();
    FileI fileI = new FileI(1, TEST_FILE_NAME, "generatedName",
            "path", 1256, "User2");

    FileI fileII = new FileI(2, TEST_FILE_NAME, "generatedName",
            "path", 1256, "User2");

    @Test
    public void getList() {

        list.add(fileI);
        when(request.getHeader("auth-token")).thenReturn(token);
        when(sessionService.getUserNameByToken(token2)).thenReturn("User2");
        when(fileService.getAll(3, request)).thenReturn(null);
        when(fileRepository.findAll()).thenReturn(list);
        fileService.getAll(3, request);
        verify(fileRepository).findAll();

//        verify(fileService).getAll(3, request);

    }

    @WithMockUser(username = "User2")
    @Test
    public void deleteFile() {

        when(fileRepository.findFileIByGeneratedName("generatedName")).thenReturn(fileI);

        doNothing().when(fileService).deleteFileFromFolder(getTestRootLocation());
        doNothing().when(fileRepository).deleteFileByGeneratedName(TEST_FILE_NAME);
        Mockito.doThrow(ErrorUnauthorized.class)
                .when(sessionService).chekSession(request, TEST_FILE_NAME);
        fileService.delete(TEST_FILE_NAME, request);

    }

    @Test
    public void testLinkingAndSavingFile() {
        Assertions.assertTrue(fileService.linkingAndSavingFile(fileI.getOriginalName(), fileI.getGeneratedName(), fileI.getPath(),
                fileI.getSize(), fileI.getUsersLogin()));

    }

    @WithMockUser(username = "User2")
    @Test
    public void putFile() {

//        when(sessionService.chekSession(request, TEST_FILE_NAME)).thenReturn(true);

        when(fileRepository.findFileIByGeneratedName(fileI.getGeneratedName())).thenReturn(fileI);
        when(sessionService.newFile(fileI.getGeneratedName(), newName)).thenReturn(fileII);

//        doNothing().when(fileRepository.save(fileII));
        doNothing().when(fileRepository).deleteFileByGeneratedName(fileI.getGeneratedName());
        doNothing().when(sessionService).renameFileInFolder(TEST_FILE_NAME, fileI.getOriginalName());
        doNothing().when(fileRepository).deleteFileByGeneratedName(fileI.getGeneratedName());

        fileService.changeFileName(fileI.getGeneratedName(), newName, request);

        InOrder inOrder = inOrder(fileRepository,sessionService);

//        inOrder.verify(fileRepository.save(fileII));
        inOrder.verify(fileRepository).deleteFileByGeneratedName(fileI.getGeneratedName());
        inOrder.verify(sessionService).renameFileInFolder(TEST_FILE_NAME, fileI.getOriginalName());
        inOrder.verify(fileRepository).deleteFileByGeneratedName(fileI.getGeneratedName());

    }


//    Override
//    public void changeFileName(String fileName, RequestNewName name, HttpServletRequest request) {
//
//        if (!sessionService.chekSession(request, fileName)) {
//            throw new ErrorUnauthorized("Unauthorized error.You are not authorized!");
//        }
//        FileI oldFile = fileRepository.findFileIByGeneratedName(fileName);
//        String oldPath = oldFile.getPath() + oldFile.getGeneratedName();
//        FileI newFile = sessionService.newFile(fileName, name);
//
//        fileRepository.save(newFile);
//        String newPath = newFile.getPath() + newFile.getGeneratedName();
//        renameFileInFolder(oldPath, newPath);
//
//        fileRepository.deleteFileByGeneratedName(fileName);
//    }


    private static final Path ROOT_LOCATION = Paths.get("src/main/resources/img/");
    private static final String TEST_FILE_NAME = "testFile.txt";
    private static final RequestNewName newName = new RequestNewName("newName");

    private String getTestRootLocation() {
        return ROOT_LOCATION.resolve("User2").resolve(TEST_FILE_NAME).toString();

    }
}
