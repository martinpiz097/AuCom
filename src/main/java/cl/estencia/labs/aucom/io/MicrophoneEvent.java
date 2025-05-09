package cl.estencia.labs.aucom.io;

import cl.estencia.labs.aucom.audio.device.Microphone;

public interface MicrophoneEvent {
    default void onAudioRead(byte[] buffer) {
        onAudioRead(buffer, Microphone.DEFAULT_BUFF_SIZE);
    }
    void onAudioRead(byte[] buffer, int length);
}
