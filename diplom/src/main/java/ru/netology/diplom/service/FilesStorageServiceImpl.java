package ru.netology.diplom.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.netology.diplom.exeption.ErrorInputData;
import ru.netology.diplom.exeption.ErrorUnauthorized;
import ru.netology.diplom.model.File;
import ru.netology.diplom.repository.FileRepository;


import javax.servlet.http.HttpServletRequest;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
    public File upload(MultipartFile file, HttpServletRequest request) {

        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMddHHmmss-"));
        String newName = date + file.getOriginalFilename();
        String directoryName = "D:\\Java\\Netology\\Laxor\\Diplom\\Back\\diplom\\uploads\\" + newName;

        if (!file.isEmpty()) {
            try {
                Files.copy(file.getInputStream(), Paths.get(directoryName), StandardCopyOption.REPLACE_EXISTING);
            } catch (Exception e) {
                throw new ErrorInputData("Error input data");
            }
        }else {
            throw new ErrorInputData("Error input data");
        }
        String authHeader = request.getHeader("auth-token");
        String newAuthHeader =  authHeader.replace("Bearer ", "");
        if (!sessionService.checkSessionByToken(newAuthHeader)){
            throw new ErrorUnauthorized("Unauthorized error");
        }
        //компановка и сохранение файла в бд
        File newFile = new File();
        newFile.setName(newName);
        newFile.setPath(directoryName);
        newFile.setUsersLogin(sessionService.getLoginByToken(newAuthHeader));
        fileRepository.save(newFile);
        return  newFile;
    }

}

