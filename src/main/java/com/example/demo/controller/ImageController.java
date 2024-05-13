package com.example.demo.controller;

import com.example.demo.service.ImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/image")
@Slf4j
public class ImageController {

    @Autowired
    public ImageService imageService;

    @Value("${image.img-max-file-size}")
    private String imageFileSize;

    private static final long IMAGE_MAX_FILE_SIZE = 5 * 1024 * 1024; // 5MB max size

    @PostMapping("/upload-image")
    public ResponseEntity<String> uploadImage(@RequestHeader(value = "image") String imageType,
                                                     @RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return new ResponseEntity<>("Please select a file to upload", HttpStatus.BAD_REQUEST);
        }
        // Check file size
        if (file.getSize() > IMAGE_MAX_FILE_SIZE) {
            return new ResponseEntity<>("Please upload file smaller then " + imageFileSize + " MB",HttpStatus.BAD_REQUEST);
        }

        return imageService.uploadImage(imageType, file);
    }

//    @GetMapping("/download-image/{fileName:.+}")
//    public ResponseEntity<byte[]> downloadImage(@RequestHeader(value = "image") String imagetype,@PathVariable String fileName) throws IOException {
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
//    }
}

