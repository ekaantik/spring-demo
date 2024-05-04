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
@RequestMapping("/api/v1/video")
public class VideoController {

    @Value("${upload.pathicovervideo}")
    private String uploadPath;

    @PostMapping("/uploadvideo")
    public ResponseEntity<String> uploadVideo(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return new ResponseEntity<>("Please select a file to upload", HttpStatus.BAD_REQUEST);
        }

        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(uploadPath+ file.getOriginalFilename());
            Files.write(path, bytes);
            return new ResponseEntity<>("File uploaded successfully", HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Failed to upload file", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/downloadvideo/{fileName:.+}")
    public ResponseEntity<byte[]> downloadVideo(@PathVariable String fileName) {
        File file = new File(uploadPath + fileName);
        if (!file.exists()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        try {
            byte[] videoBytes = Files.readAllBytes(file.toPath());
            String contentType = Files.probeContentType(file.toPath());
            MediaType mediaType = MediaType.parseMediaType(contentType);
            return ResponseEntity.ok()
                    .contentType(MediaType.valueOf(""+mediaType+""))
                    .body(videoBytes);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

