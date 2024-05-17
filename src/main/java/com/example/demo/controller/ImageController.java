package com.example.demo.controller;

import com.example.demo.constants.ImageCategories;
import com.example.demo.pojos.response.ImageUploadResponse;
import com.example.demo.service.ImageService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public ResponseEntity<ImageUploadResponse> uploadImage(@Valid @RequestHeader(value = "imageCategory") ImageCategories imageCategory,
                                                           @RequestParam("file") MultipartFile file,
                                                           @RequestParam("storeId") UUID storeId ) {
        ImageUploadResponse response ;
        if (file.isEmpty()) {
            response = ImageUploadResponse.builder().message("Please select a file to upload")
                    .build();
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        if (file.getSize() > imageFileSize) {
            response = ImageUploadResponse.builder().message("Please select a file with size less than " + imageFileSize + " bytes" )
                    .build();
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

        }
        log.info("file.getContentType() : {} ", file.getContentType());

        if (!fileFormat.contains(file.getContentType().replace("image/",""))){
            response = ImageUploadResponse.builder().message("Please select a valid file to upload")
                    .build();
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        response = imageService.processImageForUpload(imageCategory, file, storeId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/download/")
    @PreAuthorize("hasAnyAuthority('VENDOR')")
    public ResponseEntity<byte[]> downloadImage(@RequestParam("storeId") UUID storeId) {
        //throws IOException
//        File file = null;
//        if (imagetype.equals("license")) {
//            file = new File(imagePath + "pathlicense/" + fileName);
//        } else if (imagetype.equals("identitycard")) {
//            file = new File(imagePath + "pathlicense/" + fileName);
//        } else if (imagetype.equals("logo")) {
//            file = new File(imagePath + "pathlicense/" + fileName);
//        } else if (imagetype.equals("cover")) {
//            file = new File(imagePath + "pathlicense/" + fileName);
//        }else{
//            return new ResponseEntity<>(HttpStatus.valueOf("Not Type Found"));
//        }
//        if (!file.exists()) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//        String contentType = Files.probeContentType(file.toPath());
//        MediaType mediaType = MediaType.parseMediaType(contentType);
//        try {
//            byte[] videoBytes = Files.readAllBytes(file.toPath());
//            return ResponseEntity.ok()
//                    .contentType(MediaType.valueOf(""+mediaType+""))
//                    .body(videoBytes);
//        } catch (IOException e) {
//            e.printStackTrace();
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
        return null;
    }

    //Get All image for a store
}

