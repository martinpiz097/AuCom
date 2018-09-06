/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.aucom.sound;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.FloatControl.Type;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import static org.aucom.sound.AudioInfo.DEFAULT_FORMAT;

/**
 *
 * @author martin
 */
public class Speaker extends AudioInterface {
    private volatile SourceDataLine driver;
    private volatile SourceDataLine.Info driverInfo;


    public Speaker() throws LineUnavailableException {
        super();
        driverInfo = new DataLine.Info(SourceDataLine.class, DEFAULT_FORMAT);
        driver = (SourceDataLine) AudioSystem.getLine(driverInfo);
    }

    public Speaker(AudioFormat quality) throws LineUnavailableException {
        configure(quality);
    }

    // Experimental
    public Speaker(SourceDataLine driver) {
        super(driver.getFormat());
        this.driver = driver;
    }

    public synchronized SourceDataLine getDriver() {
        return driver;
    }

    public float getGain() {
        return getControl(Type.MASTER_GAIN).getValue();
    }

    public void setGain(float gain){
        FloatControl control = getControl(Type.MASTER_GAIN);
        control.setValue(gain);
    }

    public void setDriver(SourceDataLine driver){
        if (driver != null)
            this.driver = driver;
    }

    @Override
    public synchronized void configure(AudioFormat quality) throws LineUnavailableException {
        driverInfo = new DataLine.Info(SourceDataLine.class, quality);
        driver = (SourceDataLine) AudioSystem.getLine(driverInfo);
    }

    @Override
    public synchronized boolean isOpen(){
        return driver.isOpen();
    }

    public synchronized AudioFormat getFormat(){
        return driver.getFormat();
    }

    /**
     * Returns the specified control, but if this is'n incompatible
     * this method returns null.
     * @param type Control's type to call.
     * @return The specified control or null if this method
     * throws an IllegalArgumentException internally.
     */
    public synchronized FloatControl getControl(FloatControl.Type type) {
        try {
            return (FloatControl) driver.getControl(type);
        } catch (IllegalArgumentException e) {
            return null;
        }

    }

    @Override
    public synchronized void open() throws LineUnavailableException {
        AudioFormat format = driver.getFormat();
        driver.open(format == null ? DEFAULT_FORMAT : format);
        driver.start();
    }

    @Override
    public synchronized void stop(){
        driver.stop();
    }

    @Override
    public synchronized void close(){
        driver.close();
    }

    public void playAudio(byte[] audioBuff){
        if(audioBuff == null)
            return;
        driver.write(audioBuff, 0, audioBuff.length);
    }

    public void playAudio(byte[] audioBuff, int len){
        if (audioBuff == null)
            return;
        if (len > audioBuff.length)
            len = audioBuff.length;
        driver.write(audioBuff, 0, len);
    }

}
