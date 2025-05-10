package cl.estencia.labs.aucom.async;

import cl.estencia.labs.aucom.audio.device.AudioInputDevice;
import lombok.extern.java.Log;
import cl.estencia.labs.aucom.event.MicrophoneEvent;
import cl.estencia.labs.aucom.audio.device.Microphone;

import java.util.ArrayList;
import java.util.List;

@Log
public class MicrophoneListener extends Thread {
    private final AudioInputDevice microphone;
    private final List<MicrophoneEvent> listEvents;

    public MicrophoneListener(Microphone microphone) {
        this.microphone = microphone;
        this.listEvents = new ArrayList<>();
    }

    public void addEvent(MicrophoneEvent microphoneEvent) {
        listEvents.add(microphoneEvent);
    }

    @Override
    public void run() {
        try {
            byte[] audioBuffer;
            log.info("MicrophoneListener started!");
            while (microphone.isOpen()) {
                while (listEvents.isEmpty()) {
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                audioBuffer = microphone.readAudio();
                for (MicrophoneEvent event : listEvents) {
                    event.onAudioRead(audioBuffer);
                }
            }
        } catch (Exception e) {

        }

        log.info("MicrophoneListener finished!");
    }
}
