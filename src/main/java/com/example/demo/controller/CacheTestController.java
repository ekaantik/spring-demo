package com.example.demo.controller;

import com.example.demo.service.cache.CacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j
@RequestMapping("api/v1/cache-test")
public class CacheTestController {

    private CacheService cacheService;

    public CacheTestController(CacheService cacheService) {
        this.cacheService = cacheService;
    }


}
