package com.example.demo.security.service;

import com.example.iot.constants.CustomerInviteStatus;
import com.example.iot.constants.UserRolesDefault;
import com.example.iot.entity.CustomerDetails;
import com.example.iot.exception.InvalidCredentialException;
import com.example.iot.pojos.response.CustomerDetailResponse;
import com.example.iot.repository.CustomerDetailsRepoService;
import com.example.iot.repository.RoleRepoService;
import com.example.iot.repository.UserRepoService;
import com.example.iot.security.dto.UserAuthRequest;
import com.example.iot.security.dto.UserAuthResponse;
import com.example.iot.security.dto.UserResetPasswordRequest;
import com.example.iot.security.dto.UserSignUpRequest;
import com.example.iot.security.entity.User;
import com.example.iot.security.utils.JwtTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServicesImpl implements AuthServicesIf {

    private final AuthenticationManager authenticationManager;
    @Autowired
    private UserRepoService userDetailsRepoService;
    @Autowired
    private RoleRepoService roleRepoService;
    @Autowired
    private CustomerDetailsRepoService customerDetailsRepoService;
    @Autowired
    private JwtTokenService jwtTokenService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Performs Signup & assigns JWT Token.
     *
     * @param req : Incoming user auth request.
     * @return {@link com.example.iot.security.dto.UserAuthResponse AuthResponse}
     * if succesfully signed-up,else error.
     */
    public UserAuthResponse performSignUp(UserSignUpRequest req) {

        log.info("Incoming UserSignUpRequest : "+req);

        CustomerDetails customerDetails = customerDetailsRepoService.findByAdminEmail(req.getEmail());

        log.info("Customer Details with email " + req.getEmail() + " " + customerDetails);
//            if (customer.getCustomerInviteStatus().equals(CustomerInviteStatus.INVITE_SENT) && customer.getInviteCode().equals(req.getInviteCode())) {
        if (req.getEmail() != null && req.getPassword() != null) {
            customerDetails.setCustomerInviteStatus(CustomerInviteStatus.INVITE_ACCEPTED);

            User user = userDetailsRepoService.findByEmail(req.getEmail());
            user.setFirstName(req.getFirstName());
            user.setLastName(req.getLastName());
            user.setContactNumber(req.getContactNumber());
            User userSavedDetails = userDetailsRepoService.save(user);
            String jwtToken = jwtTokenService.generateToken(user);
            UserAuthResponse response = UserAuthResponse.builder().
                    email(userSavedDetails.getEmail()).
                    jwtToken(jwtToken).
                    userStatus(userSavedDetails.getUserStatus()).build();
            log.info("performSignUp Response : {}" , response);
            return response;
        } else {
            return null;
        }
    }

    /**
     * Perform user login and generate a JWT token.
     *
     * @param req : Incoming user auth request.
     * @return The user authentication response containing the JWT token and user information.
     */
    public UserAuthResponse performLogin(UserAuthRequest req) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword()));

        User user = userDetailsRepoService.findByEmail(req.getEmail());
        String jwtToken = jwtTokenService.generateToken(user);
        UserAuthResponse response = UserAuthResponse.builder()
                .email(user.getEmail())
                .jwtToken(jwtToken)
                .userStatus(user.getUserStatus())
                .build();
        return response;
    }

    /**
     * Authenticate a UserAuthRequest.
     *
     * @param req : Incoming user auth request.
     * @return The user associated with the email in request.
     */
    public User authenticateLoginRequest(UserAuthRequest req) {
        //Email Authentication
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword()));
        // Get user if it exists, otherwise throw an exception
        User user = userDetailsRepoService.findByEmail(req.getEmail());
