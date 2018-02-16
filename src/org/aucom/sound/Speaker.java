/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.aucom.sound;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import static org.aucom.sound.AudioInfo.DEFAULT_FORMAT;

/**
 *
 * @author martin
 */
public class Speaker extends AudioInterface {
    private SourceDataLine driver;
    private SourceDataLine.Info driverInfo;

    
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

    public SourceDataLine getDriver() {
        return driver;
    }

    public void setDriver(SourceDataLine driver){
        this.driver = driver;
    }
    
    @Override
    public void configure(AudioFormat quality) throws LineUnavailableException {
        driverInfo = new DataLine.Info(SourceDataLine.class, quality);
        driver = (SourceDataLine) AudioSystem.getLine(driverInfo);
    }

    @Override
    public boolean isOpen(){
        return driver.isOpen();
    }
    
    public AudioFormat getFormat(){
        return driver.getFormat();
    }
    
    @Override
    public void open() throws LineUnavailableException{
        AudioFormat format = driver.getFormat();
        driver.open(format == null ? DEFAULT_FORMAT : format);
        driver.start();
    }

    @Override
    public void stop(){
        driver.stop();
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
