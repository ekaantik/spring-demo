package com.example.demo.repository;

import com.example.demo.constants.Constants;
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
public class VideoRepoService {

    private final StoreRepo storeRepo;

    /**
     * Handles Repo Exception & finds a Store by its Id.
     *
     * @param id The Id of the Store to find.
     * @return The found Store, or null if Exception.
     */
    public Store findStoreById(UUID id) {

        // Trying to find Store details by id
        try {

            // Succesfully found Store
            Optional<Store> optionalStore = storeRepo.findById(id);

            if (optionalStore.isPresent()) {
                Store Store = optionalStore.get();
                log.info("Successfully found Store with id " + Store.getId());
                return Store;
            } else {
                log.warn("Store with id " + id + " not found.");
                return null;
            }

        }

        // Unexpected Error
        catch (Exception ex) {
            log.error(Constants.UNEXPECTED_ERROR_MSG, ex);
            throw new PersistenceException(ex);
        }

    }

    /**
     * Handles Repo Exception & finds all Store entities.
     *
     * @return A list of all Store entities, or null if Exception.
     */
    public List<Store> findAll() {

        // Trying to find all Store
        try {

            // Succesfully found all Store
            List<Store> Stores = storeRepo.findAll();
            log.info("Successfully found all Store.");
            return Stores;
        }

        // Unexpected Error
        catch (Exception ex) {
            log.error(Constants.UNEXPECTED_ERROR_MSG, ex);
            throw new PersistenceException(ex);
        }
    }

    /**
     * Handles Repo Exception & saves a Store entity.
     *
     * @param Store The Store to be saved.
     * @return The saved Store, or throws PersistenceException.
     */
    public Store save(Store Store) {

        // Trying to save Store
        try {

            // Store saved succesfully
            Store savedStore = storeRepo.save(Store);
            log.info("Successfully saved Store with id " + Store.getId());
            return savedStore;
        }

        // Unexpected Error Occured
        catch (Exception ex) {
            log.error("Failed to create Store, Exception : " + ex.getMessage(), ex);
            throw new PersistenceException("Failed To create Store record into database!", ex);
        }
    }

    /**
     * Handles Repo Exception & deletes a Store by its Id.
     *
     * @param id The Id of the Store to be deleted.
     */
    public void deleteStoreById(UUID id) {

        // Trying to delete Store
        try {

            // Store deleted succesfully
            storeRepo.deleteById(id);
            log.info("Successfully deleted Store with id " + id);
        }

        // Unexpected Error
        catch (Exception ex) {
            log.error(Constants.UNEXPECTED_ERROR_MSG, ex);
            throw new PersistenceException(ex);
        }
    }

}
