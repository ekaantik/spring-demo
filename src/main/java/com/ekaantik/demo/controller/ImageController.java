package com.ekaantik.demo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/v1/image")
public class ImageController {

    @Value("${upload.pathlicense}")
    private String pathlicense;
    @Value("${upload.pathidentitycard}")
    private String pathidentitycard;
    @Value("${upload.pathlogo}")
    private String pathlogo;
    @Value("${upload.pathcoverimage}")
    private String pathcoverimage;

    @PostMapping("/uploadimage")
    public ResponseEntity<String> uploadimagelicense(@RequestHeader(value = "image") String imagetype,
                                                     @RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return new ResponseEntity<>("Please select a file to upload", HttpStatus.BAD_REQUEST);
        }
        Path path = null;
        try {
            byte[] bytes = file.getBytes();
            if (imagetype.equals("license")) {
                path = Paths.get(pathlicense + file.getOriginalFilename());
            } else if (imagetype.equals("identitycard")) {
                path = Paths.get(pathidentitycard + file.getOriginalFilename());
            } else if (imagetype.equals("logo")) {
                path = Paths.get(pathlogo + file.getOriginalFilename());
            } else if (imagetype.equals("cover")) {
                path = Paths.get(pathcoverimage + file.getOriginalFilename());
            }else{
                return new ResponseEntity<>("Folder Type not Valid", HttpStatus.BAD_REQUEST);
            }
            Files.write(path, bytes);
            return new ResponseEntity<>("File uploaded successfully", HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Failed to upload file", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/downloadimage/{fileName:.+}")
    public ResponseEntity<byte[]> downloadimagelicense(@RequestHeader(value = "image") String imagetype,@PathVariable String fileName) throws IOException {
        File file = null;
        if (imagetype.equals("license")) {
            file = new File(pathlicense + fileName);
        } else if (imagetype.equals("identitycard")) {
            file = new File(pathlicense + fileName);
        } else if (imagetype.equals("logo")) {
            file = new File(pathlicense + fileName);
        } else if (imagetype.equals("cover")) {
            file = new File(pathlicense + fileName);
        }else{
            return new ResponseEntity<>(HttpStatus.valueOf("Not Type Found"));
        }
        if (!file.exists()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        String contentType = Files.probeContentType(file.toPath());
        MediaType mediaType = MediaType.parseMediaType(contentType);
        try {
            byte[] videoBytes = Files.readAllBytes(file.toPath());
            return ResponseEntity.ok()
                    .contentType(MediaType.valueOf(""+mediaType+""))
                    .body(videoBytes);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

