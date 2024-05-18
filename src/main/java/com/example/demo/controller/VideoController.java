package com.example.demo.controller;

import com.example.demo.constants.VideoCategories;
import com.example.demo.pojos.response.VideoUploadResponse;
import com.example.demo.service.VideoService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/video")
//@MultipartConfig(maxFileSize = "20MB", maxRequestSize = "30MB")
@Slf4j
public class VideoController {

    @Value("${video.max-file-size}")
    private long videoFileSize;

    @Value("${video.file-format}")
    private List<String> fileFormat;
    @Autowired
    public VideoService videoService;


    @PostMapping("/upload")
    @PreAuthorize("hasAnyAuthority('VENDOR')") //'ROLE_ADMIN'
    public ResponseEntity<VideoUploadResponse> uploadVideo(@Valid @RequestHeader(value = "videoCategory") VideoCategories videoCategory,
                                              @RequestParam("file") MultipartFile file,
                                              @RequestParam("storeId") UUID storeId ) {
        VideoUploadResponse response ;
        if (file.isEmpty()) {
            response = VideoUploadResponse.builder().message("Please select a file to upload")
                    .build();
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
//        if (file.getSize() > videoFileSize) {
//            response = VideoUploadResponse.builder().message("Please select a file with size less than " + videoFileSize + " bytes" )
//                    .build();
//            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
//
//        }
        log.info("file.getContentType() : {} ", file.getContentType());

        if (!fileFormat.contains(file.getContentType().replace("video/",""))){
            response = VideoUploadResponse.builder().message("Please select a valid file to upload")
                    .build();
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        response = videoService.processVideoForUpload(videoCategory, file, storeId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

//    @GetMapping("/download-video/{fileName:.+}")
//@GetMapping("/download/{videoId}")
//public ResponseEntity<Resource> downloadVideo(@PathVariable String videoId) throws IOException {
//    Path videoPath = Paths.get(VIDEO_DIRECTORY, videoId);
//    Resource resource = new PathResource(videoPath.toFile());
//
//    return ResponseEntity.ok()
//            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
//            .contentType(MediaType.parseMediaType(Files.probeContentType(videoPath)))
//            .body(resource);
//}
}

