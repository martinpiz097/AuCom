package cl.estencia.labs.aucom.audio.device;

import cl.estencia.labs.aucom.common.VolumeConverter;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.SourceDataLine;

import static cl.estencia.labs.aucom.common.IOConstants.EOF;

public abstract class AudioOutputDevice extends DataAudioDevice<SourceDataLine, DataLine.Info> {

    protected final VolumeConverter volumeConverter;

    public AudioOutputDevice() {
        super();
        this.volumeConverter = new VolumeConverter();
    }

    public AudioOutputDevice(AudioFormat quality) {
        super(quality);
        this.volumeConverter = new VolumeConverter();
    }

    public AudioOutputDevice(SourceDataLine driver) {
        super(driver);
        this.volumeConverter = new VolumeConverter();
    }

    public float getVolume() {
        FloatControl floatControl = getControl(FloatControl.Type.MASTER_GAIN);
        return floatControl != null
                ? volumeConverter.dataLineVolumeToVolume(floatControl.getValue())
                : EOF;
    }

    public boolean setVolume(float volume){
        FloatControl floatControl = getControl(FloatControl.Type.MASTER_GAIN);
        if (floatControl != null) {
            floatControl.setValue(volumeConverter.volumeToDataLineVolume(volume));
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
