package com.example.demo.controller;

import com.example.demo.constants.VideoCategories;
import com.example.demo.pojos.response.VideoUploadResponse;
import com.example.demo.service.VideoService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/video")
// @MultipartConfig(maxFileSize = "20MB", maxRequestSize = "30MB")
@Slf4j
public class VideoController {

    @Value("${video.max-file-size}")
    private long videoFileSize;

    @Value("${video.file-format}")
    private List<String> fileFormat;
    @Autowired
    public VideoService videoService;

    @PostMapping("/upload")
    @PreAuthorize("hasAnyAuthority('VENDOR')") // 'ROLE_ADMIN'
    public ResponseEntity<VideoUploadResponse> uploadVideo(
            @Valid @RequestHeader(value = "videoCategory") VideoCategories videoCategory,
            @RequestParam("file") MultipartFile file,
            @RequestParam("storeId") UUID storeId) {

        // TODO : Move Business Logic to Service Layer
        log.info("VideoController uploadVideo upload video request was called");
        VideoUploadResponse response;
        if (file.isEmpty()) {
            response = VideoUploadResponse.builder().message("Please select a file to upload")
                    .build();
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        log.info("file.getContentType() : {} ", file.getContentType());

        if (!fileFormat.contains(file.getContentType().replace("video/", ""))) {
            response = VideoUploadResponse.builder().message("Please select a valid file to upload")
                    .build();
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        response = videoService.processVideoForUpload(videoCategory, file, storeId);
        log.info("VideoController uploadVideo video upload response: {}", response);
        log.info("VideoController uploadVideo video uploaded successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/download/{id}")
    @PreAuthorize("hasAnyAuthority('VENDOR')")
    public ResponseEntity<InputStreamResource> downloadVideo(@PathVariable("id") UUID videoId) throws IOException {
        log.info("VideoController downloadVideo video download request was called");
        return videoService.downloadVideo(videoId);
    }

    @DeleteMapping("/delete-by-id")
    @PreAuthorize("hasAnyAuthority('VENDOR')")
    public ResponseEntity<String> deleteVideoById(@RequestParam("id") UUID videoId) {
        log.info("VideoController deleteVideoById video delete request was called");
        videoService.deleteVideoById(videoId);
        log.info("VideoController deleteVideoById video deleted successfully");
        return new ResponseEntity<>("Video deleted successfully", HttpStatus.OK);
    }
}
