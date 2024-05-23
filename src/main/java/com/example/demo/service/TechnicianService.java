package com.example.demo.service;

import java.time.ZonedDateTime;
import java.util.UUID;

import com.example.demo.pojos.response.ManagerResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.constants.Constants;
import com.example.demo.constants.ErrorCode;
import com.example.demo.constants.UserType;
import com.example.demo.entity.Technician;
import com.example.demo.exception.NotFoundException;
import com.example.demo.pojos.request.TechnicianRequest;
import com.example.demo.pojos.response.TechnicianResponse;
import com.example.demo.repository.TechnicianRepoService;
import com.example.demo.repository.UserRepoService;
import com.example.demo.security.entity.User;
import com.example.demo.security.utils.JwtTokenService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class TechnicianService {

    private final TechnicianRepoService technicianRepoService;
    private final JwtTokenService jwtTokenService;
    private final UserRepoService userRepo;
    private final PasswordEncoder passwordEncoder;
    private final RedisCacheService redisCacheService;

    /**
     * Creates a new technician user associated with a manager user and returns the
     * technician response.
     * 
     * @param token             The JWT token containing user information.
     * @param technicianRequest The request object containing technician
     *                          information.
     * @return The response object containing details of the created technician
     *         user.
     */
    @Transactional
    public TechnicianResponse createTechnician(String token, TechnicianRequest technicianRequest) {
        log.info("TechnicianService createTechnician request: {}", technicianRequest);
        // Extracting User from JWT Token
        UUID userId = jwtTokenService.extractClaimsId(token);
        User managedByUser = userRepo.findById(userId);

        // Managed User Not Found
        if (managedByUser == null) {
            log.error("Managed By User Not Found with Id : {}", userId);
            throw new NotFoundException(ErrorCode.NOT_EXISTS, userId, Constants.FIELD_ID,
                    Constants.TABLE_VENDOR + "/" + Constants.TABLE_MANAGER);
        }

        // Creating Technician User
        User user = User.builder()
                .firstName(technicianRequest.getFirstName())
                .lastName(technicianRequest.getLastName())
                .phoneNumber(technicianRequest.getPhoneNumber())
                .password(passwordEncoder.encode(technicianRequest.getPassword()))
                .userType(UserType.TECHNICIAN)
                .createdAt(ZonedDateTime.now())
                .updatedAt(ZonedDateTime.now())
                .build();

        // Saving Technician User
        User savedUser = userRepo.save(user);

        // Creating Technician
        Technician technician = Technician.builder()
                .managedByUser(managedByUser)
                .technicianUser(savedUser)
                .createdAt(ZonedDateTime.now())
                .updatedAt(ZonedDateTime.now())
                .build();

        // Saving Technician
        Technician savedTechnician = technicianRepoService.save(technician);

        // Creating Technician Response
        TechnicianResponse technicianResponse = TechnicianResponse.builder()
                .id(savedTechnician.getId())
                .firstName(technician.getTechnicianUser().getFirstName())
                .lastName(technician.getTechnicianUser().getLastName())
                .phoneNumber(technician.getTechnicianUser().getPhoneNumber())
                .managedById(technician.getManagedByUser().getId())
                .build();

        log.info("TechnicianService createTechnician Created : {}", technicianResponse);
        return technicianResponse;
    }

    /**
     * Finds a Technician by its id.
     * 
     * @param id The id of the Technician.
     * @return The Technician object.
     */
    public TechnicianResponse getTechnicianById(UUID id) {
        log.info("TechnicianService getTechnicianById requested Id : {}", id);
        TechnicianResponse response = redisCacheService.getTechnicianById(id);

        if (response != null){
            log.info("TechnicianService getTechnicianById getting response from redis cache: {}", response);
            return response;
        }
        Technician Technician = technicianRepoService.findTechnicianById(id);

        if (Technician == null) {
            log.error("Technician Not Found for Id : {}", id);
            throw new NotFoundException(ErrorCode.NOT_EXISTS, id, Constants.FIELD_ID, Constants.TABLE_TECHNICIAN);
        }

        TechnicianResponse technicianResponse = TechnicianResponse.builder()
                .id(Technician.getId())
                .firstName(Technician.getTechnicianUser().getFirstName())
                .lastName(Technician.getTechnicianUser().getLastName())
                .phoneNumber(Technician.getTechnicianUser().getPhoneNumber())
                .managedById(Technician.getManagedByUser().getId())
                .build();

        log.info("TechnicianService getTechnicianById received Technician response : {}", technicianResponse);
        redisCacheService.saveTechnicianById(id.toString(), technicianResponse);
        log.info("TechnicianService getTechnicianById technician Saved to Redis Cache : {}", Technician);
        return technicianResponse;
    }

    /**
     * Deletes a Technician by its id.
     * 
     * @param id The id of the Technician.
     * @return The message indicating the status of the deletion.
     */
    public String deleteTechnicianById(UUID id) {
        technicianRepoService.deleteTechnicianById(id);
        log.info("TechnicianService deleteTechnicianById id {} deleted successfully", id);
        return "Technician Deleted Successfully";
    }
}