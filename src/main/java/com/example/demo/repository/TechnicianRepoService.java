package com.example.demo.repository;

import jakarta.persistence.PersistenceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.example.demo.constants.Constants;
import com.example.demo.entity.Technician;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class TechnicianRepoService {

    private final TechnicianRepository technicianRepo;

    /**
     * Handles Repo Exception & finds a Technician by its Id.
     *
     * @param id The Id of the Technician to find.
     * @return The found Technician, or null if Exception.
     */
    public Technician findTechnicianById(UUID id) {

        // Trying to find Technician details by id
        try {

            // Succesfully found Technician
            Optional<Technician> optionalTechnician = technicianRepo.findById(id);

            if (optionalTechnician.isPresent()) {
                Technician Technician = optionalTechnician.get();
                log.info("Successfully found Technician with id " + Technician.getId());
                return Technician;
            } else {
                log.info("Technician with id " + id + " not found.");
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
     * Handles Repo Exception & finds all Technician entities.
     *
     * @return A list of all Technician entities, or null if Exception.
     */
    public List<Technician> findAll() {

        // Trying to find all Technician
        try {

            // Succesfully found all Technician
            List<Technician> Technicians = technicianRepo.findAll();
            log.info("Successfully found all Technician.");
            return Technicians;
        }

        // Unexpected Error
        catch (Exception ex) {
            log.error(Constants.UNEXPECTED_ERROR_MSG, ex);
            throw new PersistenceException(ex);
        }
    }

    /**
     * Handles Repo Exception & saves a Technician entity.
     *
     * @param Technician The Technician to be saved.
     * @return The saved Technician, or throws PersistenceException.
     */
    public Technician save(Technician Technician) {

        // Trying to save Technician
        try {

            // Technician saved succesfully
            Technician savedTechnician = technicianRepo.save(Technician);
            log.info("Successfully saved Technician with id " + Technician.getId());
            return savedTechnician;
        }

        // Unexpected Error Occured
        catch (Exception ex) {
            log.error("Failed to create Technician, Exception : " + ex.getMessage(), ex);
            throw new PersistenceException("Failed To create Technician record into database!", ex);
        }
    }

    /**
     * Handles Repo Exception & deletes a Technician by its Id.
     *
     * @param id The Id of the Technician to be deleted.
     */
    public void deleteTechnicianById(UUID id) {

        // Trying to delete Technician
        try {

            // Technician deleted succesfully
            technicianRepo.deleteById(id);
            log.info("Successfully deleted Technician with id " + id);
        }

        // Unexpected Error
        catch (Exception ex) {
            log.error(Constants.UNEXPECTED_ERROR_MSG, ex);
            throw new PersistenceException(ex);
        }
    }

}
