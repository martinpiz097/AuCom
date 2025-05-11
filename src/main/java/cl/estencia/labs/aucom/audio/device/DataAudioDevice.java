package cl.estencia.labs.aucom.audio.device;

import lombok.extern.java.Log;

import javax.sound.sampled.*;

import static cl.estencia.labs.aucom.common.AudioQuality.DEFAULT_QUALITY;

@Log
public abstract class DataAudioDevice<D extends DataLine, I extends DataLine.Info>
        extends AudioDevice<D, I> {

    protected volatile AudioFormat audioFormat;

    public DataAudioDevice() {
        super();
    }

    public DataAudioDevice(AudioFormat quality) {
        this();
        this.audioFormat = quality;
    }

    public DataAudioDevice(D driver) {
        super(driver);
        this.audioFormat = driver.getFormat();
    }

    @Override
    protected boolean setupDriver(AudioFormat audioFormat) {
        if (audioFormat != null) {
            setupDriver(initAudioDevice(audioFormat));
            return setupDriver(driver);
        }
        return false;
    }

    @Override
    protected boolean setupDriver(D driver) {
        if (!close()) {
            return false;
        }
        this.driver = driver;
        this.audioFormat = driver.getFormat();
        return true;
    }

    protected AudioFormat getDefaultFormat() {
        return DEFAULT_QUALITY;
    }

    public boolean isRunning() {
        return super.isOpen() && driver.isRunning();
    }

    public AudioFormat getAudioFormat() {
        return audioFormat;
    }

    public void setAudioFormat(AudioFormat format) {
        setupDriver(format);
    }

    @Override
    public synchronized boolean open() {
        AudioFormat openFormat = audioFormat != null ? audioFormat : getDefaultFormat();
        return open(openFormat);
    }

    public boolean open(AudioFormat audioFormat) {
        if (isOpen() || audioFormat == null) {
            return false;
        }

        driver = initAudioDevice(audioFormat);
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

    public boolean reopen(AudioFormat audioFormat) {
        boolean closed;
        if (isOpen()) {
            closed = close();
            if (!closed) {
                return closed;
            }
        }
        return open(audioFormat);
    }
}
