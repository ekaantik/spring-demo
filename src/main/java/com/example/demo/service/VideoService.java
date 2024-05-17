package com.example.demo.service;

import com.example.demo.constants.VideoCategories;
import com.example.demo.entity.Videos;
import com.example.demo.pojos.response.VideoUploadResponse;
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
import java.util.Objects;
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

    public VideoUploadResponse processVideoForUpload(VideoCategories videoCategory, MultipartFile file, UUID storeId){
        String dirPath =  new StringBuilder().append(videoPath).append(storeId)
                .append("/videos/").append(videoCategory.name()).append("/").toString();
        try {
            String filePath = new StringBuilder(dirPath).append(file.getOriginalFilename()).toString();

            if (! Objects.isNull(videoRepoService.findByPath(filePath))){
                String updatedFileName = addSuffixToFileName(file.getOriginalFilename());
                filePath = new StringBuilder(dirPath).append(updatedFileName).toString();
            }
            log.info("file path : {} dire path : {} " , filePath, dirPath);
            createUploadDirectoryIfNotExists(dirPath);
            file.transferTo(new File(filePath));
            Videos savedVideo = videoRepoService.save(filePath,storeId,videoCategory);
            VideoUploadResponse response = VideoUploadResponse.builder()
                    .videoId(savedVideo.getId())
                    .storeId(savedVideo.getStore().getId())
                    .category(savedVideo.getCategory())
                    .message("Successfully Uploaded File")
                    .build();
            return response;
        } catch (IOException e) {
            log.info("Exception : {} ", e.getMessage() );
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

}
