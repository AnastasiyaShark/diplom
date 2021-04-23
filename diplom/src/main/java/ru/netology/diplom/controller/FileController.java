package ru.netology.diplom.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import ru.netology.diplom.exeption.InternalServerError;
import ru.netology.diplom.model.ListResponse;
import ru.netology.diplom.service.FilesStorageService;
import ru.netology.diplom.service.SessionService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

//@Transactional
@RestController
public class FileController {
    @Autowired
    FilesStorageService storageService;

    @Autowired
    SessionService sessionService;


    @PostMapping("/file")
    public ResponseEntity upload(@RequestParam("filename") String fileName,
                                 @RequestParam("file") MultipartFile file,
                                 HttpServletRequest request) {


        storageService.upload(file, request);
        return ResponseEntity.ok().body("Success uploads");
    }

    @GetMapping("/list")
    public List<ListResponse> getListFiles(@RequestParam("limit") int limit,
                                           HttpServletRequest request) {
        return storageService.getAll(limit, request);
    }
//    @Transactional

    @DeleteMapping("/file")
    public ResponseEntity deleteFile(@RequestParam("filename") String filename) {
        System.out.println(filename + "    " + "    Resolved");
        System.out.println(filename + "    " + "    Resolved");
        System.out.println(filename + "    " + "    Resolved");
        storageService.delete(filename);
        return ResponseEntity.ok().body("Success deleted");
    }


    @ExceptionHandler(InternalServerError.class)
    public ResponseEntity<String> internalServerError(InternalServerError e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
