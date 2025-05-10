package cl.estencia.labs.aucom.audio.device;

import javax.sound.sampled.*;
import javax.sound.sampled.FloatControl.Type;

import static cl.estencia.labs.aucom.common.IOConstants.EOF;

/**
 *
 * @author martin
 */
public class Speaker extends AudioOutputDevice {
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
