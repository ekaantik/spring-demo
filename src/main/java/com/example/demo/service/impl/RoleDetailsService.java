package com.example.demo.service.impl;

import com.example.iot.repository.RoleRepoService;
import com.example.iot.security.entity.Role;
import org.springframework.stereotype.Service;

import com.example.iot.constants.ErrorCode;
import com.example.iot.entity.AssetDetails;
import com.example.iot.entity.CustomerDetails;
import com.example.iot.exception.AlreadyExistsException;
import com.example.iot.exception.NotFoundException;
import com.example.iot.pojos.request.RoleDetailsRequest;
import com.example.iot.pojos.response.RoleDetailsResponse;
import com.example.iot.repository.AssetDetailsRepoService;
import com.example.iot.repository.CustomerDetailsRepoService;
import com.example.iot.security.utils.JwtTokenService;

import lombok.extern.slf4j.Slf4j;

import java.util.UUID;
import java.util.Set;
import java.util.HashSet;

@Service
@Slf4j
public class RoleDetailsService {

    private final RoleRepoService roleRepoService;
    private final AssetDetailsRepoService assetDetailsRepoService;
    private final JwtTokenService jwtTokenService;
    private final CustomerDetailsRepoService customerDetailsRepoService;

    RoleDetailsService(AssetDetailsRepoService assetDetailsRepoService,
            CustomerDetailsRepoService customerDetailsRepoService,
            JwtTokenService jwtTokenService, RoleRepoService roleRepoService) {
        this.assetDetailsRepoService = assetDetailsRepoService;
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

        Set<AssetDetails> assetDetailsSet = new HashSet<>();

        // No Assets (Invalid Request)
        if (roleDetailsRequest.getAssetDetailsId().isEmpty()) {

            // TODO : Handle Assets Detail Id's Null

            log.error("AssetDetailsId cannot be empty");
            throw new RuntimeException("AssetDetailsId cannot be empty");
        }

        for (UUID assetDetailsId : roleDetailsRequest.getAssetDetailsId()) {
            AssetDetails assetDetails = assetDetailsRepoService.findById(assetDetailsId);

            if (assetDetails != null) {
                assetDetailsSet.add(assetDetails);
            }

            else {
                log.warn("AssetDetails with id {} not found", assetDetailsId);
            }
        }

        role = Role.builder()
                .name(roleDetailsRequest.getRoleName())
                .description(roleDetailsRequest.getDescription())
                .customerDetails(customerDetails)
                .assetDetails(assetDetailsSet)
                .build();
        // Save RoleDetails
        Role savedRoleDetails = roleRepoService.save(role);

        // Build RoleDetailsResponse
        RoleDetailsResponse response = RoleDetailsResponse.builder()
                .id(savedRoleDetails.getId())
                .roleName(savedRoleDetails.getName())
                .description(savedRoleDetails.getDescription())
                .assetDetailsId(savedRoleDetails.getAssetDetails().stream().map(AssetDetails::getId).toList())
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

        Set<AssetDetails> assetDetailsSet = new HashSet<>();

        // No Assets (Invalid Request)
        if (roleDetailsRequest.getAssetDetailsId().isEmpty()) {

            // TODO : Handle Assets Detail Id's Null

            log.error("AssetDetailsId cannot be empty");
            throw new RuntimeException("AssetDetailsId cannot be empty");
        }

        for (UUID assetDetailsId : roleDetailsRequest.getAssetDetailsId()) {
            AssetDetails assetDetails = assetDetailsRepoService.findById(assetDetailsId);

            if (assetDetails != null) {
                assetDetailsSet.add(assetDetails);
            }

            else {
                log.warn("AssetDetails with id {} not found", assetDetailsId);
            }
        }

        // Update RoleDetails
        role.setName(roleDetailsRequest.getRoleName());
        role.setDescription(roleDetailsRequest.getDescription());
        role.setAssetDetails(assetDetailsSet);

        Role savedRoleDetails = roleRepoService.save(role);

        // Build RoleDetailsResponse
        RoleDetailsResponse response = RoleDetailsResponse.builder()
                .id(savedRoleDetails.getId())
                .roleName(savedRoleDetails.getName())
                .description(savedRoleDetails.getDescription())
                .assetDetailsId(savedRoleDetails.getAssetDetails().stream().map(AssetDetails::getId).toList())
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
