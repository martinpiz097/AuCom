package cl.estencia.labs.aucom;

import cl.estencia.labs.aucom.core.device.input.Microphone;
import cl.estencia.labs.aucom.core.device.output.Speaker;

public class AuCom {

    public static void main(String[] args) {
        Microphone microphone = new Microphone();
        Speaker speaker = new Speaker();

        microphone.open();
        speaker.open();

        microphone.listen(speaker::playAudio);
    }
}
