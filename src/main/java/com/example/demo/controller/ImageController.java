package com.example.demo.controller;

import com.example.demo.constants.ImageCategories;
import com.example.demo.pojos.response.ImageUploadResponse;
import com.example.demo.service.ImageService;
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
@RequestMapping("/api/v1/image")
@Slf4j
public class ImageController {

    @Value("${image.max-file-size}")
    private Integer imageFileSize;

    @Value("${image.file-format}")
    private List<String> fileFormat;

    @Autowired
    public ImageService imageService;

    @PostMapping("/upload")
    @PreAuthorize("hasAnyAuthority('VENDOR')")
    public ResponseEntity<ImageUploadResponse> uploadImage(
            @Valid @RequestHeader(value = "imageCategory") ImageCategories imageCategory,
            @RequestParam("file") MultipartFile file,
            @RequestParam("storeId") UUID storeId) {
        log.info("ImageController uploadImage upload image request was called");

        // TODO : Move Business Logic to Service Layer
        ImageUploadResponse response;
        if (file.isEmpty()) {
            response = ImageUploadResponse.builder().message("Please select a file to upload")
                    .build();
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        if (file.getSize() > imageFileSize) {
            response = ImageUploadResponse.builder()
                    .message("Please select a file with size less than " + imageFileSize + " bytes")
                    .build();
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

        }
        log.info("file.getContentType() : {} ", file.getContentType());

        if (!fileFormat.contains(file.getContentType().replace("image/", ""))) {
            response = ImageUploadResponse.builder().message("Please select a valid file to upload")
                    .build();
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        response = imageService.processImageForUpload(imageCategory, file, storeId);
        log.info("ImageController uploadImage Image upload response: {}", response);
        log.info("ImageController uploadImage Image uploaded successfully");
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
    public ResponseEntity<String> deleteImageById(@RequestParam("id") UUID imageId) {
        log.info("ImageController deleteImageById image delete request was called");
        imageService.deleteImageById(imageId);
        log.info("ImageController deleteImageById image deleted successfully");

        return new ResponseEntity<>("Image deleted successfully", HttpStatus.OK);
    }
}
