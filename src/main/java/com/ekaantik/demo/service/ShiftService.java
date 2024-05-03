package com.ekaantik.demo.service;

import com.ekaantik.demo.entity.Manager;
import com.ekaantik.demo.entity.Shift;
import com.ekaantik.demo.repository.ManagerRepository;
import com.ekaantik.demo.repository.ShiftRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ShiftService {

    @Autowired
    ShiftRepository shiftRepository;

    public Shift shiftadd(Shift request) {
        return shiftRepository.save(request);
    }

    public Optional<Shift> shiftfindbyid(UUID uuid) {
        if (shiftRepository.existsById(uuid)) {
            Optional<Shift> response = shiftRepository.findById(uuid);
            return response;
        }
        return null;
    }

    public Shift shiftupdate(UUID uuid, Shift shift) {
        if (shiftRepository.existsById(uuid)) {
            shift.setId(uuid);
            shift.setName(shift.getName());
            shift.setEndTime(shift.getEndTime());
            shift.setStartTime(shift.getStartTime());
            return shiftRepository.save(shift);
        }
        return null;
    }

    public String shiftdeletebyid(UUID uuid) {
        if (shiftRepository.existsById(uuid)) {
            shiftRepository.deleteById(uuid);
            return "Shift Deleted...";
        }
        return "Shift Not Exist..";
    }

}
