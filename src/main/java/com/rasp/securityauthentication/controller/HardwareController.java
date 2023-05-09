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

    @Autowired
    public HardwareController(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @GetMapping("/distance")
    public String getDistance() throws InterruptedException {
        return this.sensorService.getDistance();

    }
}
