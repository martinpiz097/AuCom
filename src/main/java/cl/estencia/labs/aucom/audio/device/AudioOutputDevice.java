package cl.estencia.labs.aucom.audio.device;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.SourceDataLine;

import static cl.estencia.labs.aucom.common.IOConstants.EOF;

public abstract class AudioOutputDevice extends AudioDevice<SourceDataLine> {

    public AudioOutputDevice() {
        super();
    }

    public AudioOutputDevice(AudioFormat quality) {
        super(quality);
    }

    public AudioOutputDevice(SourceDataLine driver) {
        super(driver);
    }

    public float getGain() {
        FloatControl floatControl = getFloatControl(FloatControl.Type.MASTER_GAIN);
        return floatControl != null ? floatControl.getValue() : EOF;
    }

    public boolean setGain(float gain){
        FloatControl floatControl = getFloatControl(FloatControl.Type.MASTER_GAIN);
        if (floatControl != null) {
            floatControl.setValue(gain);
            return true;
        } else {
            return false;
        }
    }

    public void playAudio(byte[] audioBuff){
        if(audioBuff == null)
            return;
        driver.write(audioBuff, 0, audioBuff.length);
    }

    public void playAudio(byte[] audioBuff, int len){
        if (audioBuff == null)
            return;
        if (len > audioBuff.length)
            len = audioBuff.length;
        driver.write(audioBuff, 0, len);
    }
}
