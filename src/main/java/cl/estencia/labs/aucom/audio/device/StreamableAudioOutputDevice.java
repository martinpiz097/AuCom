package cl.estencia.labs.aucom.audio.device;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.SourceDataLine;

public abstract class StreamableAudioOutputDevice extends AudioOutputDevice<SourceDataLine> {

    public StreamableAudioOutputDevice() {
        super();
    }

    public StreamableAudioOutputDevice(AudioFormat quality) {
        super(quality);
    }

    public StreamableAudioOutputDevice(SourceDataLine driver) {
        super(driver);
    }

    public void playAudio(byte[] audioBuff){
        if(audioBuff == null)
            return;
        playAudio(audioBuff, audioBuff.length);
    }

    public void playAudio(byte[] audioBuff, int len){
        if (audioBuff == null)
            return;
        if (len > audioBuff.length)
            len = audioBuff.length;
        driver.write(audioBuff, 0, len);
    }
}
