package com.example.demo.service;

import java.time.ZonedDateTime;
import java.util.UUID;

import com.example.demo.pojos.response.ManagerResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.constants.UserType;
import com.example.demo.entity.Technician;
import com.example.demo.pojos.request.TechnicianRequest;
import com.example.demo.pojos.response.TechnicianResponse;
import com.example.demo.repository.TechnicianRepoService;
import com.example.demo.repository.UserRepoService;
import com.example.demo.security.entity.User;
import com.example.demo.security.utils.JwtTokenService;

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

    public TechnicianResponse createTechnician(String token, TechnicianRequest technicianRequest) {

        // Extracting User from JWT Token
        UUID userId = jwtTokenService.extractClaimsId(token);
        User managedByUser = userRepo.findById(userId);

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

        // TODO : if Technician failed to create then rollaback the user creation
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

        log.info("Technician Created : {}", technicianResponse);

        return technicianResponse;
    }

    /**
     * Finds a Technician by its id.
     * 
     * @param id The id of the Technician.
     * @return The Technician object.
     */
    public TechnicianResponse getTechnicianById(UUID id) {
        TechnicianResponse response = redisCacheService.getTechnicianById(id);

        if (response != null)
            return response;

        Technician Technician = technicianRepoService.findTechnicianById(id);

        TechnicianResponse technicianResponse = TechnicianResponse.builder()
                .id(Technician.getId())
                .firstName(Technician.getTechnicianUser().getFirstName())
                .lastName(Technician.getTechnicianUser().getLastName())
                .phoneNumber(Technician.getTechnicianUser().getPhoneNumber())
                .managedById(Technician.getManagedByUser().getId())
                .build();

        log.info("Technician Found : {}", technicianResponse);
        redisCacheService.saveTechnicianById(id.toString(), technicianResponse);
        log.info("technician Saved to Redis Cache : {}", Technician);
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
        return "Technician Deleted Successfully";
    }
}