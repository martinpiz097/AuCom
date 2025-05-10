package cl.estencia.labs.aucom.audio.device;

import lombok.extern.java.Log;

import javax.sound.sampled.*;

import static cl.estencia.labs.aucom.common.AudioQuality.DEFAULT_QUALITY;

@Log
public abstract class DataAudioDevice<D extends DataLine, I extends DataLine.Info>
        extends AudioDevice<D, I> {
    public DataAudioDevice() {
        super();
    }

    public DataAudioDevice(AudioFormat quality) {
        super(quality);
    }

    public DataAudioDevice(D driver) {
        super(driver);
    }

    public AudioFormat getAudioFormat() {
        return driver.getFormat();
    }

    public void setAudioFormat(AudioFormat format) {
        initAudioDevice(format);
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
            try {
                driver.open();
                driver.start();
                return true;
            } catch (LineUnavailableException e) {
                log.severe("Error on open driver ("
                        +e.getClass().getSimpleName()
                        +"): " + e.getMessage());
                return false;
            }
        }
        return false;
    }

}
