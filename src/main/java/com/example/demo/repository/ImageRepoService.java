package com.example.demo.repository;

import com.example.demo.constants.Constants;
import com.example.demo.constants.ImageCategories;
import com.example.demo.entity.Images;
import com.example.demo.entity.Store;
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
public class ImageRepoService {

    private final ImageRepo imageRepo;
    private final StoreRepoService storeRepoService;

    /**
     * Handles Repo Exception & saves a new Image record.
     *
     * @param path            The path of the Image to save.
     * @param storeId         The Id of the Store to associate the Image with.
     * @param imageCategories The category of the Image.
     * @return The saved Image, or null if Exception.
     */
    public Images save(String path, UUID storeId, ImageCategories imageCategories) {
        try {
            Store store = storeRepoService.findStoreById(storeId);
            if (!Objects.isNull(store)) {
                Images image = Images.builder()
                        .store(store)
                        .path(path)
                        .category(imageCategories)
                        .createdAt(ZonedDateTime.now())
                        .updatedAt(ZonedDateTime.now())
                        .build();
                Images savedImage = imageRepo.save(image);
                log.info("Successfully saved Store with id  {} ", image.getId());
                return savedImage;
            } else {
                throw new Exception();
            }
        } catch (Exception ex) {
            log.error("Failed to create Store, Exception : " + ex.getMessage(), ex);
            throw new PersistenceException("Failed To create Store record into database!", ex);
        }
    }

    /**
     * Handles Repo Exception & finds an Image by its path.
     *
     * @param path The path of the Image to find.
     * @return The found Image, or null if Exception.
     */
    public Images findByPath(String path) {
        try {
            Optional<Images> image = imageRepo.findByPath(path);
            return image.orElse(null);
        } catch (Exception ex) {
            log.error(Constants.UNEXPECTED_ERROR_MSG, ex);
            throw new PersistenceException(ex);
        }
    }

    /**
     * Handles Repo Exception & finds an Image by its Id.
     *
     * @param id The Id of the Image to find.
     * @return The found Image, or null if Exception.
     */
    public Images findById(UUID id) {
        try {
            Optional<Images> image = imageRepo.findById(id);
            return image.orElse(null);
        } catch (Exception ex) {
            log.error(Constants.UNEXPECTED_ERROR_MSG, ex);
            throw new PersistenceException(ex);
        }
    }

    /**
     * Handles Repo Exception & deletes an Image by its Id.
     *
     * @param id The Id of the Image to delete.
     */
    public void deleteById(UUID id) {
        try {
            imageRepo.deleteById(id);
            log.info("Successfully deleted Store with id " + id);
        } catch (Exception ex) {
            log.error(Constants.UNEXPECTED_ERROR_MSG, ex);
            throw new PersistenceException(ex);
        }
    }

    /**
     * Handles Repo Exception & checks if an Image exists by its Id.
     *
     * @param id The Id of the Image to check.
     * @return True if the Image exists, False otherwise.
     */
    public boolean existsById(UUID id) {
        return imageRepo.existsById(id);
    }

}
