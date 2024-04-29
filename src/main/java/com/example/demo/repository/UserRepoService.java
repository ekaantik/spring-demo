package com.example.demo.repository;


import com.example.demo.constants.Constants;
import com.example.demo.entity.CustomerDetails;
import com.example.demo.security.entity.User;
import jakarta.persistence.PersistenceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class UserRepoService {

    private final UserRepo userRepo;

    public UserRepoService(UserRepo userRepo){
        this.userRepo = userRepo;
    }
    /**
     * Handles Repo Exception & finds a User by id.
     *
     * @param id The id of the User to find.
     * @return The User found by id, or null if Exception.
     */
    public User findById(UUID id) {

        //Trying to find User by id
        try {

            //Succesfully found User
            Optional<User> optionalUser = userRepo.findById(id);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                log.info("Successfully found User with id " + user.getId());
                return user;
            }

            //No User found
            else {
                log.warn("No User found with id " + id + ", returning null!");
                return null;
            }
        }

        //Unexpected Error
        catch (Exception ex) {
            log.error(Constants.UNEXPECTED_ERROR_MSG, ex);
            throw new PersistenceException(ex);
        }
    }


    /**
     * Handles Repo Exception & finds a User by email.
     *
     * @param email The email of the User to find.
     * @return The User found by email, or null if Exception.
     */
    public User findByEmail(String email) {
        try {
            //Succesfully found User
            Optional<User> optionalUser = userRepo.findByEmail(email);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                log.info("Successfully found User with email " + user.getEmail());
                return user;
            }

            //No User found
            else {
                log.warn("No User found with email  " + email + ", returning null!");

                return null;
            }
        }

        //Unexpected Error
        catch (Exception ex) {
            log.error(Constants.UNEXPECTED_ERROR_MSG, ex);
            throw new PersistenceException(ex);
        }

    }


    /**
     * Handles Repo Exception & finds a User by email and password.
     *
     * @param email    The email of the User to find.
     * @param password The password of the User to find.
     * @return The User found by email and password, or null if Exception.
     */
    public User findByEmailAndPassword(String email, String password) {

        //Trying to find User by email & password
        try {

            //Succesfully found User
            Optional<User> optionalUser = userRepo.findByEmailAndPassword(email, password);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                log.info("Successfully found User with email & password " + user.getEmail()+" , " + user.getPassword() + " respectively!");
                return user;
            }

            //No User found
            else {
                log.warn("No User found with email " + email + " & password " + password + ", returning null!");
                return null;
            }
        }

        //Unexpected Error
        catch (Exception ex) {
            log.error(Constants.UNEXPECTED_ERROR_MSG, ex);
            throw new PersistenceException(ex);
        }
    }

    /**
     * Handles Repo Exception & finds a User by email and password.
     *
     * @return All Users found by CustomerDetails, or throws PersistenceException.
     */
    public List<User> findByCustomerDetails(CustomerDetails customerDetails) {

        //Trying to find All User for a Customer
        try {

            //Succesfully found All Users
            List<User> users = userRepo.findByCustomerDetails(customerDetails);
            log.info("Successfully found All Users for customer with id " + customerDetails.getId());

            return users;
        }

        //Unexpected Error
        catch (Exception ex) {
            log.error("Unexpected error occured!", ex);
            throw new PersistenceException(ex);
        }
    }

    /**
     * Handles Repo Exception & saves a User entity.
     *
     * @param user The User entity to be saved.
     * @return The saved User entity, or throws PersistenceException.
     */
    public User save(User user) {

        //Trying to save User
        try {

            //User saved succesfully
            User savedUser = userRepo.save(user);

            log.info("Successfully saved User with id " + savedUser.getId());
            return savedUser;
        }

        //Unexpected Error Occured
        catch (Exception ex) {
            log.error("Failed to create User, Exception : " + ex.getMessage(), ex);
            throw new PersistenceException("Failed To create User record into database!", ex);
        }
    }

    /**
     * Handles Repo Exception & Deletes a User by its Id.
     *
     * @param id The Id of the User to be deleted.
     */
    public void deleteUserDetailById(UUID id) {
        //Trying to delete User
        try {
            //User deleted successfully
            userRepo.deleteById(id);
            log.info("Successfully deleted User with id " + id);
        }
        //Unexpected Error
        catch (Exception ex) {
            log.error("Unexpected error occured!", ex);
            throw new PersistenceException(ex);
        }
    }

    /**
     * Handles Repo Exception & Deletes all User for a Customer.
     *
     * @param customerDetails The Custome associated with the respective users.
     */
    public void deleteUserDetailsById(CustomerDetails customerDetails) {
        //Trying to delete all User for a customer
        try {

            //All User deleted succesfully
            List<User> users = userRepo.findByCustomerDetails(customerDetails);
            userRepo.deleteAll(users);
        }
        catch (Exception ex) {
            log.error(Constants.UNEXPECTED_ERROR_MSG, ex);
            throw new PersistenceException(ex);
        }
    }

    public void delete(User user) {
        try {
            userRepo.delete(user);
        }
        catch (Exception ex) {
            log.error(Constants.UNEXPECTED_ERROR_MSG, ex);
            throw new PersistenceException(ex);
        }
    }
}
