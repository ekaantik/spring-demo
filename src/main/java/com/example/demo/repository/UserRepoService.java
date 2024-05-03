package com.example.demo.repository;

import com.example.demo.constants.Constants;
import com.example.demo.security.entity.User;
import jakarta.persistence.PersistenceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
        try {
            Optional<User> optionalUser = userRepo.findById(id);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                log.info("Successfully found User with id " + user.getId());
                return user;
            }
            else {
                log.info("No User found with id " + id + ", returning null!");
                return null;
            }
        }
        catch (Exception ex) {
            log.error(Constants.UNEXPECTED_ERROR_MSG, ex);
            throw new PersistenceException(ex);
        }
    }


    public User findByPhoneNumber(String phoneNumber) {
        try {
            Optional<User> optionalUser = userRepo.findByPhoneNumber(phoneNumber);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                log.info("Successfully found User with phoneNumber " + user.getPhoneNumber());
                return user;
            }
            else {
                log.info("No User found with phoneNumber  " + phoneNumber + ", returning null!");
                return null;
            }
        }
        catch (Exception ex) {
            log.error(Constants.UNEXPECTED_ERROR_MSG, ex);
            throw new PersistenceException(ex);
        }
    }

    public User findByPhoneNumberAndPassword(String phoneNumber, String password) {
        try {
            Optional<User> optionalUser = userRepo.findByPhoneNumberAndPassword(phoneNumber, password);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                log.info("Successfully found User with phoneNumber {} & password  {} ", user.getPhoneNumber() ,  user.getPassword());
                return user;
            }
            else {
                log.info("No User found with phoneNumber phoneNumber {} & password  {} ", phoneNumber , password);
                return null;
            }
        }
        catch (Exception ex) {
            log.error(Constants.UNEXPECTED_ERROR_MSG, ex);
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
        try {
            User savedUser = userRepo.save(user);
            log.info("Successfully saved User with id " + savedUser.getId());
            return savedUser;
        }
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
        try {
            userRepo.deleteById(id);
            log.info("Successfully deleted User with id " + id);
        }
        catch (Exception ex) {
            log.error("Unexpected error occured!", ex);
            throw new PersistenceException(ex);
        }
    }

    /**
     * Handles Repo Exception & Deletes all User for a Customer.
     *
     */
//    public void deleteUserDetailsById(CustomerDetails customerDetails) {
//        //Trying to delete all User for a customer
//        try {
//
//            //All User deleted succesfully
//            List<User> users = userRepo.findByCustomerDetails(customerDetails);
//            userRepo.deleteAll(users);
//        }
//        catch (Exception ex) {
//            log.error(Constants.UNEXPECTED_ERROR_MSG, ex);
//            throw new PersistenceException(ex);
//        }
//    }

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
