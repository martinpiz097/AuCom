package org.aucom.io;

import org.aucom.sound.Microphone;

public interface MicrophoneEvent {
    default void onAudioRead(byte[] buffer) {
        onAudioRead(buffer, Microphone.DEFAULT_BUFF_SIZE);
    }
    void onAudioRead(byte[] buffer, int length);
}
