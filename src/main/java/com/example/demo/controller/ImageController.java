package com.example.demo.controller;

import com.example.demo.constants.ImageCategories;
import com.example.demo.service.ImageService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/image")
@Slf4j
public class ImageController {

    @Autowired
    public ImageService imageService;

    @PostMapping("/upload-image")
    @PreAuthorize("hasAnyAuthority('VENDOR')") //'ROLE_ADMIN'
    public ResponseEntity<String> uploadImage(@Valid @RequestHeader(value = "imageCategory") ImageCategories imageCategory,
                                                     @RequestParam("file") MultipartFile file,
                                              @RequestParam("storeId") UUID storeId ) {
        if (file.isEmpty()) {
            return new ResponseEntity<>("Please select a file to upload", HttpStatus.BAD_REQUEST);
        }

        return imageService.processImageForUpload(imageCategory, file, storeId);
    }

    @GetMapping("/download-image/{fileName:.+}")
    public ResponseEntity<byte[]> downloadImage(@RequestHeader(value = "image") String imagetype,@PathVariable String fileName) {
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
}

