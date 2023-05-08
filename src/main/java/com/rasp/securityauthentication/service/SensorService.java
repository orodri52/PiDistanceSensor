package com.rasp.securityauthentication.service;

import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SensorService {

    private int PIN_LED_16 = 16;
    private int PIN_LED_12 = 12;

    private Context context;
    private DigitalInput echoPin;
    private DigitalOutput triggerPin;
    private DigitalOutputConfigBuilder outConfig;
    private DigitalInputConfigBuilder inConfig;

    @Autowired
    public SensorService(Context context) {
        this.context = context;
        this.init();
    }

    private void init(){

        this.outConfig = DigitalOutput.newConfigBuilder(this.context)
                .id("trigger")
                .name("trigger")
                .address(this.PIN_LED_12)
                .provider("pigpio-digital-output");


        this.inConfig = DigitalInput.newConfigBuilder(this.context)
                .id("echo")
                .name("echo")
                .address(this.PIN_LED_16)
                .pull(PullResistance.PULL_DOWN)
                .provider("pigpio-digital-input");
    }

    public String getDistance() throws InterruptedException {
        Thread.sleep(2000);
        toOutput();
        triggerPin.setState(1);
        Thread.sleep((long) .01);
        triggerPin.setState(0);

        toInput();
        while (echoPin.isLow()) {

        }
        long startTime= System.nanoTime();
        while(echoPin.isHigh()){

        }
        long endTime= System.nanoTime();
        return ((((endTime-startTime)/1e3)/2) / 29.1) * 0.3937 + " Inches";
    }

    private void toInput() {
        if(this.echoPin == null) {
            this.echoPin = this.context.create(this.inConfig);
        }else{
            this.echoPin.initialize(this.context);
        }
    }

    private void toOutput() {
        if(this.triggerPin == null) {
            this.triggerPin = this.context.create(this.outConfig);
        }else{
            this.triggerPin.initialize(this.context);
        }
    }

}
