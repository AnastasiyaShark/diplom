package ru.netology.diplom.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.netology.diplom.exeption.ErrorInputData;
import ru.netology.diplom.exeption.ErrorUnauthorized;
import ru.netology.diplom.model.FileI;
import ru.netology.diplom.model.ListResponse;
import ru.netology.diplom.model.RequestNewName;
import ru.netology.diplom.repository.FileRepository;


import javax.servlet.http.HttpServletRequest;
import java.io.File;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Service
@RequiredArgsConstructor
public class FilesStorageServiceImpl implements FilesStorageService {

    public String directoryName = "src/main/resources/img/";


    private final FileRepository fileRepository;
    private final SessionService sessionService;


    @Override
    public void chekAndCreateFolder() {
        if (!Files.exists(Paths.get(directoryName))) {
            try {
                Files.createDirectory(Paths.get(directoryName));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void upload(MultipartFile file, HttpServletRequest request) {
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMddHHmmss-"));
        String generatedName = date + file.getOriginalFilename();
        try {
            Files.copy(file.getInputStream(), Paths.get(directoryName + generatedName), StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            throw new ErrorInputData("Error input data");
        }

        String authHeader = request.getHeader("auth-token");
        String newAuthHeader = authHeader.replace("Bearer ", "");

        linkingAndSavingFile(file.getOriginalFilename(), generatedName, directoryName, (int) file.getSize(),
                sessionService.getLoginByToken(newAuthHeader));
    }


    //компоновка и сохранение файла в бд
    public boolean linkingAndSavingFile(String originalName, String generatedName, String path,
                                        int size, String userLogin) {
        FileI newFile = new FileI();
        newFile.setOriginalName(originalName);
        newFile.setGeneratedName(generatedName);
        newFile.setPath(path);
        newFile.setSize(size);
        newFile.setUsersLogin(userLogin);
        fileRepository.save(newFile);
        return true;
    }


    @Override
    public List<ListResponse> getAll(int limit, HttpServletRequest request) {
        String authHeader = request.getHeader("auth-token");
        String newAuthHeader = authHeader.replace("Bearer ", "");

        String userName = sessionService.getUserNameByToken(newAuthHeader);
        List<FileI> allFile = fileRepository.findAll();
        List<ListResponse> newList = new ArrayList<>();
        for (FileI file : allFile) {
            if (file.getUsersLogin().equals(userName)) {
                newList.add(new ListResponse(file.getGeneratedName(), file.getSize()));
            }
        }
        return newList;
    }


    @Override
    public void delete(String fileName, HttpServletRequest request) {

        if (!sessionService.chekSession(request, fileName)) {
            throw new ErrorUnauthorized("Unauthorized error.You are not authorized!");
        }
        FileI fileI = fileRepository.findFileIByGeneratedName(fileName);

        deleteFileFromFolder(fileI.getPath() + fileName);
        fileRepository.deleteFileByGeneratedName(fileName);

    }

    @Override
    public Path load(String fileName, HttpServletRequest request) {
        if (!sessionService.chekSession(request, fileName)) {
            throw new ErrorUnauthorized("Unauthorized error.You are not authorized!");
        }
        FileI fileI = fileRepository.findFileIByGeneratedName(fileName);
        return Paths.get(fileI.getPath() + fileI.getGeneratedName());
    }


    @Override
    public void changeFileName(String fileName, RequestNewName name, HttpServletRequest request) {

//        if (!sessionService.chekSession(request, fileName)) {
//            throw new ErrorUnauthorized("Unauthorized error.You are not authorized!");
//        }
        FileI oldFile = fileRepository.findFileIByGeneratedName(fileName);
        String oldPath = oldFile.getPath() + oldFile.getGeneratedName();
        FileI newFile = sessionService.newFile(fileName, name);

        fileRepository.save(newFile);
        String newPath = newFile.getPath() + newFile.getGeneratedName();
        sessionService.renameFileInFolder(oldPath, newPath);

        fileRepository.deleteFileByGeneratedName(fileName);
    }




    public void deleteFileFromFolder(String path) {

        File file = new File((path));
        if (file.exists()) {
            file.delete();
        } else {
            throw new ErrorInputData("File " + path + "does not exist");
        }
    }


}
