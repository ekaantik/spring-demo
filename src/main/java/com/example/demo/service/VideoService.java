package com.example.demo.service;

import com.example.demo.Utils;
import com.example.demo.constants.VideoCategories;
import com.example.demo.entity.Videos;
import com.example.demo.pojos.response.VideoUploadResponse;
import com.example.demo.repository.VideoRepoService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
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
public class VideoService {

    @Value("${video.max-file-size}")
    private Integer videoFileSize;

    @Value("${video.file-format}")
    private List<String> fileFormat;

    @Value("${video.path}")
    private String videoPath;

    private final VideoRepoService videoRepoService;

    /**
     * Processes and uploads a video file for a specific store and category.
     *
     * @param videoCategory The category of the video.
     * @param file          The video file to be uploaded.
     * @param storeId       The unique identifier of the store.
     * @return VideoUploadResponse containing information about the uploaded video
     *         if successful.
     * @throws RuntimeException if an error occurs during the upload process.
     */
    public VideoUploadResponse processVideoForUpload(VideoCategories videoCategory, MultipartFile file, UUID storeId) {

        try {

            // Original Path
            String originalPath = Utils.buildDirectoryPath(videoPath,
                    storeId,
                    "videos",
                    videoCategory.toString(),
                    file.getOriginalFilename());

            String filePath = originalPath;

            // Create Unique Path
            int counter = 1;
            while (!Objects.isNull(videoRepoService.findByPath(filePath))) {
                filePath = Utils.addSuffixToFilePath(originalPath, counter);
                counter++;
            }

            // Create Directory
            Utils.createDirectory(filePath);

            // Save File
            File videoFile = new File(filePath);
            file.transferTo(videoFile);

            // Save Video
            Videos savedVideo = videoRepoService.save(filePath, storeId, videoCategory);

            // Build Response
            VideoUploadResponse response = VideoUploadResponse.builder()
                    .videoId(savedVideo.getId())
                    .storeId(savedVideo.getStore().getId())
                    .category(savedVideo.getCategory())
                    .message("Successfully Uploaded File")
                    .build();

            log.info("Video uploaded successfully with id : {}", savedVideo.getId());
            return response;
        } catch (IOException e) {
            log.error("IO Exception while uploading : {} ", e.getMessage());
            throw new RuntimeException("Error while uploading video, Exception : " + e.getMessage());
        }
    }

    /**
     * Downloads a video file identified by the given videoId.
     *
     * @param videoId The unique identifier of the video to be downloaded.
     * @return ResponseEntity containing the video file as an InputStreamResource if
     *         found,
     *         otherwise returns ResponseEntity with status NOT_FOUND.
     * @throws RuntimeException if an error occurs during the download process.
     */
    public ResponseEntity<InputStreamResource> downloadVideo(UUID videoId) {
        try {

            // Get Video
            Videos video = videoRepoService.findById(videoId);

            // No Video Found
            if (video == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            // Get File
            File file = new File(video.getPath());

            // No File Found
            if (!file.exists()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            // Open Stream
            MediaType mediaType = Utils.determineMediaType(video.getPath());
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

            return ResponseEntity.ok()
                    .contentType(mediaType)
                    .contentLength(file.length())
                    .body(resource);

        } catch (Exception e) {
            log.error("IO Exception while downloading : {} ", e.getMessage());
            throw new RuntimeException("Error while downloading video, Exception : " + e.getMessage());
        }
    }

    /**
     * Deletes a video file identified by the given videoId.
     *
     * @param videoId The unique identifier of the video to be deleted.
     * @throws RuntimeException if an error occurs during the deletion process.
     */
    public void deleteVideoById(UUID videoId) {

        // Get Video
        Videos video = videoRepoService.findById(videoId);

        // Delete Video
        if (video != null) {
            File file = new File(video.getPath());
            if (file.exists()) {
                file.delete();
            }

            // TODO : Handle case where file does not exist but data is present in DB
            videoRepoService.deleteById(videoId);
        }

        // Log
        else {
            log.info("Video not found with id : {} ", videoId);
            // TODO : Throw Exception?
        }
    }

}
