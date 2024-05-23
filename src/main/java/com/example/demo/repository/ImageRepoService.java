package com.example.demo.repository;

import com.example.demo.constants.Constants;
import com.example.demo.constants.ImageCategories;
import com.example.demo.entity.Images;
import com.example.demo.entity.Shift;
import com.example.demo.entity.Store;
import com.example.demo.security.utils.JwtTokenService;
import jakarta.persistence.PersistenceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class ImageRepoService {

    private final ImageRepo imageRepo;
    private final StoreRepoService storeRepoService;

    @Autowired
    public ImageRepoService(ImageRepo imageRepo, StoreRepoService storeRepoService){
        this.imageRepo = imageRepo;
        this.storeRepoService = storeRepoService;
    }


    public Images save(String path, UUID storeId, ImageCategories imageCategories) {
        try {
            Store store = storeRepoService.findStoreById(storeId);
            if(!Objects.isNull(store)){
                Images image = Images.builder()
                        .store(store)
                        .path(path)
                        .category(imageCategories)
                        .createdAt(ZonedDateTime.now())
                        .updatedAt(ZonedDateTime.now())
                        .build();
                Images savedImage =  imageRepo.save(image);
                log.info("Successfully saved Store with id  {} ", image.getId());
                return savedImage;
            }else{
                throw new Exception();
            }
        }
        catch (Exception ex) {
            log.error("Failed to create Store, Exception : " + ex.getMessage(), ex);
            throw new PersistenceException("Failed To create Store record into database!", ex);
        }
    }


    public Images findByPath(String path) {
        try {
            Optional<Images> image = imageRepo.findByPath(path);
            return image.orElse(null);
        }
        catch (Exception ex) {
            log.error(Constants.UNEXPECTED_ERROR_MSG, ex);
            throw new PersistenceException(ex);
        }
    }

    public Images findById(UUID id) {
        try {
            Optional<Images> image = imageRepo.findById(id);
            return image.orElse(null);
        }
        catch (Exception ex) {
            log.error(Constants.UNEXPECTED_ERROR_MSG, ex);
            throw new PersistenceException(ex);
        }
    }

    public void deleteById(UUID id) {
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
