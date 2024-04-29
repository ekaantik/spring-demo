package com.example.demo.service.impl;

import com.example.iot.constants.CustomerInviteStatus;
import com.example.iot.constants.CustomerServiceStatus;
import com.example.iot.constants.UserRolesDefault;
import com.example.iot.entity.CustomerDetails;
import com.example.iot.entity.UserRoles;
import com.example.iot.pojos.request.CustomerDetailRequest;
import com.example.iot.pojos.response.CustomerDetailResponse;
import com.example.iot.repository.*;
import com.example.iot.security.entity.Role;
import com.example.iot.security.entity.User;
import com.example.iot.security.utils.JwtTokenService;
import com.example.iot.utils.MathUtil;
import com.example.iot.utils.UserStatus;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomerDetailService {

    private final UserRepoService userRepoService;
    private final RoleRepoService roleRepoService;
    private final CustomerDetailsRepoService customerDetailsRepoService;
    private final PasswordEncoder passwordEncoder;
    private final AssetDetailsRepoService assetDetailsRepoService;
    private final JwtTokenService jwtTokenService;
    private final UserRoleRepoService userRoleRepoService;

    /**
     * Creates a customer invite and performs necessary actions for user and role
     * creation.
     *
     * @param req The request object containing details required for creating a
     *            customer invite.
     * @return A CustomerDetailResponse indicating the status of the invite
     *         creation.
     */
    @Transactional
    public CustomerDetailResponse createCustomerInvite(String token, CustomerDetailRequest req) {
        // Generate a random password
        String tempPassword = MathUtil.getRandomNumberString();

        //ToDo why this ? as only super admin can create customer
        User creatorUserDetails = userRepoService.findById(jwtTokenService.extractClaimsId(token));

        // Build CustomerDetails
        CustomerDetails customerDetails = CustomerDetails.builder()
                .name(req.getName())
                .legalName(req.getLegalName())
                .adminEmail(MathUtil.getRandomNumberString())
                .companyEmail(req.getCompanyEmail())
                .contactNumber(req.getContactNumber())
                .adminEmail(req.getAdminEmail())
                .address(req.getAddress())
                .customerId(MathUtil.getRandomNumberString())
                .customerInviteStatus(CustomerInviteStatus.INVITE_SENT)
                .customerServiceStatus(CustomerServiceStatus.INACTIVE)
                .createdAt(ZonedDateTime.now())
                .createdBy(creatorUserDetails)
                .lastModifiedAt(ZonedDateTime.now())
                .build();
        log.info("Built customer details: {}", customerDetails);

        // Save CustomerDetails
        CustomerDetails savedCustomerDetails = customerDetailsRepoService.save(customerDetails);
        log.info("Saved customer details : {}", savedCustomerDetails);

        // create  Role ADMIN & USER
        Role roleCustomerAdmin = Role.builder()
                .name(UserRolesDefault.CUSTOMER_ADMIN.getUserRoles())
                .customerDetails(savedCustomerDetails)
                .description("Hardcoded Description").build();
        Role roleUser = Role.builder()
                .name(UserRolesDefault.USER.getUserRoles())
                .customerDetails(customerDetails)
                .description("Hardcoded Description").build();

        // Save Roles
        Role savedAdminRole = roleRepoService.save(roleCustomerAdmin);
        Role savedUserRole =  roleRepoService.save(roleUser);

//        // Add Roles to Set
//        Set<Role> roles = new HashSet<>();
//        roles.add(roleCustomerAdmin);
//        roles.add(roleUser);

        // Build User
        User user = User.builder()
                .firstName(req.getFirstName())
                .lastName(req.getLastName())
                .email(customerDetails.getAdminEmail())
                .userStatus(UserStatus.DISABLED)
                .contactNumber(req.getContactNumber())
                .password(passwordEncoder.encode(tempPassword))
                .customerDetails(customerDetails)
                .createdAt(ZonedDateTime.now())
                .createdBy(creatorUserDetails)
                .lastModifiedAt(ZonedDateTime.now())
                .lastModifiedBy(creatorUserDetails)
                .lastModifiedAt(ZonedDateTime.now())
                .build();
        log.info("Built user: {}", user);

        // Save User
        User savedUserResponse = userRepoService.save(user);
        log.info("Saved user : {}", savedUserResponse);

        //update roles
        UserRoles userRoles = UserRoles.builder()
                .user(savedUserResponse)
                .role(savedUserRole).build();
        UserRoles adminRoles = UserRoles.builder()
                .user(savedUserResponse)
                .role(savedAdminRole).build();
        userRoleRepoService.save(userRoles);
        userRoleRepoService.save(adminRoles);

        // ToDo : Prepare and sent an email based on the response set the status

        // Building Response
        CustomerDetailResponse response = CustomerDetailResponse.builder()
                .id(savedCustomerDetails.getId())
                .name(savedCustomerDetails.getName())
                .legalName(savedCustomerDetails.getLegalName())
                .adminEmail(savedCustomerDetails.getAdminEmail())
                .contactNumber(savedCustomerDetails.getContactNumber())
                .companyEmail(savedCustomerDetails.getCompanyEmail())
                .customerId(savedCustomerDetails.getCustomerId())
                .address(savedCustomerDetails.getAddress())
                .customerInviteStatus(savedCustomerDetails.getCustomerInviteStatus())
                .customerServiceStatus(savedCustomerDetails.getCustomerServiceStatus())
                .responseMessage("Invite Sent to Customer email")
                .build();
        log.info("Built customer detail response: {}", response);
        return response;
    }

    /**
     * Retrieves an Customer Details record by its Id.
     *
     * @param id : Id associated with the record.
     * @return CustomerDetailResponse representing the record.
     */
    public CustomerDetailResponse getCustomerDetailById(UUID id) {

        // Get Customer Details from Database
        CustomerDetails customerDetails = customerDetailsRepoService.findById(id);
        log.info("Fetched customer details: {}", customerDetails);
        CustomerDetailResponse customerDetailResponse = null;
        if (Objects.nonNull(customerDetails)){
            // Building Response
            customerDetailResponse = CustomerDetailResponse.builder()
                    .id(customerDetails.getId())
                    .name(customerDetails.getName())
                    .legalName(customerDetails.getLegalName())
                    .customerId(customerDetails.getCustomerId())
                    .companyEmail(customerDetails.getCompanyEmail())
                    .contactNumber(customerDetails.getContactNumber())
                    .adminEmail(customerDetails.getAdminEmail())
                    .address(customerDetails.getAddress())
                    .customerInviteStatus(customerDetails.getCustomerInviteStatus())
                    .customerServiceStatus(customerDetails.getCustomerServiceStatus())
                    .responseMessage("SUCCESS")
                    .build();
        }

        log.info("Built customer detail response: {}", customerDetailResponse);

        log.info("Customer detail fetched successfully");

        return customerDetailResponse;
    }

    /**
     * Retrieves an Customer Details record by its Id.
     *
     * @param id : Id associated with the record.
     * @return CustomerDetailResponse representing the record.
     */
    public CustomerDetailResponse getCustomerDetailByCustomerId(String customerId) {

        // Get Customer Details from Database
        CustomerDetails customerDetails = customerDetailsRepoService.findByCustomerId(customerId);
        log.info("Fetched customer details: {}", customerDetails);

        // Building Response
        CustomerDetailResponse customerDetailResponse = CustomerDetailResponse.builder()
                .id(customerDetails.getId())
                .name(customerDetails.getName())
                .legalName(customerDetails.getLegalName())
                .customerId(customerDetails.getCustomerId())
                .companyEmail(customerDetails.getCompanyEmail())
                .contactNumber(customerDetails.getContactNumber())
                .adminEmail(customerDetails.getAdminEmail())
                .address(customerDetails.getAddress())
                .customerInviteStatus(customerDetails.getCustomerInviteStatus())
                .customerServiceStatus(customerDetails.getCustomerServiceStatus())
                .responseMessage("SUCCESS")
                .build();
        log.info("Built customer detail response: {}", customerDetailResponse);

        log.info("Customer detail fetched successfully");

        return customerDetailResponse;
    }

    /**
     * Retrieves an Customer Details record by JWT token.
     *
     * @param token : JWT Token.
     * @return CustomerDetailResponse representing the record.
     */
    public CustomerDetailResponse getCustomerDetailsByToken(String token) {

        // Fetch CustomerDetailsId
        UUID customerDetailsId = jwtTokenService.extractClaimsCustId(token);
        log.info("Extracted customer details ID from token: {}", customerDetailsId);

        // Get CustomerDetails
        CustomerDetailResponse customerDetailResponse = getCustomerDetailById(customerDetailsId);
        log.info("Fetched customer detail response: {}", customerDetailResponse);

        log.info("Customer details fetched successfully by token");

        return customerDetailResponse;
    }

    /**
     * Retrieves all customer details and prepares a tabular response.
     *
     * @return A JSON response containing details of all customers in a tabular
     *         format.
     */
    public List<CustomerDetailResponse> getAllCustomerDetails() {
        log.info("Getting all customer details");

        // Fetch All CustomerDetails
        List<CustomerDetails> customerDetailsList = customerDetailsRepoService.findAll();
        log.info("Fetched all customer details: {}", customerDetailsList);

        // Initialize Response List
        List<CustomerDetailResponse> customerDetailResponseList = new ArrayList<>();

        // All CustomerDetails
        for (CustomerDetails it : customerDetailsList) {

            CustomerDetailResponse response = CustomerDetailResponse.builder()
                    .id(it.getId())
                    .name(it.getName())
                    .legalName(it.getLegalName())
                    .customerId(it.getCustomerId())
                    .companyEmail(it.getCompanyEmail())
                    .contactNumber(it.getContactNumber())
                    .adminEmail(it.getAdminEmail())
                    .address(it.getAddress())
                    .customerInviteStatus(it.getCustomerInviteStatus())
                    .customerServiceStatus(it.getCustomerServiceStatus())
                    .responseMessage("Customer Details")
                    .build();
            // Add to List
            customerDetailResponseList.add(response);
            log.info("Added customer detail response to list: {}", response);
        }
        return customerDetailResponseList;
    }

    /**
     * Deletes CustomerDetails record by its Id.
     *
     * @param id Id of the record.
     * @return true if the record deleted successfully,else false.
     */
    @Transactional
    public boolean deleteCustomerDetailsById(UUID id) {

        try {

            // Try deleting customerDetails
            customerDetailsRepoService.deleteById(id);
            log.info("Customer details deleted successfully");
            return true;
        } catch (Exception ex) {
            log.error("Failed to delete customer details: {}", ex.getMessage());
            return false;
        }
    }

    /**
     * Deletes Customer's Users record by its Id.
     *
     * @param id Id of the Customer.
     * @return true if the users deleted successfully,else false.
     */
    @Transactional
    public boolean deleteCustomerById(UUID id) {
        try {
            // Fetch CustomerDetails
            CustomerDetails customerDetails = customerDetailsRepoService.findById(id);
            log.info("Fetched customer details: {}", customerDetails);

            // Try deleting customer's users
            userRepoService.deleteUserDetailsById(customerDetails);
            log.info("Customer's users deleted successfully");

            // Deactivate Customer
            customerDetails.setCustomerServiceStatus(CustomerServiceStatus.INACTIVE);
            log.info("Customer deactivated");

            // Try deleting customer's all assets
            assetDetailsRepoService.deleteAll(customerDetails.getAssetDetailsList());
            customerDetails.setAssetDetailsList(null);
            log.info("Customer's all assets deleted");

            // Save CustomerDetails
            customerDetailsRepoService.save(customerDetails);
            log.info("Customer details saved");

            return true;
        } catch (Exception ex) {
            log.error("Failed to delete customer user: {}", ex.getMessage());
            return false;
        }
    }
}
