package com.example.demo.service.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class CacheService {

    private static final String ELECTRICITY_RATE_MAP_KEY_PREFIX = "electricity_rate_";
    private static final String CARBON_EMISSION_RATE_MAP_KEY_PREFIX = "carbon_emission_rate_";


    private RedisTemplate<String, BigDecimal> electricityRateTemplate;



    public CacheService(RedisTemplate<String, BigDecimal> electricityRateTemplate
) {
        this.electricityRateTemplate = electricityRateTemplate;
    }

    /**
     * Save Electricity Rate to Redis Cache.
     * 
     * @param key   The key under which the electricity rate will be stored.
     * @param value The electricity rate value to be stored.
     */
    public void saveElectricityRate(String key, BigDecimal value) {

        String redisKey = ELECTRICITY_RATE_MAP_KEY_PREFIX + key;
        log.info("Electricity Key : {}", redisKey);
        electricityRateTemplate.opsForValue().set(redisKey, value);
    }

    /**
     * Get Electricity Rate from Redis Cache.
     * 
     * @param key The key under which the electricity rate is stored.
     * @return The electricity rate value.
     */
    public BigDecimal getElectricityRate(String key) {
        return electricityRateTemplate.opsForValue().get(ELECTRICITY_RATE_MAP_KEY_PREFIX + key);
    }


    /**
     * Get Electricity Rate Map from Redis Cache.
     * 
     * @return The electricity rate map.
     */
    public Map<String, BigDecimal> getElectricityRateMap() {
        Map<String, BigDecimal> electricityRateMap = new HashMap<>();
        for (String key : electricityRateTemplate.keys(ELECTRICITY_RATE_MAP_KEY_PREFIX + "*")) {
            String actualKey = key.replace(ELECTRICITY_RATE_MAP_KEY_PREFIX, "");
            electricityRateMap.put(actualKey, electricityRateTemplate.opsForValue().get(key));
        }
        return electricityRateMap;
    }


}
