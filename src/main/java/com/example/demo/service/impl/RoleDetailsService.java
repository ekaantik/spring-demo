package com.example.demo.service.impl;

import com.example.demo.constants.ErrorCode;
import com.example.demo.entity.CustomerDetails;
import com.example.demo.exception.AlreadyExistsException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.pojos.request.RoleDetailsRequest;
import com.example.demo.pojos.response.RoleDetailsResponse;
import com.example.demo.repository.CustomerDetailsRepoService;
import com.example.demo.repository.RoleRepoService;
import com.example.demo.security.entity.Role;
import com.example.demo.security.utils.JwtTokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RoleDetailsService {

    private final RoleRepoService roleRepoService;
    private final JwtTokenService jwtTokenService;
    private final CustomerDetailsRepoService customerDetailsRepoService;

    RoleDetailsService(
            CustomerDetailsRepoService customerDetailsRepoService,
            JwtTokenService jwtTokenService, RoleRepoService roleRepoService) {
        this.customerDetailsRepoService = customerDetailsRepoService;
        this.jwtTokenService = jwtTokenService;
        this.roleRepoService = roleRepoService;
    }

    public RoleDetailsResponse createRoleDetails(String token, RoleDetailsRequest roleDetailsRequest) {

        // Extract CustomerDetails from token
        CustomerDetails customerDetails = customerDetailsRepoService
                .findById(jwtTokenService.extractClaimsCustId(token));

        Role role = roleRepoService
                .findRoleDetailsByRoleNameAndCustomerDetails(roleDetailsRequest.getRoleName(), customerDetails);

        if (role != null) {
            log.error("RoleDetails with roleName {} already exists", roleDetailsRequest.getRoleName());
            throw new AlreadyExistsException(ErrorCode.ALREADY_EXISTS,"RoleDetails", role.getId());
        }


        // No Assets (Invalid Request)
        if (roleDetailsRequest.getAssetDetailsId().isEmpty()) {

            // TODO : Handle Assets Detail Id's Null

            log.error("AssetDetailsId cannot be empty");
            throw new RuntimeException("AssetDetailsId cannot be empty");
        }



        role = Role.builder()
                .name(roleDetailsRequest.getRoleName())
                .description(roleDetailsRequest.getDescription())
                .customerDetails(customerDetails)
                .build();
        // Save RoleDetails
        Role savedRoleDetails = roleRepoService.save(role);

        // Build RoleDetailsResponse
        RoleDetailsResponse response = RoleDetailsResponse.builder()
                .id(savedRoleDetails.getId())
                .roleName(savedRoleDetails.getName())
                .description(savedRoleDetails.getDescription())
                .build();

        log.info("Successfully created RoleDetails with id {}", savedRoleDetails.getId());
        return response;
    }

    public RoleDetailsResponse updateRoleDetails(String token, RoleDetailsRequest roleDetailsRequest) {

        // Extract CustomerDetails from token
        CustomerDetails customerDetails = customerDetailsRepoService
                .findById(jwtTokenService.extractClaimsCustId(token));

        Role role = roleRepoService
                .findRoleDetailsByRoleNameAndCustomerDetails(roleDetailsRequest.getRoleName(), customerDetails);

        if (role == null) {
            log.error("RoleDetails with roleName {} does not exist", roleDetailsRequest.getRoleName());
            throw new NotFoundException(ErrorCode.NOT_EXISTS, roleDetailsRequest.getRoleName(), "RoleDetails");
        }


        // No Assets (Invalid Request)
        if (roleDetailsRequest.getAssetDetailsId().isEmpty()) {

            // TODO : Handle Assets Detail Id's Null

            log.error("AssetDetailsId cannot be empty");
            throw new RuntimeException("AssetDetailsId cannot be empty");
        }



        // Update RoleDetails
        role.setName(roleDetailsRequest.getRoleName());
        role.setDescription(roleDetailsRequest.getDescription());

        Role savedRoleDetails = roleRepoService.save(role);

        // Build RoleDetailsResponse
        RoleDetailsResponse response = RoleDetailsResponse.builder()
                .id(savedRoleDetails.getId())
                .roleName(savedRoleDetails.getName())
                .description(savedRoleDetails.getDescription())
                .build();

        log.info("Successfully updated RoleDetails with id {}", savedRoleDetails.getId());
        return response;
    }

    /**
     * Retrieves RoleDetails based on the provided roleName and token.
     * 
     * @param token    The token containing authentication information.
     * @param roleName The name of the role.
     * @return The RoleDetails corresponding to the provided roleName and
     *         customerDetails, or null if not found.
     */

    public Role getRoleDetails(String token, String roleName) {

        // Extract CustomerDetails from token
        CustomerDetails customerDetails = customerDetailsRepoService
                .findById(jwtTokenService.extractClaimsCustId(token));

        // Find RoleDetails
        Role role = roleRepoService.findRoleDetailsByRoleNameAndCustomerDetails(roleName, customerDetails);

        log.info("Role Details : {}", role);
        log.info("Successfully found RoleDetails with roleName " + roleName);
        return role;
    }
}
