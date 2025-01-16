package com.example.demo.repository;

import jakarta.persistence.PersistenceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.example.demo.constants.Constants;
import com.example.demo.entity.Shift;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ShiftRepoService {

    private final ShiftRepo shiftRepo;

    /**
     * Handles Repo Exception & finds a Shift by its Id.
     *
     * @param id The Id of the Shift to find.
     * @return The found Shift, or null if Exception.
     */
    public Shift findShiftById(UUID id) {
        try {
            Optional<Shift> optionalShift = shiftRepo.findById(id);
            if (optionalShift.isPresent()) {
                Shift Shift = optionalShift.get();
                log.info("Successfully found Shift with id " + Shift.getId());
                return Shift;
            } else {
                log.info("Shift with id " + id + " not found.");
                return null;
            }

        } catch (Exception ex) {
            log.error(Constants.UNEXPECTED_ERROR_MSG, ex);
            throw new PersistenceException("Failed to find Shift with Id " + id + ".", ex);
        }
    }

    /**
     * Handles Repo Exception & finds a Shift by its Id.
     *
     * @param id The Id of the Shift to find.
     * @return The found Shift, or null if Exception.
     */
    public Shift findShiftByStoreId(UUID id) {
        try {
            Optional<Shift> optionalShift = shiftRepo.findByStoreId(id);
            if (optionalShift.isPresent()) {
                Shift Shift = optionalShift.get();
                log.info("Successfully found Shift with id " + Shift.getId());
                return Shift;
            } else {
                log.info("Shift with id " + id + " not found.");
                return null;
            }
        } catch (Exception ex) {
            log.error(Constants.UNEXPECTED_ERROR_MSG, ex);
            throw new PersistenceException("Failed to find Shift with StoreId " + id + ".", ex);
        }
    }

    /**
     * Handles Repo Exception & finds all Shift entities.
     *
     * @return A list of all Shift entities, or null if Exception.
     */
    public List<Shift> findAll() {

        // Trying to find all Shift
        try {

            // Succesfully found all Shift
            List<Shift> shifts = shiftRepo.findAll();
            log.info("Successfully found all Shift.");
            return shifts;
        }

        // Unexpected Error
        catch (Exception ex) {
            log.error(Constants.UNEXPECTED_ERROR_MSG, ex);
            throw new PersistenceException("Failed to find All Shifts.", ex);
        }
    }

    /**
     * Handles Repo Exception & saves a Shift entity.
     *
     * @param Shift The Shift to be saved.
     * @return The saved Shift, or throws PersistenceException.
     */

    public Shift save(Shift Shift) {
        try {
            Shift savedShift = shiftRepo.save(Shift);
            log.info("Successfully saved Shift with id " + Shift.getId());
            return savedShift;
        } catch (Exception ex) {
            log.error("Failed to create Shift, Exception : {}", ex.getMessage());
            throw new PersistenceException("Failed To create Shift record into database!", ex);
        }
    }

    /**
     * Handles Repo Exception & deletes a Shift by its Id.
     *
     * @param id The Id of the Shift to be deleted.
     */
    public void deleteShiftById(UUID id) {
        try {
            shiftRepo.deleteById(id);
            log.info("Successfully deleted Shift with id " + id);
        } catch (Exception ex) {
            log.error(Constants.UNEXPECTED_ERROR_MSG, ex);
            throw new PersistenceException("Failed to delete Shift with Id " + id + ".", ex);

        }
    }

    /**
     * Handles Repo Exception & deletes a Shift by its Id.
     *
     * @param id The Id of the Shift to be deleted.
     */
    public boolean existsById(UUID id) {
        return shiftRepo.existsById(id);
    }

}
