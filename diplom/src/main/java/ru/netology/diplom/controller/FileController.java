package ru.netology.diplom.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import ru.netology.diplom.exeption.InternalServerError;

import ru.netology.diplom.model.ListResponse;
import ru.netology.diplom.model.RequestNewName;
import ru.netology.diplom.service.FilesStorageService;
import ru.netology.diplom.service.SessionService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Files;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class FileController {

    private final FilesStorageService storageService;
    private final SessionService sessionService;


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


    @DeleteMapping("/file")
    public ResponseEntity deleteFile(@RequestParam("filename") String filename) {

        storageService.delete(filename);
        return ResponseEntity.ok().body("Success deleted");
    }


    @GetMapping("/file")
    public ResponseEntity<Resource> downloadFile(@RequestParam("filename") String fileName) throws IOException {

        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(storageService.load(fileName)));

        return ResponseEntity.ok()
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(resource);
    }


    @PutMapping("/file")
    public ResponseEntity edit(@RequestParam("filename") String fileName,
                               @RequestBody RequestNewName newName) {
        storageService.changeFileName(fileName, newName);
        return ResponseEntity.ok().body("Success upload");
    }


    @ExceptionHandler(InternalServerError.class)
    public ResponseEntity<String> internalServerError(InternalServerError e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
