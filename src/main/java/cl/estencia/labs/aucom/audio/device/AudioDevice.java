package cl.estencia.labs.aucom.audio.device;

import lombok.extern.java.Log;

import javax.sound.sampled.*;

/**
 *
 * @author martin
 */
@Log
public abstract class AudioDevice<D extends Line, I extends Line.Info> {
    protected volatile D driver;

    public AudioDevice() {
        this(null);
    }

    public AudioDevice(D driver) {
        this.driver = driver;
    }

    protected abstract boolean setupDriver();
    protected abstract void setupDriver(D driver);

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

    public I getDriverInfo() {
        return driver != null ? (I) driver.getLineInfo() : null;
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
        if (isOpen()) {
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
