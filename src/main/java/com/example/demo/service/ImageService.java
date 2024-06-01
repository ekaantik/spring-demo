package com.example.demo.service;

import com.example.demo.Utils;
import com.example.demo.constants.Constants;
import com.example.demo.constants.ErrorCode;
import com.example.demo.constants.ImageCategories;
import com.example.demo.entity.Images;
import com.example.demo.exception.InvalidFileException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.pojos.response.ImageUploadResponse;
import com.example.demo.repository.ImageRepoService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
public class ImageService {

    @Value("${image.path}")
    private String imagePath;

    @Value("${image.max-file-size}")
    private Integer imageFileSize;

    @Value("${image.file-format}")
    private List<String> fileFormat;

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

        // Empty File
        if (file.isEmpty()) {
            log.error("UploadImage file is empty");
            throw new InvalidFileException(ErrorCode.INVALID_DATA, "file");
        }

        // Invalid File Size
        if (file.getSize() > imageFileSize) {
            log.error("UploadImage file size is too large");
            throw new InvalidFileException(ErrorCode.INVALID_FILE_SIZE, "file");
        }

        log.info("file.getContentType() : {} ", file.getContentType());

        // Invalid File Format
        if (!fileFormat.contains(file.getContentType().replace("image/", ""))) {
            log.error("ImageController uploadImage file format is invalid");
            throw new InvalidFileException(ErrorCode.INVALID_FILE, file.getOriginalFilename());
        }

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
                log.error("Image Not Found for Id : {}", imageId);
                throw new NotFoundException(ErrorCode.NOT_EXISTS, imageId, Constants.FIELD_ID, Constants.TABLE_IMAGE);
            }

            // Get File
            File file = new File(image.getPath());

            // No File Found
            if (!file.exists()) {
                log.error("Image File Not Found for Id : {}", imageId);
                throw new NotFoundException(ErrorCode.FILE_NOT_FOUND, Constants.TABLE_IMAGE, imageId);
            }

            // Open Stream
            MediaType mediaType = Utils.determineMediaType(image.getPath());
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

            return ResponseEntity.ok()
                    .contentType(mediaType)
                    .contentLength(file.length())
                    .body(resource);

        } catch (IOException e) {
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
            imageRepoService.deleteById(imageId);

            File file = new File(image.getPath());
            if (file.exists()) {
                log.info("Deleted Image with id : {} ", imageId);
                file.delete();
            } else {
                log.error("Image File Not Found for Id : {}", imageId);
                throw new NotFoundException(ErrorCode.FILE_NOT_FOUND, Constants.TABLE_IMAGE, imageId);
            }
        }

        // Log
        else {
            log.info("Image not found with id : {} ", imageId);
            // TODO : Throw Exception?
        }
    }
}
