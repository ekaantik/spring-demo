package com.example.demo.repository;

import com.example.demo.constants.Constants;
import com.example.demo.entity.Images;
import com.example.demo.entity.Store;
import jakarta.persistence.PersistenceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImageRepoService {

    private final ImageRepo imageRepo;

    public Images findStoreById(UUID id) {
        try {
            Optional<Images> optionalStore = imageRepo.findById(id);

            if (optionalStore.isPresent()) {
                Images images = optionalStore.get();
                log.info("Successfully found Store with id " + images.getId());
                return images;
            } else {
                log.info("Store with id  {} not found ", id);
                return null;
            }
        }
        catch (Exception ex) {
            log.error(Constants.UNEXPECTED_ERROR_MSG, ex);
            throw new PersistenceException(ex);
        }
    }

    public List<Images> findAll() {
        try {
            List<Images> images = imageRepo.findAll();
            log.info("Successfully found all Store.");
            return images;
        }
        catch (Exception ex) {
            log.error(Constants.UNEXPECTED_ERROR_MSG, ex);
            throw new PersistenceException(ex);
        }
    }

    public Images save(Images images) {
        try {
            Images image = imageRepo.save(images);
            log.info("Successfully saved Store with id  {} ", image.getId());
            return image;
        }
        catch (Exception ex) {
            log.error("Failed to create Store, Exception : " + ex.getMessage(), ex);
            throw new PersistenceException("Failed To create Store record into database!", ex);
        }
    }


    public void deleteStoreById(UUID id) {
        try {
            imageRepo.deleteById(id);
            log.info("Successfully deleted Store with id " + id);
        }
        catch (Exception ex) {
            log.error(Constants.UNEXPECTED_ERROR_MSG, ex);
            throw new PersistenceException(ex);
        }
    }

}
