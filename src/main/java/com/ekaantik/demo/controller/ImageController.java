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

    @PostMapping("/uploadimage/license")
    public ResponseEntity<String> uploadimagelicense(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return new ResponseEntity<>("Please select a file to upload", HttpStatus.BAD_REQUEST);
        }

        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(pathlicense + file.getOriginalFilename());
            Files.write(path, bytes);
            return new ResponseEntity<>("File uploaded successfully", HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Failed to upload file", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/downloadimage/license/{fileName:.+}")
    public ResponseEntity<byte[]> downloadimagelicense(@PathVariable String fileName) {
        File file = new File(pathlicense + fileName);
        if (!file.exists()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        try {
            byte[] videoBytes = Files.readAllBytes(file.toPath());
            return ResponseEntity.ok()
                    .contentType(MediaType.valueOf("image/png"))
                    .body(videoBytes);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/uploadimage/identitycard")
    public ResponseEntity<String> uploadimageidentitycard(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return new ResponseEntity<>("Please select a file to upload", HttpStatus.BAD_REQUEST);
        }

        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(pathidentitycard + file.getOriginalFilename());
            Files.write(path, bytes);
            return new ResponseEntity<>("File uploaded successfully", HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Failed to upload file", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/downloadimage/identitycard/{fileName:.+}")
    public ResponseEntity<byte[]> downloadimageidentitycard(@PathVariable String fileName) {
        File file = new File(pathidentitycard + fileName);
        if (!file.exists()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        try {
            byte[] videoBytes = Files.readAllBytes(file.toPath());
            return ResponseEntity.ok()
                    .contentType(MediaType.valueOf("image/png"))
                    .body(videoBytes);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/uploadimage/logo")
    public ResponseEntity<String> uploadimagelogo(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return new ResponseEntity<>("Please select a file to upload", HttpStatus.BAD_REQUEST);
        }

        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(pathlogo + file.getOriginalFilename());
            Files.write(path, bytes);
            return new ResponseEntity<>("File uploaded successfully", HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Failed to upload file", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/downloadimage/logo/{fileName:.+}")
    public ResponseEntity<byte[]> downloadimagelogo(@PathVariable String fileName) {
        File file = new File(pathlogo + fileName);
        if (!file.exists()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        try {
            byte[] videoBytes = Files.readAllBytes(file.toPath());
            return ResponseEntity.ok()
                    .contentType(MediaType.valueOf("image/png"))
                    .body(videoBytes);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/uploadimage/cover")
    public ResponseEntity<String> uploadimagecover(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return new ResponseEntity<>("Please select a file to upload", HttpStatus.BAD_REQUEST);
        }

        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(pathcoverimage + file.getOriginalFilename());
            Files.write(path, bytes);
            return new ResponseEntity<>("File uploaded successfully", HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Failed to upload file", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/downloadimage/cover/{fileName:.+}")
    public ResponseEntity<byte[]> downloadimagecover(@PathVariable String fileName) {
        File file = new File(pathcoverimage + fileName);
        if (!file.exists()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        try {
            byte[] videoBytes = Files.readAllBytes(file.toPath());
            return ResponseEntity.ok()
                    .contentType(MediaType.valueOf("image/png"))
                    .body(videoBytes);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

