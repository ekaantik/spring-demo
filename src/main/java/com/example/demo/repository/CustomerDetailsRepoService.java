package com.example.demo.repository;

import com.example.iot.constants.Constants;
import com.example.iot.entity.CustomerDetails;
import jakarta.persistence.PersistenceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class CustomerDetailsRepoService {

    private final CustomerDetailsRepo customerDetailsRepo;

    CustomerDetailsRepoService(CustomerDetailsRepo customerDetailsRepo){
        this.customerDetailsRepo =customerDetailsRepo;
    }

    /**
     * Handles Repo Exception & finds a CustomerDetails by its Company Email.
     *
     * @param companyEmail The Company Email of the CustomerDetails to find.
     * @return The found CustomerDetails, or null if Exception.
     */
    public CustomerDetails findByCompanyEmail(String companyEmail) {

        //Trying to find CustomerDetails by companyEmail
        try {

            //Succesfully found CustomerDetails
            Optional<CustomerDetails> optionalCustoomerDetails = customerDetailsRepo.findByCompanyEmail(companyEmail);
            if (optionalCustoomerDetails.isPresent()) {
                CustomerDetails customerDetails = optionalCustoomerDetails.get();
                log.info("Successfully found CustomerDetails with companyEmail " + customerDetails.getCompanyEmail());
                return customerDetails;
            }

            //No CustomerDetails found
            else {
                log.warn("No CustomerDetails found with companyEmail " + companyEmail + ", returning null!");
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
     * Handles Repo Exception & finds a CustomerDetails by its Customer Id.
     *
     * @param customerId The Customer Id of the CustomerDetails to find.
     * @return The found CustomerDetails, or null if Exception.
     */
    public CustomerDetails findByCustomerId(String customerId) {

        //Trying to find CustomerDetails by customerId
        try {

            //Succesfully found CustomerDetails
            Optional<CustomerDetails> optionalCustoomerDetails = customerDetailsRepo.findByCustomerId(customerId);
            if (optionalCustoomerDetails.isPresent()) {
                CustomerDetails customerDetails = optionalCustoomerDetails.get();
                log.info("Successfully found CustomerDetails with customerId " + customerDetails.getCustomerId());
                return customerDetails;
            }

            //No CustomerDetails found
            else {
                log.warn("No CustomerDetails found with customerId " + customerId + ", returning null!");
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
     * Handles Repo Exception & finds a CustomerDetails by its Admin Email.
     *
     * @param adminEmail The Admin Email of the CustomerDetails to find.
     * @return The found CustomerDetails, or null if Exception.
     */
    public CustomerDetails findByAdminEmail(String adminEmail) {

        //Trying to find CustomerDetails by adminEmail
        try {

            //Succesfully found CustomerDetails
            Optional<CustomerDetails> optionalCustoomerDetails = customerDetailsRepo.findByAdminEmail(adminEmail);
            if (optionalCustoomerDetails.isPresent()) {
                CustomerDetails customerDetails = optionalCustoomerDetails.get();
                log.info("Successfully found CustomerDetails with adminEmail " + customerDetails.getAdminEmail());
                return customerDetails;
            }

            //No CustomerDetails found
            else {
                log.warn("No CustomerDetails found with adminEmail " + adminEmail + ", returning null!");
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
     * Handles Repo Exception & finds a CustomerDetails by its Id.
     *
     * @param id The Id of the CustomerDetails to find.
     * @return The found CustomerDetails, or null if Exception.
     */
    public CustomerDetails findById(UUID id) {

        //Trying to find CustomerDetails by id
        try {

            //Succesfully found CustomerDetails
            Optional<CustomerDetails> optionalCustoomerDetails = customerDetailsRepo.findById(id);
            if (optionalCustoomerDetails.isPresent()) {
                CustomerDetails customerDetails = optionalCustoomerDetails.get();
                log.info("Successfully found CustomerDetails with id " + customerDetails.getId());
                return customerDetails;
            }

            //No CustomerDetails found
            else {
                log.warn("No CustomerDetails found with id " + id + ", returning null!");
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
     * Handles Repo Exception & finds all CustomerDetails.
     *
     * @return A list of all CustomerDetails, or null if Exception.
     */
    public List<CustomerDetails> findAll() {

        //Trying to find all CustomerDetails
        try {

            //Succesfully found all CustomerDetails
            List<CustomerDetails> customerDetails = customerDetailsRepo.findAll();
            log.info("Successfully found all CustomerDetails");
            return customerDetails;
        }

        //Unexpected Error
        catch (Exception ex) {
            log.error(Constants.UNEXPECTED_ERROR_MSG, ex);
            throw new PersistenceException(ex);
        }
    }


    /**
     * Handles Repo Exception & saves a CustomerDetails entity.
     *
     * @param customerDetails The CustomerDetails to be saved.
     * @return The saved CustomerDetails, or throws PersistenceException.
     */
    public CustomerDetails save(CustomerDetails customerDetails) {

        //Trying to save CustomerDetails
        try {

            //CustomerDetails saved succesfully
            CustomerDetails savedCustomerDetails = customerDetailsRepo.save(customerDetails);
            log.info("Successfully saved CustomerDetails with id " + savedCustomerDetails.getId());
            return savedCustomerDetails;
        }

        //Unexpected Error Occured
        catch (Exception ex) {
            log.error("Failed to create CustomerDetails, Exception : " + ex.getMessage(), ex);
            throw new PersistenceException("Failed To create CustomerDetails record into database!", ex);
        }
    }


    /**
     * Handles Repo Exception & deletes a CustomerDetails by its Id.
     *
     * @param id The Id of the CustomerDetails to be deleted.
     */
    public void deleteById(UUID id) {

        //Trying to delete CustomerDetails by id
        try {

            //CustomerDetails deleted succesfully
            customerDetailsRepo.deleteById(id);
            log.info("Successfully deleted CustomerDetails with id " + id);
        }

        //Unexpected Error
        catch (Exception ex) {
            log.error(Constants.UNEXPECTED_ERROR_MSG, ex);
            throw new PersistenceException(ex);
        }
    }

}
