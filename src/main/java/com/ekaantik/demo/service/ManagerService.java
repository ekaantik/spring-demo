package com.ekaantik.demo.service;

import com.ekaantik.demo.entity.Manager;
import com.ekaantik.demo.entity.Vender;
import com.ekaantik.demo.repository.ManagerRepository;
import com.ekaantik.demo.repository.VenderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ManagerService {

    @Autowired
    ManagerRepository managerRepository;

    public Manager manageradd(Manager request) {
        return managerRepository.save(request);
    }

    public Optional<Manager> managerfindbyid(UUID uuid) {
        if (managerRepository.existsById(uuid)) {
            Optional<Manager> response = managerRepository.findById(uuid);
            return response;
        }
        return null;
    }

    public Manager managerupdate(UUID uuid, Manager manager) {
        if (managerRepository.existsById(uuid)) {
            manager.setId(uuid);
            manager.setFirstName(manager.getFirstName());
            manager.setLastName(manager.getLastName());
            manager.setPassword(manager.getPassword());
            manager.setPhoneNum(manager.getPhoneNum());
            return managerRepository.save(manager);
        }
        return null;
    }

    public String managerdeletebyid(UUID uuid) {
        if (managerRepository.existsById(uuid)) {
            managerRepository.deleteById(uuid);
            return "Manager Deleted...";
        }
        return "Manager Not Exist..";
    }

}
