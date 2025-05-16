package cl.estencia.labs.aucom.core.device;

import cl.estencia.labs.aucom.core.device.output.AudioOutputDevice;
import cl.estencia.labs.aucom.io.AudioDecoder;
import lombok.extern.java.Log;

import javax.sound.sampled.*;
import java.io.IOException;

import static cl.estencia.labs.aucom.common.AudioQuality.DEFAULT_CLIP_FORMAT;

@Log
public class AudioClip extends AudioOutputDevice<Clip> {

    private AudioInputStream audioInputStream;

    private static final byte DEFAULT_LOOP_VALUE = -1;

    public AudioClip() {
        super(DEFAULT_CLIP_FORMAT);
    }

    public AudioClip(AudioFormat quality) {
        super(quality);
    }

    public AudioClip(Clip driver) {
        super(driver);
    }

    public AudioClip(AudioInputStream audioInputStream) {
        super(audioInputStream.getFormat());
        open(audioInputStream);
    }

    public AudioClip(AudioDecoder audioDecoder) {
        this(audioDecoder.getDecodedStream());
    }

    @Override
    protected boolean setupDriver(AudioFormat audioFormat) {
        return setupDriver(audioInputStream);
    }

    protected boolean setupDriver(AudioInputStream audioInputStream) {
        if (audioInputStream == null) {
            return false;
        }

        this.driver = initAudioDevice(getDefaultFormat());
        this.audioFormat = audioInputStream.getFormat();
        this.audioInputStream = audioInputStream;

        try {
            this.driver.open(audioInputStream);
            return true;
        } catch (LineUnavailableException | IOException e) {
            log.severe("Error on setupDriver ("
                    +e.getClass().getSimpleName()
                    +"): " + e.getMessage());
            return false;
        }
    }

    @Override
    protected AudioFormat getDefaultFormat() {
        return DEFAULT_CLIP_FORMAT;
    }

    @Override
    protected DataLine.Info getLineInfo(AudioFormat format) {
        return new DataLine.Info(Clip.class, format);
    }

    @Override
    public synchronized boolean open() {
        if (audioInputStream == null || isRunning() || isOpen()) {
            return false;
        }
        return setupDriver(audioFormat);
    }

    public boolean open(AudioInputStream audioInputStream) {
        if (isRunning() || isOpen()) {
            return false;
        }
        return setupDriver(audioInputStream);
    }

    @Override
    public boolean reopen() {
        return reopen(audioInputStream);
    }

    public boolean reopen(AudioInputStream audioInputStream) {
        if ((audioInputStream == null || isRunning() || isOpen()) && !close()) {
            return false;
        }
        return setupDriver(audioInputStream);
    }

    public boolean playAudio() {
        if (!driver.isOpen() || driver.isRunning()) {
            return false;
        }

        driver.start();
        return true;
    }

}
