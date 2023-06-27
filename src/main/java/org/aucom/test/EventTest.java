package org.aucom.test;

import org.aucom.io.MicrophoneEvent;
import org.aucom.sound.AudioQuality;
import org.aucom.sound.Microphone;
import org.aucom.sound.Speaker;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.LineUnavailableException;
import java.util.concurrent.atomic.AtomicLong;

public class EventTest {
    public static void main(String[] args) throws Exception {
        micrphoneEvent();
    }

    private static void micrphoneEvent() throws LineUnavailableException, InterruptedException {
        final AudioFormat audioQuality = new AudioFormat(
                AudioFormat.Encoding.PCM_SIGNED, 6000.0f, 16,
                1, 2, 6000.0f, false);

        final Microphone microphone = new Microphone(audioQuality);
        microphone.open();
        final Speaker speaker = new Speaker(audioQuality);
        speaker.open();

        byte[] audioBuffer;
        int bufferLength = 16;
        boolean isEmpty;
        int audioData;
        byte minimum = 40;

        while (true) {
            audioBuffer = microphone.readAudio(bufferLength);
            isEmpty = true;

            for (int i = 0; i < audioBuffer.length; i++) {
                audioData = audioBuffer[i];
                if (audioData > Math.abs(minimum) || audioData < Math.negateExact(minimum)) {
                    isEmpty = false;
                    break;
                }
            }

            if (!isEmpty) {
                speaker.playAudio(audioBuffer);
                System.out.println("Se escucha");
            }
            else {
                System.out.println("No se escucha, andate a la chucha!");
            }
        }

    }
}
