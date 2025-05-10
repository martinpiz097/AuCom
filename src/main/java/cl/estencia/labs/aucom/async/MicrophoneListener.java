package cl.estencia.labs.aucom.async;

import lombok.extern.java.Log;
import cl.estencia.labs.aucom.event.MicrophoneEvent;
import cl.estencia.labs.aucom.audio.device.Microphone;

import java.util.ArrayList;
import java.util.List;

@Log
public class MicrophoneListener extends Thread {
    private final Microphone microphone;
    private final List<MicrophoneEvent> listEvents;

    public MicrophoneListener(Microphone microphone) {
        this.microphone = microphone;
        listEvents = new ArrayList<>();
    }

    public void addEvent(MicrophoneEvent microphoneEvent) {
        listEvents.add(microphoneEvent);
    }

    @Override
    public void run() {
        byte[] audioBuffer;
        log.info("run() started thread!");
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
    }
}
