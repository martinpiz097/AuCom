package cl.estencia.labs.aucom.event;

import static cl.estencia.labs.aucom.common.IOConstants.DEFAULT_BUFF_SIZE;

public interface MicrophoneEvent {
    default void onAudioRead(byte[] buffer) {
        onAudioRead(buffer, DEFAULT_BUFF_SIZE);
    }
    void onAudioRead(byte[] buffer, int length);
}
