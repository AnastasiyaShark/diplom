package ru.netology.diplom.service;

import org.springframework.web.multipart.MultipartFile;

import ru.netology.diplom.model.ListResponse;
import ru.netology.diplom.model.RequestNewName;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.Path;
import java.util.List;


public interface FilesStorageService {

    void upload(MultipartFile file, HttpServletRequest request);

    void delete(String fileName,HttpServletRequest request);

    Path load(String fileName,HttpServletRequest request);

    void changeFileName(String fileName, RequestNewName name,HttpServletRequest request);

    List<ListResponse> getAll(int limit, HttpServletRequest request);

    void chekAndCreateFolder();

}
