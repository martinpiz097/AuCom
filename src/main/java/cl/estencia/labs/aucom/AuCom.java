package cl.estencia.labs.aucom;

import cl.estencia.labs.aucom.audio.device.Microphone;
import cl.estencia.labs.aucom.audio.device.Speaker;

import javax.sound.sampled.LineUnavailableException;

public class AuCom {

    public static void main(String[] args) throws LineUnavailableException, InterruptedException {
        Microphone microphone = new Microphone();
        Speaker speaker = new Speaker();

        microphone.open();
        speaker.open();

        microphone.listen(speaker::playAudio);
    }
}
