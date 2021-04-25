package ru.netology.diplom.service;

import org.springframework.web.multipart.MultipartFile;
import ru.netology.diplom.model.FileI;
import ru.netology.diplom.model.ListResponse;
import ru.netology.diplom.model.RequestNewName;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

public interface FilesStorageService {

    void upload(MultipartFile file, HttpServletRequest request);

    void delete(String fileName);

    Optional<FileI> load(String fileName) ;

    void changeFileName(String fileName, RequestNewName name);

    List<ListResponse> getAll(int limit, HttpServletRequest request);
   void chekAndCreateFolder ();

}
