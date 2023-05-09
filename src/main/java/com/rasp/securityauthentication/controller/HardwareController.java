package com.rasp.securityauthentication.controller;


import com.rasp.securityauthentication.service.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Endpoint to control hardware.
 */
@RestController
public class HardwareController {

    private SensorService sensorService;

    /**
     * Spring's dependency injection to the sensor component.
     * @param sensorService
     */
    @Autowired
    public HardwareController(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    /**
     * End point mapping that will get the distance for the given sensor.
     * @return Distance in inches.
     * @throws InterruptedException thread exception
     */
    @GetMapping("/distance")
    public String getDistance() throws InterruptedException {
        return this.sensorService.getDistance();

    }
}
