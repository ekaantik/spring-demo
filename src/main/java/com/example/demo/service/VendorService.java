package com.example.demo.service;


import com.example.demo.entity.Vendor;
import com.example.demo.repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class VendorService {

    @Autowired
    VendorRepository vendorRepository;
    public Vendor vendorAdd(Vendor request)
    {
        return vendorRepository.save(request);
    }

    public Optional<Vendor> vendorfindbyid(UUID uuid)
    {
        if (vendorRepository.existsById(uuid)) {
            Optional<Vendor> vendorResponse = vendorRepository.findById(uuid);
            return vendorResponse;
        }
        return null;
    }

    public Vendor vendorupdate(UUID uuid, Vendor vendor)
    {
        if (vendorRepository.existsById(uuid)) {
            vendor.setId(uuid);
            vendor.setFirstName(vendor.getFirstName());
            vendor.setLastName(vendor.getLastName());
            vendor.setPassword(vendor.getPassword());
            vendor.setPhoneNum(vendor.getPhoneNum());
            return vendorRepository.save(vendor);
        }
        return null;
    }
    public String vendordeletebyid(UUID uuid)
    {
        if (vendorRepository.existsById(uuid)) {
            vendorRepository.deleteById(uuid);
            return "Vendor Deleted...";
        }
        return "Vendor Not Exist..";
    }

}