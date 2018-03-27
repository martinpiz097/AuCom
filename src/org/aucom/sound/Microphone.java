/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.aucom.sound;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;
import static org.aucom.sound.AudioInfo.BUFF_SIZE;
import static org.aucom.sound.AudioInfo.DEFAULT_FORMAT;

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

//    public static enum COMPRESS_TYPE{
//        NONE, OMEGA;
//    }
    
    public Microphone() throws LineUnavailableException {
        driverInfo = new DataLine.Info(TargetDataLine.class, DEFAULT_FORMAT);
        driver = (TargetDataLine) AudioSystem.getLine(driverInfo);
    }

    public Microphone(AudioFormat quality) throws LineUnavailableException {
        configure(quality);
    }

    // Experimental
    public Microphone(TargetDataLine driver) {
        super(driver.getFormat());
        this.driver = driver;
    }

    public TargetDataLine getDriver() {
        return driver;
    }
    
    public void setDriver(TargetDataLine driver){
        this.driver = driver;
    }

    public AudioInputStream getInputStream() {
        return new AudioInputStream(driver);
    }
    
    @Override
    public void configure(AudioFormat format) throws LineUnavailableException{
        driverInfo = new DataLine.Info(TargetDataLine.class, format);
        driver = (TargetDataLine) AudioSystem.getLine(driverInfo);
    }
    
    @Override
    public boolean isOpen(){
        return driver.isOpen();
    }
    
    public AudioFormat getFormat(){
        return driver.getFormat();
    }
    
    @Override
    public void open() throws LineUnavailableException {
        AudioFormat format = driver == null ? null : driver.getFormat();
        driver.open(format == null ? DEFAULT_FORMAT : format);
        driver.start();
    }
    
    @Override
    public void stop(){
        driver.stop();
    }
    
    @Override
    public void close(){
        driver.close();
    }
    
    public void reopen() throws LineUnavailableException{
        if (driver.isOpen())
            driver.stop();
        open();
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
    
//    public byte[] readAudio(COMPRESS_TYPE compressType){
//        return readAudio(compressType, BUFF_SIZE);
//    }
//    
//    public byte[] readAudio(COMPRESS_TYPE compressType, int len){
//        byte[] audio = readAudio(len);
//        if (compressType == COMPRESS_TYPE.NONE)
//            return audio;
//        else
//            if (new AudioBuffer(audio).isValidVoice())
//                return audio;
//            else
//                return null;
//    }
    
    public int readAudio(byte[] buffer, int off, int len){
        if (off >= len)
            throw new IndexOutOfBoundsException();
        if (off < 0)
            off = 0;
        if (len > buffer.length)
            len = buffer.length;
        return driver.read(buffer, off, len);
    }
    
//    public byte[] record(long time){
//        ByteBuffer buffer = new ByteBuffer();
//        long ti = System.currentTimeMillis();
//        byte[] au;
//        while (System.currentTimeMillis() - ti < time){
//            au = readAudio(COMPRESS_TYPE.OMEGA, 8);
//            if (au != null) {
//                buffer.addFrom(au);
//            }
//        }
//        return buffer.toArray();
//    }
    
}
