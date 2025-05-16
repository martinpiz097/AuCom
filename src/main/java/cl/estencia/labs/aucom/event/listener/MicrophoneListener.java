package cl.estencia.labs.aucom.event.listener;

import cl.estencia.labs.aucom.core.device.input.AudioInputDevice;
import lombok.extern.java.Log;
import cl.estencia.labs.aucom.event.MicrophoneEvent;
import cl.estencia.labs.aucom.core.device.input.Microphone;

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
            log.info("MicrophoneListener started!");
            while (microphone.isOpen()) {
                while (listEvents.isEmpty()) {
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                listEvents.forEach(event ->
                        event.onAudioRead(microphone.readAudio()));
            }
        } catch (Exception e) {

        }

        log.info("MicrophoneListener finished!");
    }
}