//                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));
//        System.out.println("User : " + user);
        log.info("authenticateLoginRequest :: User :: {}",user);
        return user;
    }

    /**
     * @param user : User associated with the request.
     * @return UserAuthResponse that contains details about user.
     */
    public UserAuthResponse buildLoginResponse(User user) {
        String jwtToken = jwtTokenService.generateToken(user);
        return UserAuthResponse.builder()
                .email(user.getEmail())
                .jwtToken(jwtToken)
                .userStatus(user.getUserStatus())
                .build();
    }

    public UserAuthResponse buildCustomerLoginResponse(User user) {
        String jwtToken = jwtTokenService.generateToken(user);
        CustomerDetails details = user.getCustomerDetails();
        CustomerDetailResponse customerDetailsResponse  = CustomerDetailResponse.builder()
                .customerId(details.getCustomerId())
                .name(details.getName())
                .legalName(details.getLegalName())
                .build();
        return UserAuthResponse.builder()
                .email(user.getEmail())
                .jwtToken(jwtToken)
                .userStatus(user.getUserStatus())
                .customerDetailResponse(customerDetailsResponse)
                .build();
    }

    /**
     * Checks if the user has the specified role.
     *
     * @param user : user associated with the request.
     * @param role The role to check.
     * @return True if the user has the specified role, else False.
     */
    public boolean hasRole(User user, UserRolesDefault role) {
        log.info("hasRole : {}   userRoleList  {} ", role, user.getUserRolesList());
        return user.getUserRolesList().stream()
                .anyMatch(_role -> _role.getRole().getName()
                        .equalsIgnoreCase(role.toString()));
//        return user.getRoles().stream()
//                .anyMatch(_role -> _role.getName().equalsIgnoreCase(role.toString()));
    }

    /**
     * Authenticates the user login request for Web Admin role.
     *
     * @param req : The user authentication request.
     * @return UserAuthResponse that contains details upon successful authentication.
     * @throws InvalidCredentialException If the user does not have the Web Admin role.
     */
    public UserAuthResponse performWebAdminLogin(UserAuthRequest req) {
        //Email Authentication
        User user = authenticateLoginRequest(req);
        //Validating Role
        if (Objects.isNull(user) || !hasRole(user, UserRolesDefault.WEB_ADMIN)) {
            throw new InvalidCredentialException("User does not have Web Admin Role.");
        }
        return buildLoginResponse(user);
    }

    /**
     * Authenticates the user login request for Customer Admin role.
     *
     * @param req The user authentication request.
     * @return UserAuthResponse that contains details upon successful authentication.
     * @throws InvalidCredentialException If the user does not have the Customer Admin role.
     */
    public UserAuthResponse performCustomerLogin(UserAuthRequest req) {

        //Email Authentication
        User user = authenticateLoginRequest(req);

        //Validating Role
        if (Objects.isNull(user) || hasRole(user, UserRolesDefault.WEB_ADMIN)) {
            throw new InvalidCredentialException("User does not have Customer Admin Role.");
        }

        //Build Response & Return
        return buildCustomerLoginResponse(user);
    }

//        if (userDetails.isPresent()) {
//            User user = userDetails.get();
//            //ToDo : generate jwt token and return
//            String jwtToken = jwtTokenService.generateToken(user);
//
//
//            UserAuthResponse response = UserAuthResponse.builder().email(user.getEmail()).jwtToken(jwtToken).userStatus(user.getUserStatus()).build();
//            return response;
//        } else {
//                //ToDo : Invalid invite code or user already registered
////            throw Exception user not found email or password is wrong
//            }
//        return null;
//        }

    /**
     * Reset a user's password.
     *
     * @param req The user reset password request, including email, current password, and new password.
     * @throws IllegalArgumentException if the email or current password is invalid.
     */
    public void resetPassword(String token, UserResetPasswordRequest req) {

        // Authenticate the user with their current credentials
        String email = (String) jwtTokenService.extractAllClaims(token).get("userEmail");


        // Retrieve the user based on their email if found
        //else throw an exception
        User user = userDetailsRepoService.findByEmail(email);

        // Encode and update the user's password 
        //with the new password
        user.setPassword(passwordEncoder.encode(req.getNewPassword()));

        // Save the update user information
        userDetailsRepoService.save(user);
    }


}