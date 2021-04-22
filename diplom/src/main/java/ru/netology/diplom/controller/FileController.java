package ru.netology.diplom.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import ru.netology.diplom.service.FilesStorageService;
import ru.netology.diplom.service.SessionService;

import javax.servlet.http.HttpServletRequest;



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
        storageService.upload(file,request);
        return ResponseEntity.ok().body("Success upload");
    }



}
