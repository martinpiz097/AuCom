package cl.estencia.labs.aucom.core.device.output;

import javax.sound.sampled.*;

/**
 *
 * @author martin
 */
public class Speaker extends StreamableAudioOutputDevice {
    public Speaker() {
        super();
    }

    public Speaker(AudioFormat quality) {
        super(quality);
    }

    public Speaker(SourceDataLine driver) {
        super(driver);
    }

    @Override
    protected synchronized DataLine.Info getLineInfo(AudioFormat format) {
        return new DataLine.Info(SourceDataLine.class, format);
    }

}
