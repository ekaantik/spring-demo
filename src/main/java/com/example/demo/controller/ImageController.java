package com.example.demo.controller;

import com.example.demo.constants.ImageCategories;
import com.example.demo.pojos.response.ImageUploadResponse;
import com.example.demo.service.ImageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/image")
@Slf4j
@RequiredArgsConstructor
public class ImageController {

    @Autowired
    public ImageService imageService;

    @PostMapping("/upload")
    @PreAuthorize("hasAnyAuthority('VENDOR')")
    public ResponseEntity<ImageUploadResponse> uploadImage(
            @Valid @RequestHeader(value = "imageCategory") ImageCategories imageCategory,
            @RequestParam("file") MultipartFile file,
            @RequestParam("storeId") UUID storeId) {
        log.info("ImageController uploadImage upload image request was called");
        ImageUploadResponse response = imageService.processImageForUpload(imageCategory, file, storeId);
        log.info("ImageController uploadImage Image upload response: {}", response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/download/{imageId}")
    @PreAuthorize("hasAnyAuthority('VENDOR')")
    public ResponseEntity<InputStreamResource> downloadImage(@PathVariable UUID imageId) throws IOException {
        log.info("ImageController downloadImage image download request was called");
        return imageService.downloadImage(imageId);
    }

    @DeleteMapping("/delete-by-id")
    @PreAuthorize("hasAnyAuthority('VENDOR')")
    public ResponseEntity<Map<String, String>> deleteImageById(@RequestParam("id") UUID imageId) {
        log.info("ImageController deleteImageById image delete request was called");
        imageService.deleteImageById(imageId);
        log.info("ImageController deleteImageById image deleted successfully");

        Map<String, String> response = new HashMap<>();
        response.put("message", "Image deleted successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
