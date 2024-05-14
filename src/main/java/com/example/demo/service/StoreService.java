package com.example.demo.service;

import java.time.ZonedDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Store;
import com.example.demo.pojos.request.StoreRequest;
import com.example.demo.pojos.response.StoreResponse;
import com.example.demo.repository.StoreRepoService;
import com.example.demo.repository.UserRepoService;
import com.example.demo.security.entity.User;
import com.example.demo.security.utils.JwtTokenService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class StoreService {

    private final StoreRepoService storeRepoService;
    private final JwtTokenService jwtTokenService;
    private final UserRepoService userRepo;

    public StoreResponse createStore(String token, StoreRequest storeRequest) {

        // Extracting User from JWT Token
        UUID userId = jwtTokenService.extractClaimsId(token);
        User vendorUser = userRepo.findById(userId);

        // Creating Store
        Store store = Store.builder()
                .name(storeRequest.getName())
                .address(storeRequest.getAddress())
                .serviceType(storeRequest.getServiceType())
                .vendorUser(vendorUser)
                .createdAt(ZonedDateTime.now())
                .updatedAt(ZonedDateTime.now())
                .build();

        // Saving Store
        Store savedStore = storeRepoService.save(store);

        // Creating Store Response
        StoreResponse storeResponse = StoreResponse.builder()
                .id(savedStore.getId())
                .name(savedStore.getName())
                .address(savedStore.getAddress())
                .serviceType(savedStore.getServiceType().toString())
                .vendorUserId(savedStore.getVendorUser().getId())
                .build();

        log.info("Store Created : {}", storeResponse);

        return storeResponse;
    }

    /**
     * Finds a Store by its id.
     * 
     * @param id The id of the Store.
     * @return The Store object.
     */
    public StoreResponse getStoreById(UUID id) {
        Store store = storeRepoService.findStoreById(id);

        StoreResponse storeResponse = StoreResponse.builder()
                .id(store.getId())
                .name(store.getName())
                .address(store.getAddress())
                .serviceType(store.getServiceType().toString())
                .vendorUserId(store.getVendorUser().getId())
                .build();

        log.info("Store Found : {}", storeResponse);
        return storeResponse;
    }

    /**
     * Deletes a Store by its id.
     * 
     * @param id The id of the Store.
     * @return The message indicating the status of the deletion.
     */
    public String deleteStoreById(UUID id) {
        storeRepoService.deleteStoreById(id);
        return "Store Deleted Successfully";
    }
}