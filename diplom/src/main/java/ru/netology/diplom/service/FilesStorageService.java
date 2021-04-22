package ru.netology.diplom.service;

import org.springframework.web.multipart.MultipartFile;
import ru.netology.diplom.model.File;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.nio.file.Path;
import java.util.stream.Stream;

public interface FilesStorageService {

    File upload (MultipartFile file, HttpServletRequest request);

//    public void delete(String fileName);
//
//    public Resource load(String fileName);
//
//    public void changeFileName (String fileName);
//
//    public Stream<Path> getAll (int count);

}
