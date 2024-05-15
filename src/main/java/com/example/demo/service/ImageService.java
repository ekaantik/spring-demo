package com.example.demo.service;

import com.example.demo.constants.ImageCategories;
import com.example.demo.repository.ImageRepoService;
import com.example.demo.security.utils.JwtTokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class ImageService {

    @Value("${image.img-max-file-size}")
    private Integer imageFileSize;

    @Value("${image.file-format}")
    private List<String> fileFormat;

    @Value("${image.path}")
    private String imagePath;

    private final ImageRepoService imageRepoService;
    private final JwtTokenService jwtTokenService;

    @Autowired
    public ImageService(ImageRepoService imageRepoService , JwtTokenService jwtTokenService){
        this.imageRepoService = imageRepoService;
        this.jwtTokenService = jwtTokenService;
    }

    public ResponseEntity<String> processImageForUpload(ImageCategories imageType, MultipartFile file, UUID storeId){
        log.info("File format list : {} ", fileFormat);
        validateImageSpec(imageType, file);

        String path = null;
        try {
            path = imagePath + storeId + "/"+ imageType + "/" + file.getOriginalFilename();
            log.info("Path where image is getting stored : {} ", path);
            createUploadDirectoryIfNotExists(imagePath + storeId + "/"+ imageType + "/");
            file.transferTo(new File(path));
            imageRepoService.save(path,storeId,imageType);
            return new ResponseEntity<>("File uploaded successfully", HttpStatus.OK);
        } catch (IOException e) {
//            e.printStackTrace();
            log.info("Exception : {} ", e.getMessage() );
//            return new ResponseEntity<>("Failed to upload file", HttpStatus.INTERNAL_SERVER_ERROR);
        }
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
        log.info(" imageFileSize : ----- {}  file name {}   ",file.getOriginalFilename(),file.getName());
        log.info(" imageFileSize : ----- {}    ",file.getContentType());
        for(String it : fileFormat){
            log.info(" fileformat  {} ",it);
        }
        if (file.getSize() > imageFileSize) {
            return false;
//            return new ResponseEntity<>("Please upload file smaller then " + imageFileSize + " MB",HttpStatus.BAD_REQUEST);
        }
        log.info("file.getContentType() : {} ", file.getContentType());

        if (!fileFormat.contains(file.getContentType().replace("image/",""))){
            log.info("inside if");
            return false;
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

    private void createUploadDirectoryIfNotExists(String uploadDirPAth) throws IOException {
        Path path = Paths.get(uploadDirPAth);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }
    }

}
