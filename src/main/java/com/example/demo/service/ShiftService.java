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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Objects;
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
    public String deleteShiftById(UUID id) {
        redisCacheService.clearShiftById(id.toString());
        log.info("ShiftService deleteShiftByIkd deleted shift for ID: {}", id);

        return "Shift with id : " + id + " Deleted Successfully";
    }
}