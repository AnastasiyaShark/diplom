package ru.netology.diplom.service;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.netology.diplom.model.ListResponse;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface FilesStorageService {

    void upload (MultipartFile file, HttpServletRequest request);

     void delete(String fileName);
//
//    public Resource load(String fileName);
//
//    public void changeFileName (String fileName);
//
    List<ListResponse> getAll (int limit,HttpServletRequest request);

}
