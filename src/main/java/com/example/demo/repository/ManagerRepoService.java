package com.example.demo.repository;

import jakarta.persistence.PersistenceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.example.demo.constants.Constants;
import com.example.demo.entity.Manager;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ManagerRepoService {

    private final ManagerRepository managerRepo;

    /**
     * Handles Repo Exception & finds a Manager by its Id.
     *
     * @param id The Id of the Manager to find.
     * @return The found Manager, or null if Exception.
     */
    public Manager findManagerById(UUID id) {

        // Trying to find Manager details by id
        try {

            // Succesfully found Manager
            Optional<Manager> optionalManager = managerRepo.findById(id);

            if (optionalManager.isPresent()) {
                Manager Manager = optionalManager.get();
                log.info("Successfully found Manager with id " + Manager.getId());
                return Manager;
            } else {
                log.warn("Manager with id " + id + " not found.");
                return null;
            }

        }

        // Unexpected Error
        catch (Exception ex) {
            log.error(Constants.UNEXPECTED_ERROR_MSG, ex);
            throw new PersistenceException("Failed to find Manager with Id " + id + ".", ex);
        }
    }

    /**
     * Handles Repo Exception & finds all Manager entities.
     *
     * @return A list of all Manager entities, or null if Exception.
     */
    public List<Manager> findAll() {

        // Trying to find all Manager
        try {

            // Succesfully found all Manager
            List<Manager> Managers = managerRepo.findAll();
            log.info("Successfully found all Manager.");
            return Managers;
        }

        // Unexpected Error
        catch (Exception ex) {
            log.error(Constants.UNEXPECTED_ERROR_MSG, ex);
            throw new PersistenceException("Failed to find All Managers.", ex);
        }
    }

    /**
     * Handles Repo Exception & saves a Manager entity.
     *
     * @param Manager The Manager to be saved.
     * @return The saved Manager, or throws PersistenceException.
     */
    public Manager save(Manager Manager) {

        // Trying to save Manager
        try {

            // Manager saved succesfully
            Manager savedManager = managerRepo.save(Manager);
            log.info("Successfully saved Manager with id " + Manager.getId());
            return savedManager;
        }

        // Unexpected Error Occured
        catch (Exception ex) {
            log.error("Failed to create Manager, Exception : " + ex.getMessage(), ex);
            throw new PersistenceException("Failed To create Manager record into database!", ex);
        }
    }

    /**
     * Handles Repo Exception & deletes a Manager by its Id.
     *
     * @param id The Id of the Manager to be deleted.
     */
    public void deleteManagerById(UUID id) {

        // Trying to delete Manager
        try {

            // Manager deleted succesfully
            managerRepo.deleteById(id);
            log.info("Successfully deleted Manager with id " + id);
        }

        // Unexpected Error
        catch (Exception ex) {
            log.error(Constants.UNEXPECTED_ERROR_MSG, ex);
            throw new PersistenceException("Failed to delete Manager with Id " + id + ".", ex);
        }
    }

}
