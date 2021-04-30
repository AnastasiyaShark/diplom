package ru.netology.diplom;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.springframework.boot.test.context.SpringBootTest;


import org.springframework.security.test.context.support.WithMockUser;


import ru.netology.diplom.model.FileI;
import ru.netology.diplom.model.RequestNewName;

import ru.netology.diplom.model.User;
import ru.netology.diplom.repository.FileRepository;

import ru.netology.diplom.repository.UserRepository;
import ru.netology.diplom.service.FilesStorageServiceImpl;
import ru.netology.diplom.service.SessionService;


import javax.servlet.http.HttpServletRequest;


import java.nio.file.Path;
import java.nio.file.Paths;


import java.util.ArrayList;
import java.util.List;


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
    private static final String user = "User2";
    private static final String token2 = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJVc2VyMiJ9.VEgjm0RsXKBEmxxh6rZ5tFaHDZCT" +
            "sddIhwofdkr4C4hy5g43t1MWHCCwQZ21woIkzQuus66dpMs_jImF5CeuVQ";

    private static final String token = "Bearer " + token2;


    private static final List<FileI> list = new ArrayList<>();
    private static final FileI fileI = new FileI(1, "originalName", "generatedName",
            "path", 1256, user);

    private static final FileI fileII = new FileI(2, "originalName", "generatedName",
            "path", 1256, user);

    private static final Path ROOT_LOCATION = Paths.get("src/main/resources/img/");
    private static final String TEST_FILE_NAME = "testFile.txt";
    private static final RequestNewName newName = new RequestNewName("newName");


    @Test
    public void testGetList() {
        list.add(fileI);
        when(request.getHeader("auth-token")).thenReturn(token);
        when(sessionService.getUserNameByToken(token2)).thenReturn(fileI.getUsersLogin());
        when(fileService.getAll(3, request)).thenReturn(null);
        when(fileRepository.findAll()).thenReturn(list);

        fileService.getAll(3, request);

        InOrder inOrder = inOrder(fileRepository);
        inOrder.verify(fileRepository).findAll();

    }

    @WithMockUser(username = user)
    @Test
    public void testDeleteFile() {
        when(fileRepository.findFileIByGeneratedName(fileI.getGeneratedName())).thenReturn(fileI);

        doNothing().when(sessionService).deleteFileFromFolder(getTestRootLocation());
        doNothing().when(fileRepository).deleteFileByGeneratedName(fileI.getGeneratedName());

        fileService.delete(fileI.getGeneratedName(), request);

        InOrder inOrder = inOrder(fileRepository);
        inOrder.verify(fileRepository).deleteFileByGeneratedName(fileI.getGeneratedName());
    }

    @Test
    public void testLinkingAndSavingFile() {
        Assertions.assertTrue(fileService.linkingAndSavingFile(fileI.getOriginalName(), fileI.getGeneratedName(),
                fileI.getPath(), fileI.getSize(), fileI.getUsersLogin()));
    }

    @WithMockUser(username = user)
    @Test
    public void testPutFile() {
        when(fileRepository.findFileIByGeneratedName(fileI.getGeneratedName())).thenReturn(fileI);
        when(sessionService.newFile(fileI.getGeneratedName(), newName)).thenReturn(fileII);

        doNothing().when(fileRepository).deleteFileByGeneratedName(fileI.getGeneratedName());
        doNothing().when(sessionService).renameFileInFolder(TEST_FILE_NAME, fileI.getOriginalName());
        doNothing().when(fileRepository).deleteFileByGeneratedName(fileI.getGeneratedName());

        fileService.changeFileName(fileI.getGeneratedName(), newName, request);

        InOrder inOrder = inOrder(fileRepository, sessionService);
        inOrder.verify(fileRepository).deleteFileByGeneratedName(fileI.getGeneratedName());

    }

    private String getTestRootLocation() {

        return ROOT_LOCATION.resolve(user).resolve(TEST_FILE_NAME).toString();
    }

    @WithMockUser(username = user)
    @Test
    public void testLoadFile() {
        when(fileRepository.findFileIByGeneratedName(fileI.getGeneratedName())).thenReturn(fileI);

        fileService.load(fileI.getGeneratedName(), request);

        verify(fileRepository).findFileIByGeneratedName(fileI.getGeneratedName());
    }

}




