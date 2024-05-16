package com.example.demo.controller;

import com.example.demo.constants.ImageCategories;
import com.example.demo.constants.VideoCategories;
import com.example.demo.service.ImageService;
import com.example.demo.service.VideoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/video")
public class VideoController {

    @Value("${video.path}")
    private String uploadPath;

    @Autowired
    public VideoService videoService;

//    @PostMapping("/upload-video")
//    public ResponseEntity<String> uploadVideo(@RequestParam("file") MultipartFile file) {
//        if (file.isEmpty()) {
//            return new ResponseEntity<>("Please select a file to upload", HttpStatus.BAD_REQUEST);
//        }
//        try {
//            byte[] bytes = file.getBytes();
//            Path path = Paths.get(uploadPath+ file.getOriginalFilename());
//            Files.write(path, bytes);
//            return new ResponseEntity<>("File uploaded successfully", HttpStatus.OK);
//        } catch (IOException e) {
//            e.printStackTrace();
//            return new ResponseEntity<>("Failed to upload file", HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    @PostMapping("/upload-video")
    @PreAuthorize("hasAnyAuthority('VENDOR')") //'ROLE_ADMIN'
    public ResponseEntity<String> uploadVideo(@Valid @RequestHeader(value = "videoCategory") VideoCategories videoCategory,
                                              @RequestParam("file") MultipartFile file,
                                              @RequestParam("storeId") UUID storeId ) {
        if (file.isEmpty()) {
            return new ResponseEntity<>("Please select a file to upload", HttpStatus.BAD_REQUEST);
        }
        return videoService.processVideoForUpload(videoCategory, file, storeId);
    }

    @GetMapping("/download-video/{fileName:.+}")
    public ResponseEntity<byte[]> downloadVideo(@PathVariable String fileName) {
        File file = new File(uploadPath + fileName);
        if (!file.exists()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
//        try {
//            videoService.downloadVideo();
////            byte[] videoBytes = Files.readAllBytes(file.toPath());
////            String contentType = Files.probeContentType(file.toPath());
////            MediaType mediaType = MediaType.parseMediaType(contentType);
//            return null;
//        } catch (IOException e) {
//            e.printStackTrace();
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
        return null;
    }
}

