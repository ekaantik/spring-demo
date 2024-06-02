package com.example.demo.repository;

import com.example.demo.constants.Constants;
import com.example.demo.constants.VideoCategories;
import com.example.demo.entity.Store;
import com.example.demo.entity.Videos;
import jakarta.persistence.PersistenceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class VideoRepoService {

    private final VideoRepo videoRepo;
    private final StoreRepoService storeRepoService;

    /**
     * Handles Repo Exception & saves a new Video record.
     *
     * @param path          The path of the Video to save.
     * @param storeId       The Id of the Store to associate the Video with.
     * @param videoCategory The category of the Video.
     * @return The saved Video, or null if Exception.
     */
    public Videos save(String path, UUID storeId, VideoCategories videoCategory) {
        try {
            Store store = storeRepoService.findStoreById(storeId);
            if (!Objects.isNull(store)) {
                Videos video = Videos.builder()
                        .store(store)
                        .path(path)
                        .category(videoCategory)
                        .createdAt(ZonedDateTime.now())
                        .updatedAt(ZonedDateTime.now())
                        .build();
                Videos savedVideos = videoRepo.save(video);
                log.info("Successfully saved video with id  {} ", video.getId());
                return savedVideos;
            } else {
                throw new Exception();
            }
        } catch (Exception ex) {
            log.error("Failed to create Store, Exception : " + ex.getMessage(), ex);
            throw new PersistenceException("Failed To create Store record into database!", ex);
        }
    }

    /**
     * Handles Repo Exception & finds all Videos.
     *
     * @return A list of all Videos, or null if Exception.
     */
    public Videos findById(UUID id) {
        try {
            Optional<Videos> videos = videoRepo.findById(id);
            return videos.orElse(null);
        } catch (Exception ex) {
            log.error(Constants.UNEXPECTED_ERROR_MSG, ex);
            throw new PersistenceException(ex);
        }
    }

    /**
     * Handles Repo Exception & finds all Videos.
     *
     * @return A list of all Videos, or null if Exception.
     */
    public Videos findByPath(String path) {
        try {
            Optional<Videos> videos = videoRepo.findByPath(path);
            return videos.orElse(null);
        } catch (Exception ex) {
            log.error(Constants.UNEXPECTED_ERROR_MSG, ex);
            throw new PersistenceException(ex);
        }
    }

    /**
     * Handles Repo Exception & deletes a Video by its Id.
     *
     * @param id The Id of the Video to delete.
     */
    public void deleteById(UUID id) {
        try {
            videoRepo.deleteById(id);
            log.info("Successfully deleted Store with id " + id);
        } catch (Exception ex) {
            log.error(Constants.UNEXPECTED_ERROR_MSG, ex);
            throw new PersistenceException(ex);
        }
    }

    /**
     * Handles Repo Exception & finds a Video by its path.
     *
     * @param path The path of the Video to find.
     * @return The found Video, or null if Exception.
     */
    public boolean existsById(UUID id) {
        return videoRepo.existsById(id);
    }
}
