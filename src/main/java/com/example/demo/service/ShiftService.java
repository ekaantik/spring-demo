package com.example.demo.service;

import com.example.demo.entity.Shift;
import com.example.demo.entity.Store;
import com.example.demo.pojos.request.ShiftRequest;
import com.example.demo.pojos.response.ShiftResponse;
import com.example.demo.repository.ShiftRepoService;
import com.example.demo.repository.StoreRepoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
public class ShiftService {

    private final ShiftRepoService shiftRepoService;
    private final StoreRepoService storeRepoService;

    public ShiftResponse createShift(ShiftRequest req) {

        // Extracting User from JWT Token
        Store store = storeRepoService.findStoreById(req.getStoreId());

        // Creating Shift
        Shift shift = Shift.builder()
                .storeId(store.getId())
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
                .storeId(savedShift.getStoreId())
                .build();

        log.info("Shift Created : {}", response);

        return response;
    }

    /**
     * Finds a Shift by its id.
     * 
     * @param id The id of the Shift.
     * @return The Shift object.
     */
    public ShiftResponse getShiftById(UUID id) {
        Shift shift = shiftRepoService.findShiftById(id);
        ShiftResponse response = ShiftResponse.builder()
                .id(shift.getId())
                .storeId(shift.getStoreId())
                .shiftName(shift.getName())
                .startTime(shift.getStartTime())
                .endTime(shift.getEndTime())
                .storeId(shift.getStoreId())
                .build();
        log.info("Shift Found : {}", response);
        return response;
    }

    /**
     * Deletes a Shift by its id.
     * 
     * @param id The id of the Shift.
     * @return The message indicating the status of the deletion.
     */
    public String deleteShiftById(UUID id) {
        shiftRepoService.deleteShiftById(id);
        return "Shift with id : " + id + " Deleted Successfully";
    }
}