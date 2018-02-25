package com.saiton.ccs.finger;

import com.griaule.grfingerjava.FingerprintImage;
import com.griaule.grfingerjava.GrFingerJava;
import com.griaule.grfingerjava.GrFingerJavaException;
import com.griaule.grfingerjava.IFingerEventListener;
import com.griaule.grfingerjava.IImageEventListener;
import com.griaule.grfingerjava.IStatusEventListener;
import com.griaule.grfingerjava.MatchingContext;
import com.griaule.grfingerjava.Template;
import java.awt.Color;

/**
 *
 * @author Kasun
 */
public class FingerPrintDetect implements IStatusEventListener,
        IImageEventListener,
        IFingerEventListener {

    private MatchingContext fingerprintSDK;
    private FingerprintImage fingerprint;
    private Template template;
    private FingerPrintReceive ui = null;

    public FingerPrintDetect(FingerPrintReceive ui) {

        this.ui = ui;
        start();
    }

    public void destroy() {
       
    }

    private void destroyFingerprintSDK() {
       
    }

    private void start() {
//        try {
////            fingerprintSDK = new MatchingContext();
////            GrFingerJava.initializeCapture(this);
////            ui.writeLog("**Fingerprint SDK Initialized Successfull**");
//
//        } catch (GrFingerJavaException | NullPointerException e) {
//            //If any error ocurred while initializing GrFinger,
//            //writes the error to log
//            ui.fingerPrintExceptionOccured(e);
//           
//        }
    }

    @Override
    public void onSensorPlug(String idSensor) {
        //Logs the sensor has been pluged.
        ui.writeLog("Sensor: " + idSensor + ". Event: Plugged.");
        try {
            //Start capturing from plugged sensor.
            GrFingerJava.startCapture(idSensor, this, this);
        } catch (GrFingerJavaException e) {
            //write error to log
            
        }
    }

    @Override
    public void onSensorUnplug(String idSensor) {
        //Logs the sensor has been unpluged.
        ui.writeLog("Sensor: " + idSensor + ". Event: Unplugged.");
        try {
            GrFingerJava.stopCapture(idSensor);
        } catch (GrFingerJavaException e) {
            
        }
    }

    @Override
    public void onImageAcquired(String idSensor, FingerprintImage fingerprint) {
        //Logs that an Image Event occurred.
        ui.writeLog("Sensor: " + idSensor + ". Event: Image Captured.");

        this.fingerprint = fingerprint;
        ui.setImage(fingerprint);

        extract();
    }

    @Override
    public void onFingerDown(String idSensor) {
        // Just signals that a finger event ocurred.
        ui.writeLog("Sensor: " + idSensor + ". Event: Finger Placed.");

    }

    @Override
    public void onFingerUp(String idSensor) {
        // Just signals that a finger event ocurred.
        ui.writeLog("Sensor: " + idSensor + ". Event: Finger Removed.");

    }

    public void setBiometricDisplayColors(
            Color minutiaeColor, Color minutiaeMatchColor,
            Color segmentColor, Color segmentMatchColor,
            Color directionColor, Color directionMatchColor) {
        try {
            // set new colors for BiometricDisplay
            GrFingerJava.setBiometricImageColors(
                    minutiaeColor, minutiaeMatchColor,
                    segmentColor, segmentMatchColor,
                    directionColor, directionMatchColor);

        } catch (GrFingerJavaException e) {
            //write error to log
           
        }
    }

    public void extract() {
        try {
            template = fingerprintSDK.extract(fingerprint);
            String msg = "Template extracted successfully. ";
            switch (template.getQuality()) {
                case Template.HIGH_QUALITY:
                    msg += "High quality.";
                    break;
                case Template.MEDIUM_QUALITY:
                    msg += "Medium quality.";
                    break;
                case Template.LOW_QUALITY:
                    msg += "Low quality.";
                    break;
            }
            ui.writeLog(msg);
            ui.setImage(GrFingerJava.getBiometricImage(template, fingerprint));
        } catch (GrFingerJavaException e) {
            ui.writeLog(e.getMessage());
        }
    }

    public byte[] getFingerPrintData() {
        if (template != null) {
            try {
                return template.getData();
            } catch (Exception e) {
            }
        }
        return null;
    }

    public boolean isMatch(byte[] fingerFromDb) {
        try {
            fingerprintSDK.prepareForIdentification(this.template);
            Template referenceTemplate = new Template(fingerFromDb);
            boolean matched = fingerprintSDK.identify(referenceTemplate);
            return matched;
        } catch (GrFingerJavaException | IllegalArgumentException ex) {

        }
        return false;
    }
}
