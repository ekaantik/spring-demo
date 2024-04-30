package com.example.demo.repository;

import com.example.demo.constants.Constants;
import com.example.demo.entity.CustomerDetails;
import com.example.demo.entity.UserRoles;
import com.example.demo.security.entity.User;
import jakarta.persistence.PersistenceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class UserRoleRepoService {

    private final UserRolesRepo userRolesRepo;

    public UserRoleRepoService( UserRolesRepo userRolesRepo){
        this.userRolesRepo = userRolesRepo;
    }
    /**
     * Handles Repo Exception & finds a User by id.
     *
     * @param id The id of the User to find.
     * @return The User found by id, or null if Exception.
     */
    public UserRoles findById(UUID id) {
        try {
            Optional<UserRoles> optionalUser = userRolesRepo.findById(id);
            if (optionalUser.isPresent()) {
                UserRoles user = optionalUser.get();
                log.info("Successfully found User with id " + user.getId());
                return user;
            }
            else {
                log.info("No User found with id " + id + ", returning null!");
                return null;
            }
        }

        //Unexpected Error
        catch (Exception ex) {
            log.error(Constants.UNEXPECTED_ERROR_MSG, ex);
            throw new PersistenceException(ex);
        }
    }

    public UserRoles save(UserRoles userRoles) {
        try {
            UserRoles savedUserRoles = userRolesRepo.save(userRoles);
            log.info("Successfully saved User Roles " + savedUserRoles.getId());
            return savedUserRoles;
        }

        //Unexpected Error Occured
        catch (Exception ex) {
            log.error("Failed to create User, Exception : " + ex.getMessage(), ex);
            throw new PersistenceException("Failed To create User record into database!", ex);
        }
    }

    public void deleteUserRoleByUserId(UUID id) {
        //Trying to delete User
        try {
            //User deleted succesfully
            userRolesRepo.deleteById(id);
            log.info("Successfully deleted User with id " + id);
        }
        //Unexpected Error
        catch (Exception ex) {
            log.error("Unexpected error occured!", ex);
            throw new PersistenceException(ex);
        }
    }
}
