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
    private TargetDataLine driver;
    private TargetDataLine.Info driverInfo;
    
    // Añadir mas adelante un buffer para almacenar el audio y desde ahi
    // rescatar bytes y reemplazar metodo de grabacion por algo mas completo
    // como por ejemplo si quiero grabar en au hacerlo con el otro metodo
    private AudioFormat format;
    
    public Microphone() throws LineUnavailableException {
        driverInfo = new DataLine.Info(TargetDataLine.class, DEFAULT_FORMAT);
        driver = (TargetDataLine) AudioSystem.getLine(driverInfo);
    }

    public Microphone(AudioFormat quality) throws LineUnavailableException {
        configure(quality);
    }
    
    public TargetDataLine getDriver() {
        return driver;
    }

    @Override
    public void configure(AudioFormat format) throws LineUnavailableException{
        this.format = format;
        driverInfo = new DataLine.Info(TargetDataLine.class, format);
        driver = (TargetDataLine) AudioSystem.getLine(driverInfo);
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
        return readAudio(BUFF_SIZE);
    }
    
    public byte[] readAudio(int len){
        byte[] audioBuff = new byte[len];
        driver.read(audioBuff, 0, len);
        return audioBuff;
    }
    
}
