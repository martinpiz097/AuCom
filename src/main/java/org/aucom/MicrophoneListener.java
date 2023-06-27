package org.aucom;

import lombok.extern.java.Log;
import org.aucom.io.MicrophoneEvent;
import org.aucom.sound.Microphone;

import java.util.ArrayList;
import java.util.List;

@Log
public class MicrophoneListener extends Thread {
    private final Microphone microphone;
    private final List<MicrophoneEvent> listEvents;

    public MicrophoneListener(Microphone microphone) {
        this.microphone = microphone;
        listEvents = new ArrayList<>();
        start();
    }

    public void addEvent(MicrophoneEvent microphoneEvent) {
        listEvents.add(microphoneEvent);
    }

    @Override
    public void run() {
        byte[] audioBuffer;
        log.info("run() started thread!");
        while (true) {
            while (listEvents.isEmpty()) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            audioBuffer = microphone.readAudio();
            for (MicrophoneEvent event : listEvents)
                event.onAudioRead(audioBuffer);
        }
    }
}
