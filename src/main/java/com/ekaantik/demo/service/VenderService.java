package com.ekaantik.demo.service;

import com.ekaantik.demo.entity.Vender;
import com.ekaantik.demo.repository.VenderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class VenderService {

    @Autowired
    VenderRepository venderRepository;
    public Vender venderadd(Vender request)
    {
        return venderRepository.save(request);
    }

    public Optional<Vender> venderfindbyid(UUID uuid)
    {
        if (venderRepository.existsById(uuid)) {
            Optional<Vender> venderResponse = venderRepository.findById(uuid);
            return venderResponse;
        }
        return null;
    }

    public Vender venderupdate(UUID uuid,Vender vender)
    {
        if (venderRepository.existsById(uuid)) {
            vender.setId(uuid);
            vender.setFirstName(vender.getFirstName());
            vender.setLastName(vender.getLastName());
            vender.setPassword(vender.getPassword());
            vender.setPhoneNum(vender.getPhoneNum());
            return venderRepository.save(vender);
        }
        return null;
    }
    public String venderdeletebyid(UUID uuid)
    {
        if (venderRepository.existsById(uuid)) {
            venderRepository.deleteById(uuid);
            return "Vender Deleted...";
        }
        return "Vender Not Exist..";
    }

}
