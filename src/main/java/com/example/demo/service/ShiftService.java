package com.example.demo.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Shift;
import com.example.demo.entity.Store;
import com.example.demo.pojos.request.ShiftRequest;
import com.example.demo.pojos.response.ShiftResponse;
import com.example.demo.repository.ShiftRepoService;
import com.example.demo.repository.StoreRepoService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class ShiftService {

    private final ShiftRepoService shiftRepoService;
    private final StoreRepoService storeRepoService;

    public ShiftResponse createShift(ShiftRequest shiftRequest) {

        // Extracting User from JWT Token
        Store store = storeRepoService.findStoreById(shiftRequest.getStoreId());

        // Creating Shift
        Shift shift = Shift.builder()
                .shiftType(shiftRequest.getShiftType())
                .store(store)
                .build();

        // Saving Shift
        Shift savedShift = shiftRepoService.save(shift);

        // Creating Shift Response
        ShiftResponse shiftResponse = ShiftResponse.builder()
                .id(savedShift.getId())
                .shiftType(savedShift.getShiftType().toString())
                .storeId(savedShift.getStore().getId())
                .build();

        log.info("Shift Created : {}", shiftResponse);

        return shiftResponse;
    }

    /**
     * Finds a Shift by its id.
     * 
     * @param id The id of the Shift.
     * @return The Shift object.
     */
    public ShiftResponse getShiftById(UUID id) {
        Shift shift = shiftRepoService.findShiftById(id);

        ShiftResponse shiftResponse = ShiftResponse.builder()
                .id(shift.getId())
                .shiftType(shift.getShiftType().toString())
                .storeId(shift.getStore().getId())
                .build();

        log.info("Shift Found : {}", shiftResponse);
        return shiftResponse;
    }

    /**
     * Deletes a Shift by its id.
     * 
     * @param id The id of the Shift.
     * @return The message indicating the status of the deletion.
     */
    public String deleteShiftById(UUID id) {
        shiftRepoService.deleteShiftById(id);
        return "Shift Deleted Successfully";
    }
}