package ru.netology.diplom.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.netology.diplom.exeption.ErrorInputData;
import ru.netology.diplom.exeption.ErrorUnauthorized;
import ru.netology.diplom.model.File;
import ru.netology.diplom.model.ListResponse;
import ru.netology.diplom.repository.FileRepository;


import javax.servlet.http.HttpServletRequest;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
@Service
public class FilesStorageServiceImpl implements FilesStorageService {

    private final FileRepository fileRepository;

    @Autowired
    public FilesStorageServiceImpl(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @Autowired
    SessionService sessionService;

    @Override
    public void upload(MultipartFile file, HttpServletRequest request) {

        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMddHHmmss-"));
        String generatedName = date + file.getOriginalFilename();
        String directoryName = "D:\\Java\\Netology\\Laxor\\Diplom\\Back\\diplom\\uploads\\";

        try {
            Files.copy(file.getInputStream(), Paths.get(directoryName), StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            throw new ErrorInputData("Error input data");
        }

        String authHeader = request.getHeader("auth-token");
        String newAuthHeader = authHeader.replace("Bearer ", "");

        linkingAndSavingFile(file.getOriginalFilename(),generatedName, directoryName, (int) file.getSize(),
                sessionService.getLoginByToken(newAuthHeader));
    }

    //компоновка и сохранение файла в бд
    public void linkingAndSavingFile(String originalName,String generatedName, String path,
                                     int size, String userLogin) {
        File newFile = new File();
        newFile.setOriginalName(originalName);
        newFile.setGeneratedName(generatedName);
        newFile.setPath(path);
        newFile.setSize(size);
        newFile.setUsersLogin(userLogin);
        fileRepository.save(newFile);
    }

    @Override
    public List<ListResponse> getAll(int limit, HttpServletRequest request) {
        String authHeader = request.getHeader("auth-token");
        String newAuthHeader = authHeader.replace("Bearer ", "");

        String userName = sessionService.getUserNameByToken(newAuthHeader);
        List<File> allFile = fileRepository.findAll();
        List<ListResponse> newList = new ArrayList<>();
        for (File file : allFile) {
            if (file.getUsersLogin().equals(userName)) {
                newList.add(new ListResponse(file.getOriginalName(), file.getSize()));
            }
        }
        return newList;
    }

    @Override
    public void delete(String fileName) {

    }
}

