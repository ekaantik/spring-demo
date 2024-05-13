package com.example.demo.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
@Slf4j
public class ImageService {


    @Value("${image.path}")
    private String imagePath;

    public ResponseEntity<String> uploadImage(String imageType, MultipartFile file){
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

            //Update to DB
//            Files.write(path, bytes);
            return new ResponseEntity<>("File uploaded successfully", HttpStatus.OK);
        } catch (IOException e) {
//            e.printStackTrace();
            log.info("Exception : {} ", e.getMessage() );
            return new ResponseEntity<>("Failed to upload file", HttpStatus.INTERNAL_SERVER_ERROR);
        }
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
}
