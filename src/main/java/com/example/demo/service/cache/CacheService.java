package com.example.demo.service.cache;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.example.iot.entity.DeviceDetails;
import com.example.iot.entity.SensorDetails;
import com.example.iot.pojos.AlarmKeyValue;
import com.example.iot.utils.MapperUtil;
import com.fasterxml.jackson.core.type.TypeReference;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CacheService {

    private static final String ELECTRICITY_RATE_MAP_KEY_PREFIX = "electricity_rate_";
    private static final String CARBON_EMISSION_RATE_MAP_KEY_PREFIX = "carbon_emission_rate_";

    private static final String ASSET_DEVICE_MAP_KEY_PREFIX = "asset_device_";

    private static final String DEVICE_SENSOR_DETAILS_MAP_KEY_PREFIX = "device_sensor_details_";

    private static final String SENSOR_DETAILS_MAP_KEY_PREFIX = "sensor_details_";

    private static final String DEVICE_SENSOR_CATEGORY_MAP_KEY_PREFIX = "device_sensor_category_";

    private static final String DEVICE_ID_CUSTOMER_DEVICE_NAME_MAP_KEY_PREFIX = "device_id_customer_device_name_";

    private static final String DEVICE_CATEGORY_TO_FIELD_CODE_MAP_KEY_PREFIX = "device_category_to_field_code_";

    private static final String ALARM_NOTIFICATION_PROFILE_DATA_MAP_KEY_PREFIX = "alarm_notification_email_";

    private RedisTemplate<String, BigDecimal> electricityRateTemplate;
    private RedisTemplate<String, BigDecimal> carbonEmissionRateTemplate;

    private RedisTemplate<String, String> assetDeviceTemplate;

    private RedisTemplate<String, String> deviceSensorDetailsTemplate;

    private RedisTemplate<String, SensorDetails> sensorDetailsTemplate;

    private RedisTemplate<String, Set<String>> deviceSensorCategoryTemplate;

    private RedisTemplate<String, String> deviceIdCustomerDeviceNameTemplate;

    private RedisTemplate<String, List<String>> deviceCategoryToFieldCodeTemplate;

    private RedisTemplate<String, AlarmKeyValue> alarmNotificationProfileDataTemplate;

    public CacheService(RedisTemplate<String, BigDecimal> electricityRateTemplate,
            RedisTemplate<String, BigDecimal> carbonEmissionRateTemplate,
            RedisTemplate<String, String> assetDeviceTemplate,
            RedisTemplate<String, String> deviceSensorDetailsTemplate,
            RedisTemplate<String, SensorDetails> sensorDetailsTemplate,
            RedisTemplate<String, Set<String>> deviceSensorCategoryTemplate,
            RedisTemplate<String, String> deviceIdCustomerDeviceNameTemplate,
            RedisTemplate<String, List<String>> deviceCategoryToFieldCodeTemplate,
            RedisTemplate<String, AlarmKeyValue> alarmNotificationProfileDataTemplate) {
        this.electricityRateTemplate = electricityRateTemplate;
        this.carbonEmissionRateTemplate = carbonEmissionRateTemplate;
        this.assetDeviceTemplate = assetDeviceTemplate;
        this.deviceSensorDetailsTemplate = deviceSensorDetailsTemplate;
        this.sensorDetailsTemplate = sensorDetailsTemplate;
        this.deviceSensorCategoryTemplate = deviceSensorCategoryTemplate;
        this.deviceIdCustomerDeviceNameTemplate = deviceIdCustomerDeviceNameTemplate;
        this.deviceCategoryToFieldCodeTemplate = deviceCategoryToFieldCodeTemplate;
        this.alarmNotificationProfileDataTemplate = alarmNotificationProfileDataTemplate;
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
     * Save Carbon Emission Rate to Redis Cache.
     * 
     * @param key   The key under which the carbon emission rate will be stored.
     * @param value The carbon emission rate value to be stored.
     */
    public void saveCarbonEmissionRate(String key, BigDecimal value) {

        String redisKey = CARBON_EMISSION_RATE_MAP_KEY_PREFIX + key;
        log.info("Carbon Emission Key : {}", redisKey);
        carbonEmissionRateTemplate.opsForValue().set(redisKey, value);
    }

    /**
     * Save Asset Device to Redis Cache.
     * 
     * @param key   The key under which the asset device will be stored.
     * @param value The asset device value to be stored.
     */
    public void saveAssetDevice(String key, Set<DeviceDetails> value) {

        String redisKey = ASSET_DEVICE_MAP_KEY_PREFIX + key;
        log.info("Asset Device Key : {}", redisKey);

        String jsonValue = MapperUtil.toJson(value);
        assetDeviceTemplate.opsForHash().put(redisKey, key, jsonValue);
    }

    /**
     * Save Device Sensor Details to Redis Cache.
     * 
     * @param key   The key under which the device sensor details will be stored.
     * @param value The device sensor details value to be stored.
     */
    public void saveDeviceSensorDetails(String key, Set<SensorDetails> value) {
        String redisKey = DEVICE_SENSOR_DETAILS_MAP_KEY_PREFIX + key;
        log.info("Device Sensor Details Key : {}", redisKey);
        String jsonValue = MapperUtil.toJson(value);
        deviceSensorDetailsTemplate.opsForHash().put(redisKey, key, jsonValue);
    }

    /**
     * Save Sensor Details to Redis Cache.
     * 
     * @param key   The key under which the sensor details will be stored.
     * @param value The sensor details value to be stored.
     */
    public void saveSensorDetails(String key, SensorDetails value) {

        log.info("Sensor Details Key : {}", SENSOR_DETAILS_MAP_KEY_PREFIX + key);
        sensorDetailsTemplate.opsForValue().set(SENSOR_DETAILS_MAP_KEY_PREFIX + key, value);
    }

    /**
     * Save Device Sensor Category to Redis Cache.
     * 
     * @param key   The key under which the device sensor category will be stored.
     * @param value The device sensor category value to be stored.
     */
    public void saveDeviceSensorCategory(String key, Set<String> value) {

        log.info("Device Sensor Category Key : {}", DEVICE_SENSOR_CATEGORY_MAP_KEY_PREFIX + key);
        deviceSensorCategoryTemplate.opsForValue().set(DEVICE_SENSOR_CATEGORY_MAP_KEY_PREFIX + key, value);
    }

    /**
     * Save Device Id Customer Device Name to Redis Cache.
     * 
     * @param key   The key under which the device id customer device name will be
     *              stored.
     * @param value The device id customer device name value to be stored.
     */
    public void saveDeviceIdCustomerDeviceName(String key, String value) {

        log.info("Device Id Customer Device Name Key : {}", DEVICE_ID_CUSTOMER_DEVICE_NAME_MAP_KEY_PREFIX + key);
        deviceIdCustomerDeviceNameTemplate.opsForValue().set(DEVICE_ID_CUSTOMER_DEVICE_NAME_MAP_KEY_PREFIX + key,
                value);
    }

    /**
     * Save Device Category To Field Code to Redis Cache.
     * 
     * @param key   The key under which the device category to field code will be
     *              stored.
     * @param value The device category to field code value to be stored.
     */
    public void saveDeviceCategoryToFieldCode(String key, List<String> value) {

        log.info("Device Category To Field Code Key : {}", DEVICE_CATEGORY_TO_FIELD_CODE_MAP_KEY_PREFIX + key);
        deviceCategoryToFieldCodeTemplate.opsForValue().set(DEVICE_CATEGORY_TO_FIELD_CODE_MAP_KEY_PREFIX + key, value);
    }

    /**
     * Save Alarm Notification Profile Data to Redis Cache.
     * 
     * @param key   The key under which the alarm notification profile data will be
     *              stored.
     * @param value The alarm notification profile data value to be stored.
     */
    public void saveAlarmNotificationProfileData(String key, AlarmKeyValue value) {

        log.info("Alarm Notification Profile Data Key : {}", ALARM_NOTIFICATION_PROFILE_DATA_MAP_KEY_PREFIX + key);
        alarmNotificationProfileDataTemplate.opsForValue().set(ALARM_NOTIFICATION_PROFILE_DATA_MAP_KEY_PREFIX + key,
                value);
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
     * Get Carbon Emission Rate from Redis Cache.
     * 
     * @param key The key under which the carbon emission rate is stored.
     * @return The carbon emission rate value.
     */
    public BigDecimal getCarbonEmissionRate(String key) {
        return carbonEmissionRateTemplate.opsForValue().get(CARBON_EMISSION_RATE_MAP_KEY_PREFIX + key);
    }

    /**
     * Get Asset Device from Redis Cache.
     * 
     * @param key The key under which the asset device is stored.
     * @return The asset device value.
     */
    public Set<DeviceDetails> getAssetDevice(String key) {

        String redisKey = ASSET_DEVICE_MAP_KEY_PREFIX + key;

        // Retrieve the value from Redis
        Object value = assetDeviceTemplate.opsForHash().get(redisKey, key);

        // Value found
        if (value != null && value instanceof String) {
            String jsonValue = (String) value;
            return MapperUtil.fromJson(jsonValue, new TypeReference<Set<DeviceDetails>>() {
            });
        }

        // Value not found
        else {
            return null;
        }
    }

    /**
     * Get Device Sensor Details from Redis Cache.
     * 
     * @param key The key under which the device sensor details are stored.
     * @return The device sensor details value.
     */
    public Set<SensorDetails> getDeviceSensorDetails(String key) {

        String redisKey = DEVICE_SENSOR_DETAILS_MAP_KEY_PREFIX + key;

        // Retrieve the value from Redis
        Object value = deviceSensorDetailsTemplate.opsForHash().get(redisKey, key);

        // Value found
        if (value != null && value instanceof String) {
            String jsonValue = (String) value;
            return MapperUtil.fromJson(jsonValue, new TypeReference<Set<SensorDetails>>() {
            });
        }

        // Value not found
        else {
            return null;
        }
    }

    /**
     * Get Sensor Details from Redis Cache.
     * 
     * @param key The key under which the sensor details are stored.
     * @return The sensor details value.
     */
    public SensorDetails getSensorDetails(String key) {
        return sensorDetailsTemplate.opsForValue().get(SENSOR_DETAILS_MAP_KEY_PREFIX + key);
    }

    /**
     * Get Device Sensor Category from Redis Cache.
     * 
     * @param key The key under which the device sensor category is stored.
     * @return The device sensor category value.
     */
    public Set<String> getDeviceSensorCategory(String key) {
        return deviceSensorCategoryTemplate.opsForValue().get(DEVICE_SENSOR_CATEGORY_MAP_KEY_PREFIX + key);
    }

    /**
     * Get Device Id Customer Device Name from Redis Cache.
     * 
     * @param key The key under which the device id customer device name is stored.
     * @return The device id customer device name value.
     */
    public String getDeviceIdCustomerDeviceName(String key) {
        return deviceIdCustomerDeviceNameTemplate.opsForValue()
                .get(DEVICE_ID_CUSTOMER_DEVICE_NAME_MAP_KEY_PREFIX + key);
    }

    /**
     * Get Device Category To Field Code from Redis Cache.
     * 
     * @param key The key under which the device category to field code is stored.
     * @return The device category to field code value.
     */
    public List<String> getDeviceCategoryToFieldCode(String key) {
        return deviceCategoryToFieldCodeTemplate.opsForValue().get(DEVICE_CATEGORY_TO_FIELD_CODE_MAP_KEY_PREFIX + key);
    }

    /**
     * Get Alarm Notification Profile Data from Redis Cache.
     * 
     * @param key The key under which the alarm notification profile data is stored.
     * @return The alarm notification profile data value.
     */
    public AlarmKeyValue getAlarmNotificationProfileData(String key) {
        return alarmNotificationProfileDataTemplate.opsForValue()
                .get(ALARM_NOTIFICATION_PROFILE_DATA_MAP_KEY_PREFIX + key);
    }

    /**
     * Clear Electricity Rate from Redis Cache.
     * 
     * @param key The key under which the electricity rate is stored.
     */
    public void clearElectricityRate(String key) {
        electricityRateTemplate.delete(ELECTRICITY_RATE_MAP_KEY_PREFIX + key);
    }

    /**
     * Clear Carbon Emission Rate from Redis Cache.
     * 
     * @param key The key under which the carbon emission rate is stored.
     */
    public void clearCarbonEmissionRate(String key) {
        carbonEmissionRateTemplate.delete(CARBON_EMISSION_RATE_MAP_KEY_PREFIX + key);
    }

    /**
     * Clear Asset Device from Redis Cache.
     * 
     * @param key The key under which the asset device is stored.
     */
    public void clearAssetDevice(String key) {
        assetDeviceTemplate.delete(ASSET_DEVICE_MAP_KEY_PREFIX + key);
    }

    /**
     * Clear Device Sensor Details from Redis Cache.
     * 
     * @param key The key under which the device sensor details are stored.
     */
    public void clearDeviceSensorDetails(String key) {
        deviceSensorDetailsTemplate.delete(DEVICE_SENSOR_DETAILS_MAP_KEY_PREFIX + key);
    }

    /**
     * Clear Sensor Details from Redis Cache.
     * 
     * @param key The key under which the sensor details are stored.
     */
    public void clearSensorDetails(String key) {
        sensorDetailsTemplate.delete(SENSOR_DETAILS_MAP_KEY_PREFIX + key);
    }

    /**
     * Clear Device Sensor Category from Redis Cache.
     * 
     * @param key The key under which the device sensor category is stored.
     */
    public void clearDeviceSensorCategory(String key) {
        deviceSensorCategoryTemplate.delete(DEVICE_SENSOR_CATEGORY_MAP_KEY_PREFIX + key);
    }

    /**
     * Clear Device Id Customer Device Name from Redis Cache.
     * 
     * @param key The key under which the device id customer device name is stored.
     */
    public void clearDeviceIdCustomerDeviceName(String key) {
        deviceIdCustomerDeviceNameTemplate.delete(DEVICE_ID_CUSTOMER_DEVICE_NAME_MAP_KEY_PREFIX + key);
    }

    /**
     * Clear Device Category To Field Code from Redis Cache.
     * 
     * @param key The key under which the device category to field code is stored.
     */
    public void clearDeviceCategoryToFieldCode(String key) {
        deviceCategoryToFieldCodeTemplate.delete(DEVICE_CATEGORY_TO_FIELD_CODE_MAP_KEY_PREFIX + key);
    }

    /**
     * Clear Alarm Notification Profile Data from Redis Cache.
     * 
     * @param key The key under which the alarm notification profile data is stored.
     */
    public void clearAlarmNotificationProfileData(String key) {
        alarmNotificationProfileDataTemplate.delete(ALARM_NOTIFICATION_PROFILE_DATA_MAP_KEY_PREFIX + key);
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

    /**
     * Get Carbon Emission Rate Map from Redis Cache.
     * 
     * @return The carbon emission rate map.
     */
    public Map<String, BigDecimal> getCarbonEmissionRateMap() {
        Map<String, BigDecimal> carbonEmissionRateMap = new HashMap<>();
        for (String key : carbonEmissionRateTemplate.keys(CARBON_EMISSION_RATE_MAP_KEY_PREFIX + "*")) {
            String actualKey = key.replace(CARBON_EMISSION_RATE_MAP_KEY_PREFIX, "");
            carbonEmissionRateMap.put(actualKey, carbonEmissionRateTemplate.opsForValue().get(key));
        }
        return carbonEmissionRateMap;
    }

    /**
     * Get Asset Device Map from Redis Cache.
     * 
     * @return The asset device map.
     */
    public Map<String, Set<DeviceDetails>> getAssetDeviceMap() {
        Map<String, Set<DeviceDetails>> assetDeviceMap = new HashMap<>();
        for (String redisKey : assetDeviceTemplate.keys(ASSET_DEVICE_MAP_KEY_PREFIX + "*")) {
            String key = redisKey.replace(ASSET_DEVICE_MAP_KEY_PREFIX, "");

            // Retrieve the value from Redis
            Object value = deviceSensorDetailsTemplate.opsForHash().get(redisKey, key);

            // Value found
            if (value instanceof String) {
                String jsonValue = (String) value;
                Set<DeviceDetails> deviceDetails = MapperUtil.fromJson(jsonValue,
                        new TypeReference<Set<DeviceDetails>>() {
                        });
                assetDeviceMap.put(key, deviceDetails);
            }
        }
        return assetDeviceMap;
    }

    /**
     * Get Device Sensor Details Map from Redis Cache.
     * 
     * @return The device sensor details map.
     */
    public Map<String, Set<SensorDetails>> getDeviceSensorDetailsMap() {
        Map<String, Set<SensorDetails>> deviceSensorDetailsMap = new HashMap<>();
        for (String redisKey : deviceSensorDetailsTemplate.keys(DEVICE_SENSOR_DETAILS_MAP_KEY_PREFIX + "*")) {
            log.info("Key : {}", redisKey);
            String key = redisKey.replace(DEVICE_SENSOR_DETAILS_MAP_KEY_PREFIX, "");

            Object value = deviceSensorDetailsTemplate.opsForHash().get(redisKey, key);

            // Value found
            if (value != null && value instanceof String) {
                String jsonValue = (String) value;
                Set<SensorDetails> sensorDetails = MapperUtil.fromJson(jsonValue,
                        new TypeReference<Set<SensorDetails>>() {
                        });
                deviceSensorDetailsMap.put(key, sensorDetails);
            }
        }
        return deviceSensorDetailsMap;
    }

    /**
     * Get Sensor Details Map from Redis Cache.
     * 
     * @return The sensor details map.
     */
    public Map<String, SensorDetails> getSensorDetailsMap() {
        Map<String, SensorDetails> sensorDetailsMap = new HashMap<>();
        for (String key : sensorDetailsTemplate.keys(SENSOR_DETAILS_MAP_KEY_PREFIX + "*")) {
            String actualKey = key.replace(SENSOR_DETAILS_MAP_KEY_PREFIX, "");
            sensorDetailsMap.put(actualKey, sensorDetailsTemplate.opsForValue().get(key));
        }
        return sensorDetailsMap;
    }

    /**
     * Get Device Sensor Category Map from Redis Cache.
     * 
     * @return The device sensor category map.
     */
    public Map<String, Set<String>> getDeviceSensorCategoryMap() {
        Map<String, Set<String>> deviceSensorCategoryMap = new HashMap<>();
        for (String key : deviceSensorCategoryTemplate.keys(DEVICE_SENSOR_CATEGORY_MAP_KEY_PREFIX + "*")) {
            String actualKey = key.replace(DEVICE_SENSOR_CATEGORY_MAP_KEY_PREFIX, "");
            deviceSensorCategoryMap.put(actualKey, deviceSensorCategoryTemplate.opsForValue().get(key));
        }
        return deviceSensorCategoryMap;
    }

    /**
     * Get Device Id Customer Device Name Map from Redis Cache.
     * 
     * @return The device id customer device name map.
     */
    public Map<String, String> getDeviceIdCustomerDeviceNameMap() {
        Map<String, String> deviceIdCustomerDeviceNameMap = new HashMap<>();
        for (String key : deviceIdCustomerDeviceNameTemplate
                .keys(DEVICE_ID_CUSTOMER_DEVICE_NAME_MAP_KEY_PREFIX + "*")) {
            String actualKey = key.replace(DEVICE_ID_CUSTOMER_DEVICE_NAME_MAP_KEY_PREFIX, "");
            deviceIdCustomerDeviceNameMap.put(actualKey, deviceIdCustomerDeviceNameTemplate.opsForValue().get(key));
        }
        return deviceIdCustomerDeviceNameMap;
    }

    /**
     * Get Device Category To Field Code Map from Redis Cache.
     * 
     * @return The device category to field code map.
     */
    public Map<String, List<String>> getDeviceCategoryToFieldCodeMap() {
        Map<String, List<String>> deviceCategoryToFieldCodeMap = new HashMap<>();
        for (String key : deviceCategoryToFieldCodeTemplate.keys(DEVICE_CATEGORY_TO_FIELD_CODE_MAP_KEY_PREFIX + "*")) {
            String actualKey = key.replace(DEVICE_CATEGORY_TO_FIELD_CODE_MAP_KEY_PREFIX, "");
            deviceCategoryToFieldCodeMap.put(actualKey, deviceCategoryToFieldCodeTemplate.opsForValue().get(key));
        }
        return deviceCategoryToFieldCodeMap;
    }
}
