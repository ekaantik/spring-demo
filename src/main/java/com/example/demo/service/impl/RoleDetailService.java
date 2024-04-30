package com.example.demo.service.impl;

import com.example.demo.pojos.request.RoleDetailRequest;
import com.example.demo.pojos.response.RoleDetailResponse;
import com.example.demo.repository.CustomerDetailsRepoService;
import com.example.demo.repository.RoleRepoService;
import com.example.demo.security.entity.Role;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class RoleDetailService {

    @Autowired
    private RoleRepoService roleRepoService;

    @Autowired
    private CustomerDetailsRepoService customerDetailsRepoService;

    public RoleDetailResponse createRoleDetail(RoleDetailRequest roleDetailRequest) {

//        RoleDetails roleDetails = new RoleDetails();
//        roleDetails.setRoleName(roleDetailRequest.getRole());
//
//        //Associate Cusotmer User Detail
//        UserDetails userDetails = userDetailRepository.findCustomerUserDetailById(roleDetailRequest.getCustomerUserDetailId());
//        //roleDetails.setCustomerUserDetail(customerUserDetails);
//
//        //Associate Plant Location Details
//        List<PlantDetails> plantDetails = new ArrayList<>();
//        for (UUID plantLocationId : roleDetailRequest.getPlantLocationDetailIds()) {
//            plantDetails.add(plantLocationDetailRepository.findPlantLocationDetailById(plantLocationId));
//        }
//        roleDetails.setPlantDetails(plantDetails);
//
//        // System.out.println("-x-x-x-x-x-x-x-x-[ROLE-2]-x-x--x-x--x\n" + roleDetailRequest);
//        roleDetailRepository.save(roleDetails);
//
//        return new RoleDetailResponse(roleDetails.getId(), roleDetails.getRoleName(),
//                roleDetails.getPlantDetails(),
//                Constants.SUCCESS_RESPONSE);
//
        return null;
    }

    public List<RoleDetailResponse> getAllRoleDetails() {
        List<RoleDetailResponse> roleDetailResponses = new ArrayList<>();
        List<Role> roleDetails = roleRepoService.findAll();

//        for (RoleDetails roleDetail : roleDetails) {
//            roleDetailResponses.add(new RoleDetailResponse(roleDetail.getId(), roleDetail.getRoleName(),
//                    roleDetail.getPlantDetails(),
//                    Constants.SUCCESS_RESPONSE));
//        }
//        return  roleDetailResponses;
        return null;
    }

    public RoleDetailResponse getRoleDetailById(UUID id) {
//        RoleDetails roleDetails = roleDetailRepository.findRoleDetailById(id);
//
//        return new RoleDetailResponse(roleDetails.getId(), roleDetails.getRoleName(), roleDetails.getPlantDetails(),
//                Constants.SUCCESS_RESPONSE);
        return null;
    }

    /**
     * Deletes role details record by its Id.
     *
     * @param id : Id of the role detail record.
     * @return true if the record deleted successfully,else false.
     */
    @Transactional
    public boolean deleteRoleDetailById(UUID id) {

        Role role = roleRepoService.findById(id);
        
        List<Role> customerRoleDetails = roleRepoService.findByCustomerDetailsId(role.getCustomerDetails().getId());
        if(customerRoleDetails.size()>1){
            try {
                roleRepoService.deleteRoleDetailById(id);
                return true;
            } catch (Exception ex) {
                return false;
            }
        }else{
            log.error("Cannot delete the last available role for customer : ", role.getCustomerDetails());
            return false;
        }

    }

//    @Transactional
//    public boolean deleteRoleDetailById(UUID id) {
//        try {
//            roleDetailRepository.deleteRoleDetailById(id);
//            return true;
//        } catch (Exception ex) {
//            return false;
//        }
//    }
}
