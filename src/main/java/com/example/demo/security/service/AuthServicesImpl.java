package com.example.demo.security.service;

import com.example.demo.constants.Constants;
import com.example.demo.constants.ErrorCode;
import com.example.demo.constants.UserType;
import com.example.demo.exception.Details;
import com.example.demo.exception.InvalidCredentialException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.exception.RequestValidationException;
import com.example.demo.pojos.request.UserRequest;
import com.example.demo.pojos.response.UserResponse;
import com.example.demo.repository.UserRepoService;
import com.example.demo.security.dto.UserAuthRequest;
import com.example.demo.security.dto.UserAuthResponse;
import com.example.demo.security.dto.UserResetPasswordRequest;
import com.example.demo.security.dto.UserSignUpRequest;
import com.example.demo.security.entity.User;
import com.example.demo.security.utils.JwtTokenService;
import com.example.demo.service.RedisCacheService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServicesImpl implements AuthServicesIf {

    private final AuthenticationManager authenticationManager;
    private final RedisCacheService redisCacheService;

    @Autowired
    private UserRepoService userRepo;

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    /**
     * Performs Signup & assigns JWT Token.
     *
     * @param req : Incoming user auth request.
     * @return {@link UserAuthResponse AuthResponse}
     *         if succesfully signed-up,else error.
     */
    public UserAuthResponse performSignUp(UserSignUpRequest req) {
        User existingUser = userRepo.findByPhoneNumber(req.getPhoneNumber());

        if (existingUser != null) {
            log.error("User already exists with PhoneNumber : {}", req.getPhoneNumber());
            Details details = new Details(ErrorCode.INVALID_DATA.getAppError(),
                    ErrorCode.INVALID_DATA.getAppErrorCode(),
                    String.format(ErrorCode.INVALID_DATA.getAppErrorMessage(), "phoneNumber"));

            throw new RequestValidationException(
                    "User already exists with PhoneNumber : " + req.getPhoneNumber(),
                    HttpStatus.BAD_REQUEST.value(),
                    details);
        }

        User user = User.builder()
                .firstName(req.getFirstName())
                .lastName(req.getLastName())
                .phoneNumber(req.getPhoneNumber())
                .password(passwordEncoder.encode(req.getPassword()))
                .userType(UserType.VENDOR)
                .createdAt(ZonedDateTime.now())
                .updatedAt(ZonedDateTime.now())
                .build();

        User savedUser = userRepo.save(user);

        log.info("User saved : {}", savedUser);

        String jwtToken = jwtTokenService.generateToken(user);

        return UserAuthResponse.builder()
                .firstName(savedUser.getFirstName())
                .lastName(savedUser.getLastName())
                .phoneNumber(savedUser.getPhoneNumber())
                .userType(savedUser.getUserType())
                .jwtToken(jwtToken)
                .build();
    }

    /**
     * Perform user login and generate a JWT token.
     *
     * @param req : Incoming user auth request.
     * @return The user authentication response containing the JWT token and user
     *         information.
     */
    public UserAuthResponse performLogin(UserAuthRequest req) {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(req.getPhoneNumber(), req.getPassword()));
            User user = userRepo.findByPhoneNumber(req.getPhoneNumber());

            String jwtToken = jwtTokenService.generateToken(user);
            UserAuthResponse response = UserAuthResponse.builder()
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .phoneNumber(user.getPhoneNumber())
                    .jwtToken(jwtToken)
                    .userType(user.getUserType())
                    .build();

            return response;
        } catch (Exception e) {
            log.error("Invalid Credentials : {}", e.getMessage());
            throw new InvalidCredentialException(ErrorCode.INVALID_DATA, "PhoneNumber/Password");
        }
    }

    /**
     * Update the vendor by their ID.
     *
     * @param id  The ID of the vendor to update.
     * @param req The updated vendor information.
     * @return The updated vendor information.
     */
    public UserResponse updateVendor(String token, UserRequest req) {

        log.info("AuthServiceImpl Vendor Token : {}", token);
        UUID id = jwtTokenService.extractClaimsId(token);
        User user = userRepo.findById(id);

        // User Not Found
        if (Objects.isNull(user)) {
            log.error("User Not Found for Id : {}", id);
            throw new NotFoundException(ErrorCode.NOT_EXISTS, id, Constants.FIELD_ID, Constants.TABLE_VENDOR);
        }

        // Update User
        Optional.ofNullable(req.getFirstName()).ifPresent(user::setFirstName);
        Optional.ofNullable(req.getLastName()).ifPresent(user::setLastName);
        Optional.ofNullable(req.getPhoneNumber()).ifPresent(user::setPhoneNumber);
        user.setUpdatedAt(ZonedDateTime.now());

        // Save User
        userRepo.save(user);

        // Create UserResponse
        UserResponse response = UserResponse.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phoneNumber(user.getPhoneNumber())
                .userType(user.getUserType().toString())
                .build();

        // Save User to Redis Cache
        redisCacheService.saveUserById(id.toString(), response);
        log.info("User Info Updated to Redis Cache");
        return response;
    }

    /**
     * Get the vendor by their ID.
     *
     * @param vendorId The ID of the vendor to retrieve.
     * @return The vendor information.
     */
    public UserResponse getVendorById(UUID vendorId) {
        log.info("AuthServiceImpl getVendorById requested Id : {}", vendorId);
        UserResponse response = redisCacheService.getUserById(vendorId.toString());

        if (response != null) {
            log.info("AuthServiceImpl getVendorById getting response from redis cache: {}", response);
            return response;
        }

        User user = userRepo.findById(vendorId);

        if (Objects.isNull(user)) {
            Details details = new Details(ErrorCode.INVALID_DATA.getAppError(),
                    ErrorCode.INVALID_DATA.getAppErrorCode(),
                    String.format(ErrorCode.INVALID_DATA.getAppErrorMessage(), "vendorId"));
            throw new RequestValidationException(
                    "Invalid vendorId : " + vendorId,
                    HttpStatus.BAD_REQUEST.value(),
                    details);
        }

        response = UserResponse.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phoneNumber(user.getPhoneNumber())
                .userType(user.getUserType().toString())
                .build();

        log.info("AuthServiceImpl getVendorById received Technician response : {}", response);
        redisCacheService.saveUserById(vendorId.toString(), response);
        log.info("AuthServiceImpl getVendorById vendor Saved to Redis Cache : {}", user);
        return response;
    }

    /**
     * Refresh the JWT token.
     *
     * @param token The JWT token to refresh.
     * @return The refreshed JWT token.
     */
    public UserAuthResponse refreshToken(String token) {
        if (jwtTokenService.validateToken(token)) {

            User user = userRepo.findByPhoneNumber(jwtTokenService.extractPhoneNumber(token));
            String refToken = jwtTokenService.generateToken(user);

            return UserAuthResponse.builder()
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .phoneNumber(user.getPhoneNumber())
                    .jwtToken(refToken)
                    .userType(user.getUserType())
                    .build();
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Token");
        }
    }

    /**
     * @param user : User associated with the request.
     * @return UserAuthResponse that contains details about user.
     */
    public UserAuthResponse buildLoginResponse(User user) {
        String jwtToken = jwtTokenService.generateToken(user);
        return UserAuthResponse.builder()
                .phoneNumber(user.getPhoneNumber())
                .jwtToken(jwtToken)
                .userType(user.getUserType())
                .build();
    }

    /**
     * Checks if the user has the specified role.
     *
     * @param user : user associated with the request.
     * @param role The role to check.
     * @return True if the user has the specified role, else False.
     */
    // public boolean hasRole(User user, UserRolesDefault role) {
    // log.info("hasRole : {} userRoleList {} ", role, user.getUserType());
    // return user.getUserRolesList().stream()
    // .anyMatch(_role -> _role.getRole().getName()
    // .equalsIgnoreCase(role.toString()));
    //// return user.getRoles().stream()
    //// .anyMatch(_role -> _role.getName().equalsIgnoreCase(role.toString()));
    // }

    /**
     * Reset a user's password.
     *
     * @param req The user reset password request, including email, current
     *            password, and new password.
     * @throws IllegalArgumentException if the email or current password is invalid.
     */
    public void resetPassword(String token, UserResetPasswordRequest req) {

        // Authenticate the user with their current credentials
        String phoneNumber = (String) jwtTokenService.extractAllClaims(token).get("phoneNumber");

        // Retrieve the user based on their phoneNumber if found
        // else throw an exception
        User user = userRepo.findByPhoneNumber(phoneNumber);

        // Encode and update the user's password
        // with the new password
        user.setPassword(passwordEncoder.encode(req.getNewPassword()));

        // Save the update user information
        userRepo.save(user);
    }

}