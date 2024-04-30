package com.example.demo.repository;

import com.example.demo.constants.Constants;
import com.example.demo.entity.CustomerDetails;
import com.example.demo.security.entity.Role;
import jakarta.persistence.PersistenceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class RoleRepoService {

    private final RoleRepo roleRepo;

    RoleRepoService(RoleRepo roleRepo){
        this.roleRepo = roleRepo;
    }

    /**
     * Handles Repo Exception & finds an Role by its Id.
     *
     * @param id The Id of the Role to find.
     * @return The found Role, or null if Exception.
     */
    public Role findById(UUID id) {

        //Trying to find Role by id
        try {

            //Succesfully found Role
            Optional<Role> optionalRole = roleRepo.findById(id);
            if (optionalRole.isPresent()) {
                Role role = optionalRole.get();
                log.info("Successfully found Role with id " + role.getId());
                return role;
            }

            //No Role found
            else {
                log.warn("No Role found with id " + id + ", returning null!");
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
     * Handles Repo Exception & finds a LoggerDetails by its Id.
     *
     * @param id The Id of the LoggerDetails to find.
     * @return The found LoggerDetails, or null if Exception.
     */
    public List<Role> findByCustomerDetailsId(UUID id) {

        //Trying to find Role by id
        try {

            List<Role> roleDetails = roleRepo.findByCustomerDetailsId(id);

            log.info("Successfully found Roles");
            return roleDetails;
        }

        //Unexpected Error
        catch (Exception ex) {
            log.error(Constants.UNEXPECTED_ERROR_MSG, ex);
            throw new PersistenceException(ex);
        }
    }


    /**
     * Handles Repo Exception & finds a list of LoggerDetails by AssetDetails Id list.
     *
     * @param assetIdList The list of AssetDetails Ids to search for LoggerDetails.
     * @return A list of LoggerDetails for the given AssetDetails Id list, or null if Exception.
     */
    public List<Role> findAll() {

        //Trying to find all Roles
        try {

            //Succesfully found all Roles
            List<Role> roleDetails = roleRepo.findAll();
            log.info("Successfully found all Role.");
            return roleDetails;
        }

        //Unexpected Error
        catch (Exception ex) {
            log.error(Constants.UNEXPECTED_ERROR_MSG, ex);
            throw new PersistenceException(ex);
        }
    }


    /**
     * Handles Repo Exception & saves a LoggerDetails entity.
     *
     * @param loggerDetails The LoggerDetails to be saved.
     * @return The saved LoggerDetails, or throws PersistenceException.
     */
    public Role save(Role role) {

        //Trying to save Role
        try {

            //Role saved succesfully
            Role savedRole = roleRepo.save(role);

            log.info("Successfully saved Role with id " + savedRole.getId());
            return savedRole;
        }

        //Unexpected Error Occured
        catch (Exception ex) {
            log.error("Failed to create Role, Exception : " + ex.getMessage(), ex);
            throw new PersistenceException("Failed To create Role record into database!", ex);
        }
    }


    /**
     * Handles Repo Exception & Deletes a Role by its Id.
     *
     * @param id The Id of the Role to be deleted.
     */
    public void deleteRoleDetailById(UUID id) {

        //Trying to delete Role
        try {

            //Role deleted succesfully
            roleRepo.deleteRoleById(id);
            log.info("Successfully deleted Role with id " + id);
        }

        //Unexpected Error
        catch (Exception ex) {
            log.error(Constants.UNEXPECTED_ERROR_MSG, ex);
            throw new PersistenceException(ex);
        }
    }

    public Role findRoleDetailsByRoleNameAndCustomerDetails(String roleName, CustomerDetails customerDetails) {
        // Trying to find RoleDetails
        try {
            Optional<Role> optionalRoleDetails = roleRepo.findByNameAndCustomerDetails(roleName,
                    customerDetails);
            if (optionalRoleDetails.isPresent()) {
                Role role = optionalRoleDetails.get();
                log.info("Successfully found RoleDetails with roleName " + roleName);
                return role;
            } else {
                log.info("No RoleDetails found with roleName " + roleName + ", returning null!");
                return null;
            }
        } catch (Exception ex) {
            log.error(Constants.UNEXPECTED_ERROR_MSG, ex);
            throw new PersistenceException(ex);
        }
    }
}
