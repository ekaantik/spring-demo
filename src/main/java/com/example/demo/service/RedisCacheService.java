package com.example.demo.service;

import java.util.Objects;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.example.demo.pojos.response.ShiftResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class RedisCacheService {

    private static final String SHIFT_BY_ID_KEY_PREFIX = "shift_by_id_";
    private final RedisTemplate<String, ShiftResponse> shiftTemplate;

    /**
     * Save Shift Response to Redis Cache.
     * 
     * @param key   The key under which the shift response will be stored.
     * @param value The shiftresponse value to be stored.
     */
    public void saveShiftById(String key, ShiftResponse value) {

        if (Objects.isNull(key)) {
            log.warn("Key is null. Cannot save shift response to Redis Cache.");
            return;
        }

        shiftTemplate.opsForValue().set(SHIFT_BY_ID_KEY_PREFIX + key, value);
    }

    /**
     * Get ShiftResponse from Redis Cache.
     * 
     * @param key The key under which the shiftresponse are stored.
     * @return The shiftresponse value.
     */
    public ShiftResponse getShiftById(String key) {
        log.info("Getting ShiftResponse from Redis Cache for key : {}", key);
        return shiftTemplate.opsForValue().get(SHIFT_BY_ID_KEY_PREFIX + key);
    }

    /**
     * Clear Shift from Redis Cache.
     * 
     * @param key The key under which the shift is stored.
     */
    public void clearShiftById(String key) {
        shiftTemplate.delete(SHIFT_BY_ID_KEY_PREFIX + key);
    }
}
