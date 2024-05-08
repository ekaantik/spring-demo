package com.ekaantik.demo.security.service;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ekaantik.demo.security.entity.User;
import com.ekaantik.demo.security.pojos.request.AuthenticationRequest;
import com.ekaantik.demo.security.pojos.request.RegisterRequest;
import com.ekaantik.demo.security.pojos.response.AuthenticationResponse;
import com.ekaantik.demo.security.repository.UserRepo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

        private final UserRepo userRepo;
        private final PasswordEncoder passwordEncoder;
        private final JwtService jwtService;
        private final AuthenticationManager authenticationManager;

        /**
         * Registers a new user and generates a JWT token.
         *
         * @param registerRequest The registration request.
         * @return An AuthenticationResponse with a JWT token.
         * @throws ResponseStatusException If user already exists.
         */
        public AuthenticationResponse register(RegisterRequest registerRequest) {

                User existingUser = userRepo.findByEmail(registerRequest.getEmail()).orElse(null);

                if (existingUser != null) {
                        log.error("User already exists with email : {}", registerRequest.getEmail());

                        throw new ResponseStatusException(
                                        HttpStatus.BAD_REQUEST,
                                        "User already exists with email: " + registerRequest.getEmail());
                }

                User user = User.builder()
                                .firstName(registerRequest.getFirstName())
                                .lastName(registerRequest.getLastName())
                                .email(registerRequest.getEmail())
                                .password(passwordEncoder.encode(registerRequest.getPassword()))
                                .build();

                User savedUser = userRepo.save(user);

                log.info("User saved : {}", savedUser);

                String jwtToken = jwtService.generateToken(user);

                log.info("User Profile Created");

                return AuthenticationResponse.builder()
                                .jwtToken(jwtToken)
                                .build();
        }

        /**
         * Authenticates a user and generates a JWT token.
         *
         * @param authRequest The authentication request.
         * @return An AuthenticationResponse with a JWT token.
         * @throws ResponseStatusException If user not found or password incorrect.
         */
        public AuthenticationResponse authenticate(AuthenticationRequest authRequest) {
                try {
                        authenticationManager.authenticate(
                                        new UsernamePasswordAuthenticationToken(authRequest.getEmail(),
                                                        authRequest.getPassword()));
                        User user = userRepo.findByEmail(authRequest.getEmail())
                                        .orElseThrow(() -> new UsernameNotFoundException(
                                                        "User not found with email : " + authRequest.getEmail()));

                        String jwtToken = jwtService.generateToken(user);
                        return AuthenticationResponse.builder()
                                        .jwtToken(jwtToken)
                                        .build();
                } catch (Exception e) {
                        log.error("Error authenticating user : {}", e.getMessage());

                        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
                }

        }

        /**
         * Refreshes a JWT token.
         *
         * @param token The JWT token.
         * @return An AuthenticationResponse with a new JWT token.
         * @throws ResponseStatusException If token is invalid.
         */
        public AuthenticationResponse refreshToken(String token) {

                String jwtToken = jwtService.extractToken(token);

                if (jwtService.isTokenValid(jwtToken)) {
                        String userName = jwtService.extractUsername(jwtToken);
                        User user = userRepo.findByEmail(userName)
                                        .orElseThrow(() -> new UsernameNotFoundException(
                                                        "User not found with email : " + userName));

                        String genToken = jwtService.generateToken(user);

                        return AuthenticationResponse.builder()
                                        .jwtToken(genToken)
                                        .build();
                } else {
                        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token");
                }
        }

        /**
         * Checks if a user exists with the given email.
         *
         * @param email The email to check.
         * @return True if user exists, false otherwise.
         */
        public boolean existsByEmail(String email) {
                return userRepo.existsByEmail(email);
        }

        /**
         * Verifies a JWT token.
         *
         * @param token The JWT token.
         * @return True if token is valid, false otherwise.
         */
        public boolean verifyToken(String token) {
                return jwtService.isTokenValid(token);
        }
}
