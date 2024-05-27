package com.example.demo.service;

import java.util.Objects;
import java.util.UUID;

import com.example.demo.pojos.response.ManagerResponse;
import com.example.demo.pojos.response.TechnicianResponse;
import com.example.demo.pojos.response.UserResponse;

import org.springframework.beans.factory.annotation.Value;
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
    private static final String USER_BY_ID_KEY_PREFIX = "user_by_id_";
    private static final String MANAGER_BY_ID_KEY_PREFIX = "manager_by_id_";
    private static final String TECHNICIAN_BY_ID_KEY_PREFIX = "technician_by_id_";

    private final RedisTemplate<String, ShiftResponse> shiftTemplate;
    private final RedisTemplate<String, UserResponse> userTemplate;
    private final RedisTemplate<String, ManagerResponse> managerTemplate;
    private final RedisTemplate<String, TechnicianResponse> technicianTemplate;

    @Value("${spring.data.redis.enabled}")
    private boolean redisEnabled;

    /**
     * Save User Response to Redis Cache.
     * 
     * @param key   The key under which the user response will be stored.
     * @param value The user response value to be stored.
     */
    public void saveUserById(String key, UserResponse value) {

        if (Objects.isNull(key)) {
            log.info("Key is null. Cannot save vendor response to Redis Cache.");
            return;
        }

        userTemplate.opsForValue().set(USER_BY_ID_KEY_PREFIX + key, value);
    }

    /**
     * Save Manager Response to Redis Cache.
     * 
     * @param key   The key under which the manager response will be stored.
     * @param value The manager response value to be stored.
     */
    public void saveManagerById(String key, ManagerResponse value) {

        if (Objects.isNull(key)) {
            log.info("Key is null. Cannot save manager response to Redis Cache.");
            return;
        }

        managerTemplate.opsForValue().set(MANAGER_BY_ID_KEY_PREFIX + key, value);
    }

    /**
     * Save Technician Response to Redis Cache.
     * 
     * @param key   The key under which the technician response will be stored.
     * @param value The technician response value to be stored.
     */
    public void saveTechnicianById(String key, TechnicianResponse value) {

        if (Objects.isNull(key)) {
            log.info("Key is null. Cannot save technician response to Redis Cache.");
            return;
        }

        technicianTemplate.opsForValue().set(TECHNICIAN_BY_ID_KEY_PREFIX + key, value);
    }

    /**
     * Save Shift Response to Redis Cache.
     * 
     * @param key   The key under which the shift response will be stored.
     * @param value The shiftresponse value to be stored.
     */
    public void saveShiftById(String key, ShiftResponse value) {

        if (Objects.isNull(key)) {
            log.info("Key is null. Cannot save shift response to Redis Cache.");
            return;
        }

        shiftTemplate.opsForValue().set(SHIFT_BY_ID_KEY_PREFIX + key, value);
    }

    /**
     * Get UserResponse from Redis Cache.
     * 
     * @param key The key under which the userresponse are stored.
     * @return The userresponse value.
     */
    public UserResponse getUserById(String key) {

        if (redisEnabled) {
            log.info("Getting UserResponse from Redis Cache for key : {}", key);
            return userTemplate.opsForValue().get(USER_BY_ID_KEY_PREFIX + key);
        } else {
            return null;
        }
    }

    /**
     * Get ManagerResponse from Redis Cache.
     * 
     * @param key The key under which the managerresponse are stored.
     * @return The managerresponse value.
     */
    public ManagerResponse getManagerById(UUID key) {

        if (redisEnabled) {
            log.info("Getting ManagerResponse from Redis Cache for key : {}", key);
            return managerTemplate.opsForValue().get(MANAGER_BY_ID_KEY_PREFIX + key);
        } else {
            return null;
        }
    }

    /**
     * Get TechnicianResponse from Redis Cache.
     * 
     * @param key The key under which the technicianresponse are stored.
     * @return The technicianresponse value.
     */
    public TechnicianResponse getTechnicianById(UUID key) {

        if (redisEnabled) {
            log.info("Getting TechnicianResponse from Redis Cache for key : {}", key);
            return technicianTemplate.opsForValue().get(TECHNICIAN_BY_ID_KEY_PREFIX + key);
        } else {
            return null;
        }
    }

    /**
     * Get ShiftResponse from Redis Cache.
     * 
     * @param key The key under which the shiftresponse are stored.
     * @return The shiftresponse value.
     */
    public ShiftResponse getShiftById(String key) {

        if (redisEnabled) {
            log.info("Getting ShiftResponse from Redis Cache for key : {}", key);
            return shiftTemplate.opsForValue().get(SHIFT_BY_ID_KEY_PREFIX + key);
        } else {
            return null;
        }

    }

    /**
     * Clear User from Redis Cache.
     * 
     * @param key The key under which the user is stored.
     */
    public void clearUserById(String key) {
        userTemplate.delete(USER_BY_ID_KEY_PREFIX + key);
    }

    /**
     * Clear Manager from Redis Cache.
     * 
     * @param key The key under which the manager is stored.
     */
    public void clearManagerById(String key) {
        managerTemplate.delete(MANAGER_BY_ID_KEY_PREFIX + key);
    }

    /**
     * Clear Technician from Redis Cache.
     * 
     * @param key The key under which the technician is stored.
     */
    public void clearTechnicianById(String key) {
        technicianTemplate.delete(TECHNICIAN_BY_ID_KEY_PREFIX + key);
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
