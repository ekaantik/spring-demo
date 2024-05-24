package com.example.demo.repository;

import jakarta.persistence.PersistenceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.example.demo.constants.Constants;
import com.example.demo.entity.ShiftSchedule;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ShiftScheduleRepoService {

    private final ShiftScheduleRepo shiftScheduleRepo;

    /**
     * Handles Repo Exception & finds a ShiftSchedule by its Id.
     *
     * @param id The Id of the ShiftSchedule to find.
     * @return The found ShiftSchedule, or null if Exception.
     */
    public ShiftSchedule findShiftScheduleById(UUID id) {

        // Trying to find ShiftSchedule details by id
        try {

            // Succesfully found ShiftSchedule
            Optional<ShiftSchedule> optionalShiftSchedule = shiftScheduleRepo.findById(id);

            if (optionalShiftSchedule.isPresent()) {
                ShiftSchedule shiftSchedule = optionalShiftSchedule.get();
                log.info("Successfully found ShiftSchedule with id " + shiftSchedule.getId());
                return shiftSchedule;
            } else {
                log.info("ShiftSchedule with id " + id + " not found.");
                return null;
            }

        }

        // Unexpected Error
        catch (Exception ex) {
            log.error(Constants.UNEXPECTED_ERROR_MSG, ex);
            throw new PersistenceException("Failed to find Shift Schedule with Id " + id + ".", ex);
        }

    }

    /**
     * Handles Repo Exception & finds all ShiftSchedule entities.
     *
     * @return A list of all ShiftSchedule entities, or null if Exception.
     */
    public List<ShiftSchedule> findAll() {

        // Trying to find all ShiftSchedule
        try {

            // Succesfully found all ShiftSchedule
            List<ShiftSchedule> shiftSchedules = shiftScheduleRepo.findAll();
            log.info("Successfully found all ShiftSchedule.");
            return shiftSchedules;
        }

        // Unexpected Error
        catch (Exception ex) {
            log.error(Constants.UNEXPECTED_ERROR_MSG, ex);
            throw new PersistenceException("Failed to find All Shift Schedules.", ex);
        }
    }

    /**
     * Handles Repo Exception & saves a ShiftSchedule entity.
     *
     * @param ShiftSchedule The ShiftSchedule to be saved.
     * @return The saved ShiftSchedule, or throws PersistenceException.
     */
    public synchronized ShiftSchedule save(ShiftSchedule ShiftSchedule) {

        // Trying to save ShiftSchedule
        try {

            // ShiftSchedule saved succesfully
            ShiftSchedule savedShiftSchedule = shiftScheduleRepo.save(ShiftSchedule);
            log.info("Successfully saved ShiftSchedule with id " + ShiftSchedule.getId());
            return savedShiftSchedule;
        }

        // Unexpected Error Occured
        catch (Exception ex) {
            log.error("Failed to create ShiftSchedule, Exception : " + ex.getMessage(), ex);
            throw new PersistenceException("Failed To create ShiftSchedule record into database!", ex);
        }
    }

    /**
     * Handles Repo Exception & deletes a ShiftSchedule by its Id.
     *
     * @param id The Id of the ShiftSchedule to be deleted.
     */
    public void deleteShiftScheduleById(UUID id) {

        // Trying to delete ShiftSchedule
        try {

            // ShiftSchedule deleted succesfully
            shiftScheduleRepo.deleteById(id);
            log.info("Successfully deleted ShiftSchedule with id " + id);
        }

        // Unexpected Error
        catch (Exception ex) {
            log.error(Constants.UNEXPECTED_ERROR_MSG, ex);
            throw new PersistenceException("Failed to delete Shift Schedule with Id " + id + ".", ex);

        }
    }

}
