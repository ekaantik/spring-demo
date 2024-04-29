package com.example.demo.service.impl;

import com.example.demo.pojos.request.UserDetailsRequest;
import com.example.demo.pojos.response.UserDetailsResponse;
import com.example.demo.repository.*;
import com.example.demo.security.entity.User;
import com.example.demo.security.utils.JwtTokenService;
import com.example.demo.utils.MathUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserDetailService {

    private final UserRepoService userRepoService;
    private final RoleRepo roleRepo;
    private final RoleRepoService roleRepoService;
    private final CustomerDetailsRepoService customerDetailsRepoService;
    private final UserRoleRepoService userRoleRepoService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenService jwtTokenService;

    /**
     * Creates a user invitation for a specific customer.
     *
     * @param req   The request object containing user details required for
     *              invitation.
     * @param token The authentication token used to identify the customer.
     * @return A UserDetailsResponse indicating the success of the user invitation.
     */
    public UserDetailsResponse createUserInvite(UserDetailsRequest req, String token) {

        // Generate a random password
        String tempPassword = MathUtil.getRandomNumberString();
        // ToDo : Prepare and sent an email based on the response set the status

        // Extracting Customer's Id
        UUID customerId = jwtTokenService.extractClaimsCustId(token);
        UUID userId = jwtTokenService.extractClaimsId(token);
        User customerUserDetails = userRepoService.findById(userId);

        // Fetching CustomerDetails
        CustomerDetails customerDetails = customerDetailsRepoService.findById(customerId);
        log.info("Fetched customer details : {}", customerDetails);

        // Initialize Set
//        Set<Role> roles = new HashSet<>();
//        roles.addAll(roleRepoService.findByCustomerDetailsId(customerId));

        //Always return 1 list with role User
        List<Role> userRoleDetails = roleRepo.findByCustomerDetailsId(customerId)
                .stream()
                .filter(x -> x.getName().equals(UserRolesDefault.USER.getUserRoles()))
                .toList();
//        userRoles.setUser();Role();
//
//        // Add Role
//        roles.add(roleUser);

        // Build User
        User user = User.builder()
                .firstName(req.getFirstName())
                .lastName(req.getLastName())
                .email(req.getEmail())
                .userStatus(req.getUserStatus())
                .contactNumber(req.getContactNumber())
                .password(passwordEncoder.encode(tempPassword))
                .customerDetails(customerDetails)
                .createdAt(ZonedDateTime.now())
                .createdBy(customerUserDetails)
                .lastModifiedAt(ZonedDateTime.now())
                .lastModifiedBy(customerUserDetails)
                .build();
        log.info("Built user : {}", user);

        // Save User
        user = userRepoService.save(user);
        log.info("User Saved");
        UserRoles userRolesToSave = UserRoles.builder()
                .user(user)
                .role(userRoleDetails.get(0)).build();
        userRoleRepoService.save(userRolesToSave);

        // Build Response
        UserDetailsResponse userDetailsResponse = UserDetailsResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .userStatus(user.getUserStatus())
                .contactNumber(user.getContactNumber())
                .build();
        log.info("User invited successfully Built response : {}", userDetailsResponse);
        return userDetailsResponse;
    }

    public UserDetailsResponse updateUser(UserDetailsRequest req, UUID userId) {
        User userDetails = userRepoService.findById(userId);
//        userDetails.setId(userDetails.getId());
        userDetails.setFirstName(req.getFirstName());
        userDetails.setLastName(req.getLastName());
        userDetails.setEmail(req.getEmail());
        userDetails.setContactNumber(req.getContactNumber());
        userDetails.setUserStatus(req.getUserStatus());

        User user = userRepoService.save(userDetails);

        // Build Response
        UserDetailsResponse userDetailsResponse = UserDetailsResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .userStatus(user.getUserStatus())
                .contactNumber(user.getContactNumber())
                .build();
        log.info("Built response : {}", userDetailsResponse);
        log.info("User invited successfully");

        return userDetailsResponse;
    }

    // TODO : Remove unused function
    // public UserProfileResponse createCustomerUserDetail(UserProfileRequest
    // userProfileRequest) {
    //
    // User user = new User();
    //
    // user.setFirstName(userProfileRequest.getFirstName());
    // user.setLastName(userProfileRequest.getLastName());
    // user.setEmail(userProfileRequest.getEmail());
    // user.setPassword(userProfileRequest.getPassword());
    // user.setUserStatus(userProfileRequest.getUserStatus());
    // user.setContactNumber(userProfileRequest.getContactNumber());
    //
    // //Associate Customer Detail
    // CustomerDetails customerDetails =
    // customerDetailsRepoService.findById(userProfileRequest.getId());
    // user.setCustomerDetails(customerDetails);
    //
    // userRepoService.save(user);
    //
    // return new UserProfileResponse(user.getId(), user.getFirstName(),
    // user.getLastName(), user.getEmail(),
    // user.getUserStatus(),
    // user.getContactNumber());
    // }

    /**
     * Retrieves the user profile details based on the provided authentication
     * token.
     *
     * @param token The authentication token used to identify and retrieve user
     *              profile details.
     * @return A UserDetailsResponse containing the user's profile details.
     */
    public UserDetailsResponse getUserProfile(String token) {

        // Fetch CustomerDetails
        UUID customerId = jwtTokenService.extractClaimsCustId(token);
        CustomerDetails customerDetails = customerDetailsRepoService.findById(customerId);
        log.info("Fetched customer details : {}", customerDetails);

        // Fetch UserDetails
        UUID userId = jwtTokenService.extractClaimsId(token);
        User userDetails = userRepoService.findById(userId);
        log.info("Fetched user details : {}", userDetails);

        // Building Response
        UserDetailsResponse response = UserDetailsResponse.builder()
                .id(userDetails.getId())
                .firstName(userDetails.getFirstName())
                .lastName(userDetails.getLastName())
                .email(userDetails.getEmail())
                .userStatus(userDetails.getUserStatus())
                .contactNumber(userDetails.getContactNumber())
                .customerName(customerDetails.getName())
                .customerAddress(customerDetails.getAddress())
                .build();
        log.info("Built response : {}", response);
        log.info("User profile fetched successfully");
        return response;
    }

    /**
     * Get all user associated with a customer from the token.
     *
     * @param token the JWT token
     * @return a list of UserDetailsResponse
     */
    public List<UserDetailsResponse> getAllUsersProfile(String token) {

        // Extracting Customer Id
        UUID customerDetailsId = jwtTokenService.extractClaimsCustId(token);
        log.info("Fetched customer detail's Id : {}", customerDetailsId);

        // Getting All Users associated with the Customer
        CustomerDetails customerDetails = customerDetailsRepoService.findById(customerDetailsId);
        List<User> users = userRepoService.findByCustomerDetails(customerDetails);
        log.info("Fetched users : {}", users);

        // Building Response
        List<UserDetailsResponse> userDetailsResponseList = new ArrayList<>();
        for (User user : users) {
            UserDetailsResponse response = UserDetailsResponse.builder()
                    .id(user.getId())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .email(user.getEmail())
                    .userStatus(user.getUserStatus())
                    .contactNumber(user.getContactNumber())
                    .customerName(customerDetails.getName())
                    .customerAddress(customerDetails.getAddress())
                    .build();
            userDetailsResponseList.add(response);
        }

        log.info("Built response : {}", userDetailsResponseList);
        log.info("All users profile fetched successfully");

        return userDetailsResponseList;
    }

    /**
     * Deletes user detail record by its Id.
     *
     * @param id : Id of the user detail record.
     * @return true if the record deleted successfully,else false.
     */
    @Transactional
    public boolean deleteUserDetailsById(UUID id) {
        try {
            //Delete role_asset_mapping which is in Role.java

            // As of now only default roles are there so no need to delete
            // roleRepo.deleteRoleById(id);

            // delete user_roles
//            userRoleRepoService.findById()
            // delete user_details
            // Try deleting user
            User user = userRepoService.findById(id);
            userRepoService.delete(user);
            log.info("User deleted successfully");
            return true;
        } catch (Exception ex) {
            log.error("Error deleting user : {}", ex.getMessage());
            return false;
        }
    }

    // TODO : Remove unused functions
    // public List<UserAuthResponse> getAllCustomerUserDetails() {
    // List<UserAuthResponse> userAuthRespons = new ArrayList<>();
    // List<UserDetails> userDetails = customerUserDetailRepository.findAll();
    //
    // for (UserDetails customerUserDetail : userDetails) {
    // userAuthRespons.add(new UserAuthResponse(customerUserDetail.getId(),
    // customerUserDetail.getFirstName(),
    // customerUserDetail.getLastName(),
    // customerUserDetail.getEmail(),
    // customerUserDetail.getPassword(),
    // customerUserDetail.getStatus(),
    // customerUserDetail.getContactNumber(),
    // ""));
    // }
    // return userAuthRespons;
    // }

    // public UserAuthResponse getCustomerUserDetailById(UUID id) {
    //
    // UserDetails userDetails =
    // customerUserDetailRepository.findCustomerUserDetailById(id);
    // return new UserAuthResponse(userDetails.getId(), userDetails.getFirstName(),
    // userDetails.getLastName(), userDetails.getEmail(),
    // userDetails.getPassword(), userDetails.getStatus(),
    // userDetails.getContactNumber(),
    // "");
    // }

    // @Transactional
    // public boolean deleteCustomerUserDetailById(UUID id) {
    // try {
    // userDetailRepository.deleteCustomerUserDetailById(id);
    // return true;
    // } catch (Exception ex) {
    // return false;
    // }
    // }
}
