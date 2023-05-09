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

    /**
     * Initialization method that sets up the input/output used as the echo/trigger pin.
     * This is all from the pi4j documentation.
     */
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

    /**
     * Followed spec document in order to fire off and read trigger/echo pins.
     * Simple conversion method used to calculate distance d=rt. Taken from online example.
     * @return Distance in inches.
     * @throws InterruptedException
     */
    public String getDistance() throws InterruptedException {
        Thread.sleep(2000);
        toOutput();
        //this triggers the clock to start processing the data needed to calculate distance
        triggerPin.setState(1);
        Thread.sleep((long) .01);
        //start reading echo
        triggerPin.setState(0);

        toInput();
        while (echoPin.isLow()) {

        }
        //start clock
        long startTime= System.nanoTime();
        while(echoPin.isHigh()){

        }
        //end clock
        long endTime= System.nanoTime();
        //calculate distance
        return ((((endTime-startTime)/1e3)/2) / 29.1) * 0.3937 + " Inches";
    }

    /**
     * More out of the box setup for input
     */
    private void toInput() {
        if(this.echoPin == null) {
            this.echoPin = this.context.create(this.inConfig);
        }else{
            this.echoPin.initialize(this.context);
        }
    }

    /**
     * More out of the box setup for output
     */
    private void toOutput() {
        if(this.triggerPin == null) {
            this.triggerPin = this.context.create(this.outConfig);
        }else{
            this.triggerPin.initialize(this.context);
        }
    }

}
