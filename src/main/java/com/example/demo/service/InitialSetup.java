package com.example.demo.service;

import com.example.demo.service.cache.CacheService;
import org.springframework.stereotype.Component;

@Component
public class InitialSetup {


    private CacheService cacheService;

    public InitialSetup(CacheService cacheService) {

        this.cacheService = cacheService;
    }

}