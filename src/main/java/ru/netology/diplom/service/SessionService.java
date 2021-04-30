package ru.netology.diplom.service;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.netology.diplom.exeption.ErrorInputData;
import ru.netology.diplom.model.FileI;
import ru.netology.diplom.model.RequestNewName;
import ru.netology.diplom.model.Session;
import ru.netology.diplom.repository.FileRepository;
import ru.netology.diplom.repository.SessionRepository;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Component

public class SessionService {

    SessionRepository sessionRepository;

     FileRepository fileRepository;

    public SessionService(SessionRepository sessionRepository, FileRepository fileRepository) {
        this.sessionRepository = sessionRepository;
        this.fileRepository = fileRepository;
    }

    public boolean chekSession(HttpServletRequest request, String fileName) {
        String authHeader = request.getHeader("auth-token");
        String newAuthHeader = authHeader.replace("Bearer ", "");
        String userName = getUserNameByToken(newAuthHeader);

        FileI fileI = fileRepository.findFileIByGeneratedName(fileName);
        return userName.equals(fileI.getUsersLogin());
    }

    public void renameFileInFolder(String oldPath, String newPath) {

        File file = new File(oldPath);
        if (file.exists()) {
            file.renameTo(new File(newPath));
        } else {
            throw new ErrorInputData("File " + oldPath + "does not exist");
        }
    }

    public FileI newFile(String fileName, RequestNewName name) {

        Optional<FileI> file = fileRepository.findFileByGeneratedName(fileName);

        FileI newFile = file.get();
        newFile.setOriginalName(name.getFilename());
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMddHHmmss-"));
        newFile.setGeneratedName(date + name.getFilename());

        return newFile;
    }

    public void deleteFileFromFolder(String path) {

        File file = new File((path));
        if (file.exists()) {
            file.delete();
        } else {
            throw new ErrorInputData("File " + path + "does not exist");
        }
    }


    public String getLoginByToken(String token) {
        return sessionRepository.getLoginByToken(token);
    }

    public void saveSession(Session session) {
        sessionRepository.saveSession(session);
    }

    public boolean checkSessionRepository(Session session) {
        return sessionRepository.checkSessionRepository(session);
    }

    public void deleteSession(Session session) {
        sessionRepository.deleteSession(session);
    }

    public List<Session> getAll() {
        return sessionRepository.getSessionsRepository();
    }

    public Session getSessionByToken(String token) {
        return sessionRepository.getSessionByToken(token);
    }

    public String getUserNameByToken(String token) {
        return sessionRepository.getUserNameByToken(token);
    }


}
