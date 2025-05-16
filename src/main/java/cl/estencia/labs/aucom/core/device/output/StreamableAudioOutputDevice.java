package cl.estencia.labs.aucom.core.device.output;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.SourceDataLine;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import static cl.estencia.labs.aucom.common.IOConstants.*;

public abstract class StreamableAudioOutputDevice extends AudioOutputDevice<SourceDataLine> {

    public StreamableAudioOutputDevice() {
        super();
    }

    public StreamableAudioOutputDevice(AudioFormat quality) {
        super(quality);
    }

    public StreamableAudioOutputDevice(SourceDataLine driver) {
        super(driver);
    }

    public void playAudio(byte[] audioBuff){
        if(audioBuff == null)
            return;

        playAudio(audioBuff, audioBuff.length);
    }

    public void playAudio(byte[] audioBuff, int len){
        if (audioBuff == null)
            return;

        driver.write(audioBuff, OFFSET, Math.min(len, audioBuff.length));
    }

    public void playAudio(ByteBuffer audioBuffer) {
        if (audioBuffer == null)
            return;

        playAudio(audioBuffer.array(), audioBuffer.capacity());
    }

    public void playAudio(ByteBuffer audioBuffer, int len) {
        if (audioBuffer == null)
            return;

        playAudio(audioBuffer.array(), len);
    }

    public void playAudioStream(InputStream inputStream) throws IOException {
        playAudioStream(inputStream, DEFAULT_BUFF_SIZE);
    }

    public void playAudioStream(InputStream inputStream, int bufferSize) throws IOException {
        playAudioStream(inputStream, new byte[bufferSize]);
    }

    public void playAudioStream(InputStream inputStream, byte[] audioBuffer) throws IOException {
        int read;
        while ((read = inputStream.read(audioBuffer)) != EOF) {
            playAudio(audioBuffer, read);
        }
    }
}
