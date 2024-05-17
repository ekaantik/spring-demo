package com.example.demo.service;

import com.example.demo.constants.ImageCategories;
import com.example.demo.entity.Images;
import com.example.demo.pojos.response.ImageUploadResponse;
import com.example.demo.repository.ImageRepoService;
import com.example.demo.security.utils.JwtTokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@Slf4j
public class ImageService {

    @Value("${image.path}")
    private String imagePath;

    private final ImageRepoService imageRepoService;
    private final JwtTokenService jwtTokenService;

    @Autowired
    public ImageService(ImageRepoService imageRepoService , JwtTokenService jwtTokenService){
        this.imageRepoService = imageRepoService;
        this.jwtTokenService = jwtTokenService;
    }

    public ImageUploadResponse processImageForUpload(ImageCategories imageCategory, MultipartFile file, UUID storeId){
        String dirPath =  new StringBuilder().append(imagePath).append(storeId)
                .append("/images/").append(imageCategory.name()).append("/").toString();
        try {
            String filePath = new StringBuilder(dirPath).append(file.getOriginalFilename()).toString();

            if (! Objects.isNull(imageRepoService.findByPath(filePath))){
                String updatedFileName = addSuffixToFileName(file.getOriginalFilename());
                filePath = new StringBuilder(dirPath).append(updatedFileName).toString();
            }
            log.info("file path : {} dire path : {} " , filePath, dirPath);
            createUploadDirectoryIfNotExists(dirPath);
            file.transferTo(new File(filePath));
            Images savedImage = imageRepoService.save(filePath,storeId,imageCategory);
            ImageUploadResponse response = ImageUploadResponse.builder()
                    .imageId(savedImage.getId())
                    .storeId(savedImage.getStore().getId())
                    .category(savedImage.getCategory())
                    .message("Successfully Uploaded File")
                    .build();
            return response;
        } catch (IOException e) {
            log.info("Exception : {} ", e.getMessage() );
        }
        return null;
    }

    public ResponseEntity<InputStreamResource> downloadImage(UUID imageId){
        try {
            Images image = imageRepoService.findById(imageId);
            if (image == null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            File file = new File(image.getPath());
            if (!file.exists()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            log.info("file.getName() ... ");
            // Set content type and headers
            MediaType mediaType = determineMediaType(file.getName());

            // Prepare the response entity
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
            return ResponseEntity.ok()
                    .contentType(mediaType)
                    .contentLength(file.length())
//                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                    .body(resource);
        } catch (IOException e) {
//            e.printStackTrace();
            log.info("Exception : {} ", e.getMessage() );
//            return new ResponseEntity<>("Failed to upload file", HttpStatus.INTERNAL_SERVER_ERROR);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    private void createUploadDirectoryIfNotExists(String uploadDirPAth) throws IOException {
        Path path = Paths.get(uploadDirPAth);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }
    }

    private String addSuffixToFileName(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex == -1) {
            // No extension
            return fileName + "_1";
        } else {
            // With extension
            String name = fileName.substring(0, dotIndex);
            String extension = fileName.substring(dotIndex);
            return name + "_1" + extension;
        }
    }

    private MediaType determineMediaType(String fileName) {
        if (fileName.toLowerCase().endsWith(".jpg") || fileName.toLowerCase().endsWith(".jpeg")) {
            return MediaType.IMAGE_JPEG;
        } else if (fileName.toLowerCase().endsWith(".png")) {
            return MediaType.IMAGE_PNG;
        } else {
            return MediaType.APPLICATION_OCTET_STREAM;
        }
    }

}
