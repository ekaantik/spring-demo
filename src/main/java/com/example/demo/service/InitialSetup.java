package com.example.demo.service;

import com.example.iot.repository.AlarmNotificationProfileRepoService;
import com.example.iot.repository.SensorRepoService;
import com.example.iot.service.cache.CacheService;
import com.example.iot.service.impl.AssetDetailsService;
import com.example.iot.service.impl.CarbonEmissionDetailService;
import com.example.iot.service.impl.DeviceService;
import com.example.iot.service.impl.ElectricityDetailService;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class InitialSetup {

    private final CarbonEmissionDetailService carbonEmissionDetailService;
    private final ElectricityDetailService electricityDetailService;
    private final DeviceService deviceService;
    private final SensorRepoService sensorRepoService;
    private final AssetDetailsService assetDetailsService;
    private final AlarmNotificationProfileRepoService alarmNotificationProfileRepoService;
    private CacheService cacheService;

    public InitialSetup(ElectricityDetailService electricityDetailService,
            CarbonEmissionDetailService carbonEmissionDetailService,
            AssetDetailsService assetDetailsService, DeviceService deviceService, SensorRepoService sensorRepoService,
            AlarmNotificationProfileRepoService alarmNotificationProfileRepoService, CacheService cacheService) {
        this.electricityDetailService = electricityDetailService;
        this.carbonEmissionDetailService = carbonEmissionDetailService;
        this.assetDetailsService = assetDetailsService;
        this.deviceService = deviceService;
        this.sensorRepoService = sensorRepoService;
        this.alarmNotificationProfileRepoService = alarmNotificationProfileRepoService;
        this.cacheService = cacheService;
    }

    @PostConstruct
    public void prepareElectricityRateMap() {
        electricityDetailService.getAllAssetElectricityRate()
                .forEach((k, v) -> cacheService.saveElectricityRate(k, v));
    }

    @PostConstruct
    public void prepareCarbonEmissionRateMap() {
        carbonEmissionDetailService.getAllAssetCarbonEmissionRate()
                .forEach((k, v) -> cacheService.saveCarbonEmissionRate(k, v));
    }

    @PostConstruct
    public void prepareAssetDeviceMap() {
        assetDetailsService.getAssetDeviceMapping()
                .forEach((k, v) -> cacheService.saveAssetDevice(k, v));
    }

    @PostConstruct
    public void prepareDeviceSensorMap() {
        deviceService.getDeviceSensorMapping()
                .forEach((k, v) -> cacheService.saveDeviceSensorDetails(k, v));
    }

    @PostConstruct
    public void prepareDeviceIdSensorMap() {
        deviceService.getDeviceSensorCategoryMapping()
                .forEach((k, v) -> cacheService.saveDeviceSensorCategory(k, v));
    }

    @PostConstruct
    public void prepareDeviceCustomerDeviceNameMap() {
        deviceService.getDeviceCustomerDeviceNameMapping()
                .forEach((k, v) -> cacheService.saveDeviceIdCustomerDeviceName(k, v));
    }

    @PostConstruct
    public void prepareDeviceCategoryToFieldCodeMapping() {
        deviceService.getDeviceCategoryFieldCodeMapping()
                .forEach((k, v) -> cacheService.saveDeviceCategoryToFieldCode(k, v));
    }

    @PostConstruct
    public void prepareSensorDetailsMap() {
        sensorRepoService.findAll()
                .forEach(sensor -> cacheService.saveSensorDetails(sensor.getFieldCode(), sensor));
    }

    @PostConstruct
    public void prepareAlarmDetailsMap() {
        alarmNotificationProfileRepoService.alarmNotificationProfileDataMap()
                .forEach((k, v) -> cacheService.saveAlarmNotificationProfileData(k, v));
    }
}