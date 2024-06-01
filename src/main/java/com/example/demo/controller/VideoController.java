package com.example.demo.controller;

import com.example.demo.constants.VideoCategories;
import com.example.demo.pojos.response.VideoUploadResponse;
import com.example.demo.service.VideoService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/video")
@Slf4j
public class VideoController {

    @Autowired
    public VideoService videoService;

    @PostMapping("/upload")
    @PreAuthorize("hasAnyAuthority('VENDOR')")
    public ResponseEntity<VideoUploadResponse> uploadVideo(
            @Valid @RequestHeader(value = "videoCategory") VideoCategories videoCategory,
            @RequestParam("file") MultipartFile file,
            @RequestParam("storeId") UUID storeId) {

        log.info("VideoController uploadVideo upload video request was called");

        VideoUploadResponse response = videoService.processVideoForUpload(videoCategory, file, storeId);

        log.info("VideoController uploadVideo video upload response: {}", response);
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
