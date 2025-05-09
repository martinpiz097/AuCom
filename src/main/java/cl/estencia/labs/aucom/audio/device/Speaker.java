package cl.estencia.labs.aucom.audio.device;

import javax.sound.sampled.*;
import javax.sound.sampled.FloatControl.Type;
import static cl.estencia.labs.aucom.audio.AudioQuality.DEFAULT_QUALITY;

/**
 *
 * @author martin
 */
public class Speaker extends AudioDevice {
    private volatile SourceDataLine driver;


    public Speaker() throws LineUnavailableException {
        super();
        driver = (SourceDataLine) AudioSystem.getLine(getLineInfo(DEFAULT_QUALITY));
        //printDriverInfo();
    }

    public Speaker(AudioFormat quality) throws LineUnavailableException {
        configure(quality);
        //printDriverInfo();
    }

    // Experimental
    public Speaker(SourceDataLine driver) {
        super(driver.getFormat());
        this.driver = driver;
        //printDriverInfo();
    }

    @Override
    protected synchronized DataLine.Info getLineInfo(AudioFormat format) {
        return new DataLine.Info(SourceDataLine.class, format);
    }

    /*private void printDriverInfo() {
        DataLine.Info driverInfo = getDriverInfo();
        System.out.println("LineInfo");
        System.out.println("--------------");
        System.out.println("MaxBuffSize: "+driverInfo.getMaxBufferSize());
        System.out.println("MinBuffSize: "+driverInfo.getMinBufferSize());
        System.out.println("BufferSize: "+driver.getBufferSize());
        System.out.println("MicrosecondPosition: "+driver.getMicrosecondPosition());
        System.out.println("FramePosition: "+driver.getFramePosition());
        System.out.println("--------------");
    }*/

    // testing
    /*public void showCurrentPositions() {
        System.out.println("MicrosecondPosition: "+driver.getMicrosecondPosition());
        System.out.println("SecondPosition: "+(driver.getMicrosecondPosition() / 1000000));
        System.out.println("FramePosition: "+driver.getFramePosition());
        System.out.println("--------------");
    }*/


    public float getGain() {
        return getControl(Type.MASTER_GAIN).getValue();
    }

    public void setGain(float gain){
        FloatControl control = getControl(Type.MASTER_GAIN);
        control.setValue(gain);
    }

    @Override
    public synchronized void configure(AudioFormat quality) throws LineUnavailableException {
        driver = (SourceDataLine) AudioSystem.getLine(getLineInfo(quality));
    }

    @Override
    public synchronized boolean isOpen(){
        return driver.isOpen();
    }

    public synchronized SourceDataLine getDriver() {
        return driver;
    }

    public synchronized SourceDataLine.Info getDriverInfo() {
        return (SourceDataLine.Info) driver.getLineInfo();
    }

    public void setDriver(SourceDataLine driver){
        if (driver != null && driver.isOpen()) {
            close();
            this.driver = driver;
        }
    }

    @Override
    public void setDriverInfo(DataLine.Info driverInfo) throws LineUnavailableException {
        if (driver != null) {
            driver.close();
            driver = (SourceDataLine) AudioSystem.getLine(driverInfo);
        }
    }

    @Override
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

    @Override
    public synchronized FloatControl getControl(FloatControl.Type type) {
        try {
            return (FloatControl) driver.getControl(type);
        } catch (IllegalArgumentException e) {
            return null;
        }

    }

    @Override
    public synchronized void open() throws LineUnavailableException {
        AudioFormat lineFormat = driver.getFormat();
        driver.open(lineFormat == null ? DEFAULT_QUALITY : lineFormat);
        driver.start();
    }

    @Override
    public synchronized void start() {
        driver.start();
    }

    @Override
    public synchronized void close(){
        driver.close();
    }

    @Override
    public synchronized void stop(){
        driver.stop();
    }

    public void playAudio(byte[] audioBuff){
        if(audioBuff == null)
            return;
        driver.write(audioBuff, 0, audioBuff.length);
        //showCurrentPositions();
    }

    public void playAudio(byte[] audioBuff, int len){
        if (audioBuff == null)
            return;
        if (len > audioBuff.length)
            len = audioBuff.length;
        driver.write(audioBuff, 0, len);
    }

}
