package cl.estencia.labs.aucom.audio.device;

import lombok.extern.java.Log;

import javax.sound.sampled.*;

import static cl.estencia.labs.aucom.audio.AudioQuality.DEFAULT_QUALITY;

/**
 *
 * @author martin
 */
@Log
public abstract class AudioDevice<T extends DataLine> {
    protected volatile T driver;

    public AudioDevice() {
        this(DEFAULT_QUALITY);
    }

    public AudioDevice(AudioFormat quality) {
        this.driver = initAudioDevice(quality);
    }

    public AudioDevice(T driver) {
        this.driver = driver;
    }

    protected T initAudioDevice(AudioFormat audioFormat) {
        return initAudioDevice(getLineInfo(audioFormat));
    }

    protected T initAudioDevice(DataLine.Info driverInfo) {
        try {
            return (T) AudioSystem.getLine(driverInfo);
        } catch (LineUnavailableException e) {
            log.severe("Error on audioDevice initialization "
                    + "("+e.getClass().getSimpleName()+"): "
                    + e.getMessage());
            return null;
        }
    }

    protected abstract DataLine.Info getLineInfo(AudioFormat format);

    public boolean isOpen() {
        return driver != null && driver.isOpen();
    }

    public T getDriver() {
        return driver;
    }

    public void setDriver(T driver){
        close();
        this.driver = driver;
    }

    public <I extends DataLine.Info> I getDriverInfo() {
        return (I) driver.getLineInfo();
    }

    public void setDriverInfo(DataLine.Info info) {
        close();
        driver = initAudioDevice(info);
    }

    public AudioFormat getAudioFormat() {
        return driver.getFormat();
    }
    public void setAudioFormat(AudioFormat format) {
        initAudioDevice(format);
    }

    public synchronized Control getControl(Control.Type type) {
        try {
            return driver.getControl(type);
        } catch (IllegalArgumentException e) {
            log.severe("Error on getControl ("
                    +e.getClass().getSimpleName()
                    +"): " + e.getMessage());
            return null;
        }
    }

    public synchronized FloatControl getFloatControl(FloatControl.Type type) {
        return (FloatControl) getControl(type);
    }

    public synchronized boolean open() {
        AudioFormat openFormat;
        if (driver != null) {
            if (driver.isOpen()) {
                driver.close();
            }
            AudioFormat driverFormat = driver.getFormat();
            openFormat = driverFormat != null ? driverFormat : DEFAULT_QUALITY;
        } else {
            openFormat = DEFAULT_QUALITY;
        }

        driver = initAudioDevice(openFormat);
        if (driver != null) {
            driver.start();
            return true;
        }
        return false;
    }

    public synchronized boolean close() {
        if (driver != null && driver.isOpen()) {
            driver.close();
            return true;
        }
        return false;
    }

    public boolean reopen() {
        boolean closed;
        if (isOpen()) {
            closed = close();
            if (!closed) {
                return closed;
            }
        }
        return open();
    }
}
