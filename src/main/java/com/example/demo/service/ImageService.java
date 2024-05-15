package com.example.demo.service;

import com.example.demo.constants.ImageCategories;
import com.example.demo.constants.ImageTypes;
import com.example.demo.repository.ImageRepoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
@Slf4j
public class ImageService {

    @Value("${image.img-max-file-size}")
    private static String imageFileSize;

    @Value("${image.file-format}")
    private static List<String> fileFormat;

    private static final long IMAGE_MAX_FILE_SIZE = 5 * 1024 * 1024; // 5MB max size
//(long) Integer.parseInt(imageFileSize)
    @Autowired
    private ImageRepoService imageRepoService;

    @Value("${image.path}")
    private String imagePath;

    public ResponseEntity<String> processImageForUpload(ImageCategories imageType, MultipartFile file){
        log.info("File format list : {} ", fileFormat);
//        validateImageSpec(imageType, file);
//        String path = null;
//        try {
//            path = imagePath + imageType + "/" + file.getOriginalFilename();
//            file.transferTo(new File(path));
////            Files.write(path, bytes);
//            imageRepoService.save()
//            return new ResponseEntity<>("File uploaded successfully", HttpStatus.OK);
//        } catch (IOException e) {
////            e.printStackTrace();
//            log.info("Exception : {} ", e.getMessage() );
//            return new ResponseEntity<>("Failed to upload file", HttpStatus.INTERNAL_SERVER_ERROR);
//        }
        return null;
    }

    public ResponseEntity<String> downloadImage(String imageType, MultipartFile file){
        String path = null;
        try {
            if (imageType.equals("license")) {
                path = imagePath + "license/" + file.getOriginalFilename();
            } else if (imageType.equals("id-card")) {
                path = imagePath + "id_card/" + file.getOriginalFilename();
            } else if (imageType.equals("logo")) {
                path = imagePath + "logo/" + file.getOriginalFilename();
            } else if (imageType.equals("cover")) {
                path = imagePath + "cover/" + file.getOriginalFilename();
            }else{
                return new ResponseEntity<>("Please select correct file Type", HttpStatus.BAD_REQUEST);
            }
            file.transferTo(new File(path));
//            Files.write(path, bytes);
            return new ResponseEntity<>("File uploaded successfully", HttpStatus.OK);
        } catch (IOException e) {
//            e.printStackTrace();
            log.info("Exception : {} ", e.getMessage() );
            return new ResponseEntity<>("Failed to upload file", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private boolean validateImageSpec(ImageCategories imageType, MultipartFile file){

        if (file.getSize() > IMAGE_MAX_FILE_SIZE) {
            return false;
//            return new ResponseEntity<>("Please upload file smaller then " + imageFileSize + " MB",HttpStatus.BAD_REQUEST);
        }

//        MediaType mediaType = MediaType.APPLICATION_OCTET_STREAM;
//        if (file.getOriginalFilename().toLowerCase().endsWith(".jpg") || file.getOriginalFilename().toLowerCase().endsWith(".jpeg")) {
//
//            mediaType = MediaType.IMAGE_JPEG;
//        } else if (file.toLowerCase().endsWith(".png")) {
//            mediaType = MediaType.IMAGE_PNG;
//        }


        ////                return new ResponseEntity<>("Please select correct file Type", HttpStatus.BAD_REQUEST);
        return true;
    }
}
