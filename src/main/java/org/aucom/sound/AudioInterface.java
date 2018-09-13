package org.aucom.sound;

import javax.sound.sampled.*;

/**
 *
 * @author martin
 */
public abstract class AudioInterface {

    public AudioInterface() {}

    public AudioInterface(AudioFormat quality) {}

    protected abstract DataLine.Info getLineInfo(AudioFormat format);

    public abstract void configure(AudioFormat format) throws LineUnavailableException;
    public abstract boolean isOpen();
    public abstract DataLine.Info getDriverInfo();
    public abstract void setDriverInfo(DataLine.Info info) throws LineUnavailableException;
    public abstract AudioFormat getFormat();
    public abstract FloatControl getControl(FloatControl.Type type);
    public abstract void open() throws LineUnavailableException;
    public abstract void stop();
    public abstract void close();
}
