package com.example.demo.service;

import com.example.demo.constants.Constants;
import com.example.demo.constants.ErrorCode;
import com.example.demo.entity.ShiftSchedule;
import com.example.demo.entity.Store;
import com.example.demo.exception.NotFoundException;
import com.example.demo.pojos.request.ShiftScheduleRequest;
import com.example.demo.pojos.response.ShiftScheduleResponse;
import com.example.demo.repository.ShiftScheduleRepoService;
import com.example.demo.repository.StoreRepoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
public class ShiftScheduleService {

    private final ShiftScheduleRepoService shiftScheduleRepoService;
    private final StoreRepoService storeRepoService;

    /**
     * Creates a new ShiftSchedule.
     * 
     * @param req The ShiftScheduleRequest object.
     * @return The ShiftScheduleResponse object.
     */
    public ShiftScheduleResponse createShiftSchedule(ShiftScheduleRequest req) {
        log.info("ShiftScheduleService createShiftSchedule request: {}", req);

        // Extracting User from JWT Token
        Store store = storeRepoService.findStoreById(req.getStoreId());

        if (store == null) {
            log.error("Store Not Found for Id : {}", req.getStoreId());
            throw new NotFoundException(ErrorCode.NOT_EXISTS, req.getStoreId(), Constants.FIELD_ID,
                    Constants.TABLE_STORE);
        }

        // Creating ShiftSchedule
        ShiftSchedule shiftSchedule = ShiftSchedule.builder()
                .storeId(req.getStoreId())
                .shiftId(req.getShiftId())
                .name(req.getShiftName())
                .date(req.getDate())
                .startTime(req.getStartTime())
                .endTime(req.getEndTime())
                .createdAt(ZonedDateTime.now())
                .updatedAt(ZonedDateTime.now())
                .build();

        // Saving ShiftSchedule
        ShiftSchedule savedShiftSchedule = shiftScheduleRepoService.save(shiftSchedule);

        // Creating ShiftSchedule Response
        ShiftScheduleResponse response = ShiftScheduleResponse.builder()
                .id(savedShiftSchedule.getId())
                .storeId(store.getId())
                .shiftId(savedShiftSchedule.getShiftId())
                .shiftName(savedShiftSchedule.getName())
                .date(req.getDate())
                .startTime(savedShiftSchedule.getStartTime())
                .endTime(savedShiftSchedule.getEndTime())
                .build();

        log.info("ShiftScheduleService createShiftSchedule ShiftSchedule Created : {}", response);

        return response;
    }

    /**
     * Updates a ShiftSchedule by its id.
     * 
     * @param id  The id of the ShiftSchedule.
     * @param req The ShiftScheduleRequest object.
     * @return The ShiftScheduleResponse object.
     */
    public ShiftScheduleResponse updateShiftScheduleById(UUID id, ShiftScheduleRequest req) {

        log.info("ShiftScheduleService updateShiftScheduleById \n request: {}\nrequested ID{}", req, id);

        // ShiftSchedule from Request
        ShiftSchedule shiftSchedule = shiftScheduleRepoService.findShiftScheduleById(id);

        if (shiftSchedule == null) {
            log.error("ShiftSchedule Not Found for Id : {}", id);
            throw new NotFoundException(ErrorCode.NOT_EXISTS, id, Constants.FIELD_ID, Constants.TABLE_SHIFT_SCHEDULE);
        }

        // Updating ShiftSchedule
        Optional.ofNullable(req.getStoreId()).ifPresent(shiftSchedule::setStoreId);
        Optional.ofNullable(req.getShiftId()).ifPresent(shiftSchedule::setShiftId);
        Optional.ofNullable(req.getShiftName()).ifPresent(shiftSchedule::setName);
        Optional.ofNullable(req.getDate()).ifPresent(shiftSchedule::setDate);
        Optional.ofNullable(req.getStartTime()).ifPresent(shiftSchedule::setStartTime);
        Optional.ofNullable(req.getEndTime()).ifPresent(shiftSchedule::setEndTime);

        shiftSchedule.setUpdatedAt(ZonedDateTime.now());

        // Saving ShiftSchedule
        ShiftSchedule savedShiftSchedule = shiftScheduleRepoService.save(shiftSchedule);

        // Creating ShiftSchedule Response
        ShiftScheduleResponse response = ShiftScheduleResponse.builder()
                .id(savedShiftSchedule.getId())
                .storeId(savedShiftSchedule.getStoreId())
                .shiftId(savedShiftSchedule.getShiftId())
                .shiftName(savedShiftSchedule.getName())
                .date(savedShiftSchedule.getDate())
                .startTime(savedShiftSchedule.getStartTime())
                .endTime(savedShiftSchedule.getEndTime())
                .build();

        log.info("ShiftScheduleService updateShiftScheduleById ShiftSchedule Updated : {}", response);

        return response;
    }

    /**
     * Finds a ShiftSchedule by its id.
     * 
     * @param id The id of the ShiftSchedule.
     * @return The ShiftSchedule object.
     */
    public ShiftScheduleResponse getShiftScheduleById(UUID id) {
        log.info("ShiftScheduleService getShiftScheduleById requested ID: {}", id);
        ShiftSchedule shiftSchedule = shiftScheduleRepoService.findShiftScheduleById(id);

        if (shiftSchedule == null) {
            log.error("ShiftSchedule Not Found for Id : {}", id);
            throw new NotFoundException(ErrorCode.NOT_EXISTS, id, Constants.FIELD_ID, Constants.TABLE_SHIFT_SCHEDULE);
        }

        ShiftScheduleResponse response = ShiftScheduleResponse.builder()
                .id(shiftSchedule.getId())
                .storeId(shiftSchedule.getShiftId())
                .shiftId(shiftSchedule.getShiftId())
                .shiftName(shiftSchedule.getName())
                .date(shiftSchedule.getDate())
                .startTime(shiftSchedule.getStartTime())
                .endTime(shiftSchedule.getEndTime())
                .build();

        log.info("ShiftScheduleService getShiftScheduleById received response for ShiftSchedule : {}", response);
        return response;
    }

    /**
     * Deletes a ShiftSchedule by its id.
     * 
     * @param id The id of the ShiftSchedule.
     * @return The message indicating the status of the deletion.
     */
    public String deleteShiftScheduleById(UUID id) {
        shiftScheduleRepoService.deleteShiftScheduleById(id);
        log.info("ShiftScheduleService deleteShiftScheduleById id {} deleted successfully", id);
        return "ShiftSchedule with id : " + id + " Deleted Successfully";
    }
}