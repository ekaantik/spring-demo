package com.example.demo.service;

import com.example.demo.constants.Constants;
import com.example.demo.constants.ErrorCode;
import com.example.demo.constants.UserType;
import com.example.demo.entity.Manager;
import com.example.demo.exception.NotFoundException;
import com.example.demo.pojos.request.ManagerRequest;
import com.example.demo.pojos.response.ManagerResponse;
import com.example.demo.repository.ManagerRepoService;
import com.example.demo.repository.UserRepoService;
import com.example.demo.security.entity.User;
import com.example.demo.security.utils.JwtTokenService;
import com.example.demo.service.RedisCacheService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
public class ManagerService {

    private final ManagerRepoService managerRepoService;
    private final JwtTokenService jwtTokenService;
    private final UserRepoService userRepo;
    private final PasswordEncoder passwordEncoder;
    private final RedisCacheService redisCacheService;

    /**
     * Creates a new manager user associated with a vendor user and returns the
     * manager response.
     * 
     * @param token          The JWT token containing user information.
     * @param managerRequest The request object containing manager information.
     * @return The response object containing details of the created manager user.
     */
    @Transactional
    public ManagerResponse createManager(String token, ManagerRequest managerRequest) {

        log.info("ManagerService createManager request: {}", managerRequest);
        // Extracting User from JWT Token
        UUID userId = jwtTokenService.extractClaimsId(token);
        User vendorUser = userRepo.findById(userId);

        if (vendorUser == null) {
            log.error("Vendor User Not Found for Id : {}", userId);
            throw new NotFoundException(ErrorCode.NOT_EXISTS, userId, Constants.FIELD_ID, Constants.TABLE_VENDOR);
        }

        // Creating Manager User
        User user = User.builder()
                .firstName(managerRequest.getFirstName())
                .lastName(managerRequest.getLastName())
                .phoneNumber(managerRequest.getPhoneNumber())
                .password(passwordEncoder.encode(managerRequest.getPassword()))
                .userType(UserType.MANAGER)
                .createdAt(ZonedDateTime.now())
                .updatedAt(ZonedDateTime.now())
                .build();

        // Saving Manager User
        User savedUser = userRepo.save(user);

        // Creating Manager
        Manager manager = Manager.builder()
                .vendorUser(vendorUser)
                .managerUser(savedUser)
                .createdAt(ZonedDateTime.now())
                .updatedAt(ZonedDateTime.now())
                .build();

        // Saving Manager
        Manager savedManager = managerRepoService.save(manager);

        // Creating Manager Response
        ManagerResponse managerResponse = ManagerResponse.builder()
                .id(savedManager.getId())
                .firstName(manager.getManagerUser().getFirstName())
                .lastName(manager.getManagerUser().getLastName())
                .phoneNumber(manager.getManagerUser().getPhoneNumber())
                .vendorId(manager.getVendorUser().getId())
                .build();

        log.info("ManagerService createManager Manager Created : {}", managerResponse);

        return managerResponse;
    }

    /**
     * Finds a manager by its id.
     * 
     * @param id The id of the manager.
     * @return The manager object.
     */
    public ManagerResponse getManagerById(UUID id) {
        log.info("ManagerService getManagerById requested ID: {}", id);
        ManagerResponse response = redisCacheService.getManagerById(id);

        if (response != null) {
            log.info("ManagerService getManagerById getting response from redis cache: {}", response);
            return response;
        }

        Manager manager = managerRepoService.findManagerById(id);

        if (manager == null) {
            log.error("Manager Not Found for Id : {}", id);
            throw new NotFoundException(ErrorCode.NOT_EXISTS, id, Constants.FIELD_ID, Constants.TABLE_MANAGER);
        }

        ManagerResponse managerResponse = ManagerResponse.builder()
                .id(manager.getId())
                .firstName(manager.getManagerUser().getFirstName())
                .lastName(manager.getManagerUser().getLastName())
                .phoneNumber(manager.getManagerUser().getPhoneNumber())
                .vendorId(manager.getVendorUser().getId())
                .build();

        log.info("ManagerService getManagerById received managerResponse : {}", managerResponse);
        redisCacheService.saveManagerById(id.toString(), managerResponse);
        log.info("ManagerService getManagerById manager Saved to Redis Cache : {}", manager);
        return managerResponse;
    }

    /**
     * Deletes a manager by its id.
     * 
     * @param id The id of the manager.
     * @return The message indicating the status of the deletion.
     */
    public String deleteManagerById(UUID id) {
        log.info("ManagerService deleteManagerById requested ID: {}", id);
        redisCacheService.clearManagerById(id.toString());
        managerRepoService.deleteManagerById(id);
        log.info("ManagerService deleteManagerById Manager deleted successfully.");
        return "Manager Deleted Successfully";
    }

}