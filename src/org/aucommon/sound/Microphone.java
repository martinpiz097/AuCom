/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.aucommon.sound;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;
import static org.aucommon.sound.AudioInfo.BUFF_SIZE;
import static org.aucommon.sound.AudioInfo.DEFAULT_FORMAT;

/**
 *
 * @author martin
 */
public class Microphone extends AudioInterface{
    private final TargetDataLine driver;
    private final TargetDataLine.Info driverInfo;
    
    private AudioFormat format;
    
    public Microphone() throws LineUnavailableException {
        driverInfo = new DataLine.Info(TargetDataLine.class, DEFAULT_FORMAT);
        driver = (TargetDataLine) AudioSystem.getLine(driverInfo);
    }

    public TargetDataLine getDriver() {
        return driver;
    }

    @Override
    public void configure(AudioFormat quality){
        this.format = quality;
    }
    
    @Override
    public boolean isOpen(){
        return driver.isOpen();
    }
    
    @Override
    public void open() throws LineUnavailableException{
        driver.open(format == null ? DEFAULT_FORMAT : format);
        driver.start();
    }
    
    @Override
    public void stop(){
        driver.stop();
    }
    
    public byte[] readAudio(){
        // available va aumentando hasta llegar al limite del buffer
        // no sirve para saber cuantos bytes quedan por leer
        
        byte[] audioBuff = new byte[BUFF_SIZE];
        driver.read(audioBuff, 0, BUFF_SIZE);
        //System.out.println("Available: "+driver.available());
        //System.out.println(Arrays.toString(audioBuff));
//        int zeroCount = 0;
//        for (int i = 0; i < audioBuff.length; i++)
//            if (audioBuff[i] == 0)
//                zeroCount++;
//        System.out.println("Cantidad de ceros: "+zeroCount);
//        System.out.println("-------------------------------");
        
//        new Thread(() -> {
//            for (int i = 0; i < audioBuff.length; i++) {
//                if (audioBuff[i] < 0) {
//                    System.out.println("Menor a 0 --> "+audioBuff[i]);
//                }
//            }
//        }).start();
        //return AudioManager.getAudioCleaned(audioBuff);
        return audioBuff;
    }
    
}
