package cl.estencia.labs.aucom.audio.device;

import lombok.extern.java.Log;

import javax.sound.sampled.*;

import static cl.estencia.labs.aucom.common.AudioQuality.DEFAULT_QUALITY;

/**
 *
 * @author martin
 */
@Log
public abstract class AudioDevice<D extends Line, I extends Line.Info> {
    protected volatile D driver;

    public AudioDevice() {
        this(DEFAULT_QUALITY);
    }

    public AudioDevice(AudioFormat quality) {
        this.driver = initAudioDevice(quality);
    }

    public AudioDevice(D driver) {
        this.driver = driver;
    }

    protected D initAudioDevice(AudioFormat audioFormat) {
        return initAudioDevice(getLineInfo(audioFormat));
    }

    protected D initAudioDevice(I driverInfo) {
        try {
            return (D) AudioSystem.getLine(driverInfo);
        } catch (LineUnavailableException e) {
            log.severe("Error on audioDevice initialization "
                    + "("+e.getClass().getSimpleName()+"): "
                    + e.getMessage());
            return null;
        }
    }

    protected abstract I getLineInfo(AudioFormat format);

    public boolean isOpen() {
        return driver != null && driver.isOpen();
    }

    public D getDriver() {
        return driver;
    }

    public void setDriver(D driver){
        close();
        this.driver = driver;
    }

    public I getDriverInfo() {
        return (I) driver.getLineInfo();
    }

    public void setDriverInfo(I info) {
        close();
        driver = initAudioDevice(info);
    }


    public synchronized <C extends Control> C getControl(Control.Type type) {
        try {
            return (C) driver.getControl(type);
        } catch (IllegalArgumentException e) {
            log.severe("Error on getControl ("
                    +e.getClass().getSimpleName()
                    +"): " + e.getMessage());
            return null;
        }
    }

    public abstract boolean open();

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
