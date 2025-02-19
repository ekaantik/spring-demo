package com.example.demo.service;

import com.example.demo.constants.Constants;
import com.example.demo.constants.ErrorCode;
import com.example.demo.entity.Shift;
import com.example.demo.entity.Store;
import com.example.demo.exception.NotFoundException;
import com.example.demo.pojos.request.ShiftRequest;
import com.example.demo.pojos.response.ShiftResponse;
import com.example.demo.repository.ShiftRepoService;
import com.example.demo.repository.StoreRepoService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
public class ShiftService {

    private final ShiftRepoService shiftRepoService;
    private final StoreRepoService storeRepoService;

    private final RedisCacheService redisCacheService;

    /**
     * Creates a new Shift.
     *
     * @param req The ShiftRequest object.
     * @return The ShiftResponse object.
     */
    public ShiftResponse createShift(ShiftRequest req) {
        log.info("ShiftService createShift request: {}", req);

        // Extracting User from JWT Token
        Store store = storeRepoService.findStoreById(req.getStoreId());

        if (store == null) {
            log.error("Store Not Found for Id : {}", req.getStoreId());
            throw new NotFoundException(ErrorCode.NOT_EXISTS, req.getStoreId(), Constants.FIELD_ID,
                    Constants.TABLE_STORE);
        }

        // Creating Shift
        Shift shift = Shift.builder()
                .store(store)
                .name(req.getShiftName())
                .startTime(req.getStartTime())
                .endTime(req.getEndTime())
                .createdAt(ZonedDateTime.now())
                .updatedAt(ZonedDateTime.now())
                .build();

        // Saving Shift
        Shift savedShift = shiftRepoService.save(shift);

        // Creating Shift Response
        ShiftResponse response = ShiftResponse.builder()
                .id(savedShift.getId())
                .storeId(store.getId())
                .shiftName(savedShift.getName())
                .startTime(savedShift.getStartTime())
                .endTime(savedShift.getEndTime())
                .build();

        log.info("ShiftService createShift Shift Created : {}", response);

        return response;
    }

    /**
     * Updates a Shift.
     * 
     * @param id  The id of the Shift.
     * @param req The ShiftRequest object.
     * @return The ShiftResponse object.
     */
    @Transactional
    public ShiftResponse updateShift(UUID id, ShiftRequest req) {

        log.info("ShiftService updateShift requested ID: {} req {} ", id, req);

        // Shift & Store from request
        Shift shift = shiftRepoService.findShiftById(id);
        Store store = storeRepoService.findStoreById(req.getStoreId());

        // Shift Not Found
        if (shift == null) {
            log.error("Shift Not Found for Id : {}", id);
            throw new NotFoundException(ErrorCode.NOT_EXISTS, id, Constants.FIELD_ID, Constants.TABLE_SHIFT);
        }

        // Store Not Found
        if (store == null) {
            log.error("Store Not Found for Id : {}", req.getStoreId());
            throw new NotFoundException(ErrorCode.NOT_EXISTS, req.getStoreId(), Constants.FIELD_ID,
                    Constants.TABLE_STORE);
        }

        // Updating Shift
        if (Optional.ofNullable(req.getStoreId()).isPresent())
            shift.setStore(store);
        Optional.ofNullable(req.getShiftName()).ifPresent(shift::setName);
        Optional.ofNullable(req.getStartTime()).ifPresent(shift::setStartTime);
        Optional.ofNullable(req.getEndTime()).ifPresent(shift::setEndTime);

        shift.setUpdatedAt(ZonedDateTime.now());

        // Saving Shift
        Shift savedShift = shiftRepoService.save(shift);

        // Creating Shift Response
        ShiftResponse shiftResponse = ShiftResponse.builder()
                .id(savedShift.getId())
                .storeId(savedShift.getStore().getId())
                .shiftName(savedShift.getName())
                .startTime(savedShift.getStartTime())
                .endTime(savedShift.getEndTime())
                .build();

        redisCacheService.saveShiftById(id.toString(), shiftResponse);

        log.info("ShiftService updateShift Shift Updated : {}", shiftResponse);

        return shiftResponse;

    }

    /**
     * Finds a Shift by its id.
     * 
     * @param id The id of the Shift.
     * @return The Shift object.
     */
    public ShiftResponse getShiftById(UUID id) {
        log.info("ShiftService getShiftById requested ID: {}", id);

        ShiftResponse response = redisCacheService.getShiftById(id.toString());

        if (response != null) {
            log.info("ShiftService getShiftById getting response from redis cache: {}", id);
            return response;
        }

        Shift shift = shiftRepoService.findShiftById(id);

        if (shift == null) {
            log.error("Shift Not Found for Id : {}", id);
            throw new NotFoundException(ErrorCode.NOT_EXISTS, id, Constants.FIELD_ID, Constants.TABLE_SHIFT);
        }

        response = ShiftResponse.builder()
                .id(shift.getId())
                .storeId(shift.getStore().getId())
                .shiftName(shift.getName())
                .startTime(shift.getStartTime())
                .endTime(shift.getEndTime())
                .build();

        redisCacheService.saveShiftById(id.toString(), response);
        log.info("ShiftService getShiftById Shift Saved to Redis Cache : {}", shift);
        return response;
    }

    /**
     * Finds a Shift by its id.
     *
     * @param id The id of the Shift.
     * @return The Shift object.
     */
    public ShiftResponse getShiftByStoreId(UUID id) {
        Store store = storeRepoService.findStoreById(id);

        if (store == null) {
            log.error("Store Not Found for Id : {}", id);
            throw new NotFoundException(ErrorCode.NOT_EXISTS, id, Constants.FIELD_ID, Constants.TABLE_STORE);
        }

        Shift shift = shiftRepoService.findShiftByStoreId(store.getId());
        log.info("ShiftService getShiftByStoreId requested Store ID: {}", id);
        log.info("ShiftService getShiftByStoreId recieved ID of shift by store: {}", shift);

        ShiftResponse response = new ShiftResponse();
        if (!Objects.isNull(shift)) {
            response = ShiftResponse.builder()
                    .id(shift.getId())
                    .storeId(shift.getStore().getId())
                    .shiftName(shift.getName())
                    .startTime(shift.getStartTime())
                    .endTime(shift.getEndTime())
                    .build();
        }

        log.info("ShiftService getShiftByStoreId response received for Shift : {}", response);
        return response;
    }

    /**
     * Deletes a Shift by its id.
     * 
     * @param id The id of the Shift.
     * @return The message indicating the status of the deleton.
     */
    public ResponseEntity<Map<String, String>> deleteShiftById(UUID id) {

        log.info("ShiftService deleteShiftById deleted shift for ID: {}", id);

        boolean exists = shiftRepoService.existsById(id);

        if (!exists) {
            log.error("Shift Not Found for Id : {}", id);
            throw new NotFoundException(ErrorCode.NOT_EXISTS, id, Constants.FIELD_ID, Constants.TABLE_SHIFT);
        }

        // Clearing Redis & Deleting Shift
        redisCacheService.clearShiftById(id.toString());
        shiftRepoService.deleteShiftById(id);

        // Response
        Map<String, String> response = new HashMap<>();
        response.put("message", "Shift deleted successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}