package ru.netology.diplom.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import ru.netology.diplom.exeption.InternalServerError;
import ru.netology.diplom.model.File;
import ru.netology.diplom.model.ListResponse;
import ru.netology.diplom.model.RequestNewName;
import ru.netology.diplom.service.FilesStorageService;
import ru.netology.diplom.service.SessionService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

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

        storageService.delete(filename);
        return ResponseEntity.ok().body("Success deleted");
    }




    @GetMapping("/file")
    public ResponseEntity downloadFile(@RequestParam("filename") String fileName,
                                               HttpServletRequest request) throws IOException {

        Optional<File> fileOptional = storageService.load(fileName);
        byte [] bytes =  Files.readAllBytes(Paths.get(fileOptional.get().getPath()+fileOptional.get().getGeneratedName()));
        File file = fileOptional.get();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getGeneratedName() + "\"")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(bytes);
    }

    @PutMapping("/file")
    public ResponseEntity edit (@RequestParam("filename") String fileName,
                                @RequestBody RequestNewName filename) {
        String m = filename.getFilename();
        System.out.println("name" + "       "+ m);
        storageService.changeFileName(fileName,filename);
        return ResponseEntity.ok().body("Success upload");
    }



    @ExceptionHandler(InternalServerError.class)
    public ResponseEntity<String> internalServerError(InternalServerError e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
