package cl.estencia.labs.aucom.audio.device;

import javax.sound.sampled.*;

/**
 *
 * @author martin
 */
public abstract class AudioDevice {

    public AudioDevice() {}

    public AudioDevice(AudioFormat quality) {}

    protected abstract DataLine.Info getLineInfo(AudioFormat format);

    public abstract void configure(AudioFormat format) throws LineUnavailableException;
    public abstract boolean isOpen();
    public abstract DataLine.Info getDriverInfo();
    public abstract void setDriverInfo(DataLine.Info info) throws LineUnavailableException;
    public abstract AudioFormat getFormat();
    public abstract FloatControl getControl(FloatControl.Type type);
    public abstract void open() throws LineUnavailableException;
    public abstract void close();
}
