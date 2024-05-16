package com.example.demo.service;

import com.example.demo.constants.VideoCategories;
import com.example.demo.repository.VideoRepoService;
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
public class VideoService {

    @Value("${video.max-file-size}")
    private Integer videoFileSize;

    @Value("${video.file-format}")
    private List<String> fileFormat;

    @Value("${video.path}")
    private String videoPath;

    private final VideoRepoService videoRepoService;
    private final JwtTokenService jwtTokenService;

    @Autowired
    public VideoService(VideoRepoService videoRepoService , JwtTokenService jwtTokenService){
        this.videoRepoService = videoRepoService;
        this.jwtTokenService = jwtTokenService;
    }

    public ResponseEntity<String> processVideoForUpload(VideoCategories videoCategory, MultipartFile file, UUID storeId){
        log.info("File format list : {} ", fileFormat);
        validateVideoSpec(videoCategory, file);

        String path = null;
        try {
            path = storeId + videoPath + "/"+ videoCategory.name() + "/" + file.getOriginalFilename();
            log.info("Path where image is getting stored : {} ", path);
            createUploadDirectoryIfNotExists(storeId + videoPath + "/"+ videoCategory + "/");
            file.transferTo(new File(path));
            videoRepoService.save(path,storeId,videoCategory);
            return new ResponseEntity<>("File uploaded successfully", HttpStatus.OK);
        } catch (IOException e) {
//            e.printStackTrace();
            log.info("Exception : {} ", e.getMessage() );
//            return new ResponseEntity<>("Failed to upload file", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return null;
    }

    public ResponseEntity<String> downloadVideo(String videoCategory, MultipartFile file){
        String path = null;
        try {
            if (videoCategory.equals("license")) {
                path = videoPath + "license/" + file.getOriginalFilename();
            } else{
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

    private boolean validateVideoSpec(VideoCategories videoCategory, MultipartFile file){
        if (file.getSize() > videoFileSize) {
            return false;
        }
        log.info("file.getContentType() : {} ", file.getContentType());

        if (!fileFormat.contains(file.getContentType().replace("video/",""))){
            log.info("inside if");
            return false;
        }
        return true;
    }

    private void createUploadDirectoryIfNotExists(String uploadDirPAth) throws IOException {
        Path path = Paths.get(uploadDirPAth);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }
    }


}
