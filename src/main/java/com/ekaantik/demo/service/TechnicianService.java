package com.ekaantik.demo.service;

import com.ekaantik.demo.entity.Technician;
import com.ekaantik.demo.entity.Vender;
import com.ekaantik.demo.repository.TechnicianRepository;
import com.ekaantik.demo.repository.VenderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class TechnicianService {

    @Autowired
    TechnicianRepository technicianRepository;

    public Technician technicianadd(Technician request) {
        return technicianRepository.save(request);
    }

    public Optional<Technician> technicianfindbyid(UUID uuid) {
        if (technicianRepository.existsById(uuid)) {
            Optional<Technician> response = technicianRepository.findById(uuid);
            return response;
        }
        return null;
    }

    public Technician technicianupdate(UUID uuid, Technician technician) {
        if (technicianRepository.existsById(uuid)) {
            technician.setId(uuid);
            technician.setFirstName(technician.getFirstName());
            technician.setLastName(technician.getLastName());
            technician.setPassword(technician.getPassword());
            technician.setPhoneNum(technician.getPhoneNum());
            return technicianRepository.save(technician);
        }
        return null;
    }

    public String techniciandeletebyid(UUID uuid) {
        if (technicianRepository.existsById(uuid)) {
            technicianRepository.deleteById(uuid);
            return "Technician Deleted...";
        }
        return "Technician Not Exist..";
    }
}