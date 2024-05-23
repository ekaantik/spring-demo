package com.example.demo.service;

import com.example.demo.Utils;
import com.example.demo.constants.ImageCategories;
import com.example.demo.entity.Images;
import com.example.demo.pojos.response.ImageUploadResponse;
import com.example.demo.repository.ImageRepoService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
public class ImageService {

    @Value("${image.path}")
    private String imagePath;

    private final ImageRepoService imageRepoService;

    /**
     * Processes and uploads an image file for a specific store and category.
     *
     * @param imageCategory The category of the image.
     * @param file          The image file to be uploaded.
     * @param storeId       The unique identifier of the store.
     * @return ImageUploadResponse containing information about the uploaded image
     *         if successful.
     * @throws RuntimeException if an error occurs during the upload process.
     */
    public ImageUploadResponse processImageForUpload(ImageCategories imageCategory, MultipartFile file, UUID storeId) {

        try {

            // Original Path
            String originalPath = Utils.buildDirectoryPath(imagePath,
                    storeId,
                    "images",
                    imageCategory.toString(),
                    file.getOriginalFilename());

            String filePath = originalPath;

            // Create Unique Path
            int counter = 1;
            while (!Objects.isNull(imageRepoService.findByPath(filePath))) {
                filePath = Utils.addSuffixToFilePath(originalPath, counter);
                counter++;
            }

            // Create Directory
            Utils.createDirectory(filePath);

            // Save File
            File imageFile = new File(filePath);
            file.transferTo(imageFile);

            // Save Image
            Images savedImage = imageRepoService.save(filePath, storeId, imageCategory);

            ImageUploadResponse response = ImageUploadResponse.builder()
                    .imageId(savedImage.getId())
                    .storeId(storeId)
                    .category(imageCategory)
                    .message("Image uploaded successfully")
                    .build();

            log.info("Image uploaded successfully with id : {} ", savedImage.getId());
            return response;
        } catch (IOException e) {
            log.error("IO Exception while uploading : {} ", e.getMessage());
            throw new RuntimeException("Error while uploading image, Exception : " + e.getMessage());
        }
    }

    /**
     * Downloads an image file by its unique identifier.
     *
     * @param imageId The unique identifier of the image.
     * @return ResponseEntity containing the image file if successful.
     */
    public ResponseEntity<InputStreamResource> downloadImage(UUID imageId) {
        try {

            // Get Image
            Images image = imageRepoService.findById(imageId);

            // No Image Found
            if (image == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            // Get File
            File file = new File(image.getPath());

            // No File Found
            if (!file.exists()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            // Open Stream
            MediaType mediaType = Utils.determineMediaType(image.getPath());
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

            return ResponseEntity.ok()
                    .contentType(mediaType)
                    .contentLength(file.length())
                    .body(resource);

        } catch (Exception e) {
            log.error("IO Exception while downloading : {} ", e.getMessage());
            throw new RuntimeException("Error while downloading image, Exception : " + e.getMessage());
        }
    }

    /**
     * Deletes a image file identified by the given imageId.
     *
     * @param imageId The unique identifier of the image to be deleted.
     * @throws RuntimeException if an error occurs during the deletion process.
     */
    public void deleteImageById(UUID imageId) {

        // Get Image
        Images image = imageRepoService.findById(imageId);

        // Delete Image
        if (image != null) {
            File file = new File(image.getPath());
            if (file.exists()) {
                file.delete();
            }

            // TODO : Handle case where file does not exist but data is present in DB
            imageRepoService.deleteById(imageId);
        }

        // Log
        else {
            log.info("Image not found with id : {} ", imageId);
            // TODO : Throw Exception?
        }
    }
}
