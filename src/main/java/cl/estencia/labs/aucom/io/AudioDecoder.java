package cl.estencia.labs.aucom.io;

import cl.estencia.labs.aucom.util.AudioDecodingUtil;
import lombok.Data;
import lombok.extern.java.Log;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

@Data
@Log
public abstract class AudioDecoder {
    protected final File source;
    protected final AudioDecodingUtil audioDecodingUtil;

    protected volatile AudioInputStream decodedStream;

    public AudioDecoder(String path) throws UnsupportedAudioFileException, IOException {
        this(new File(path), new AudioDecodingUtil());
    }

    public AudioDecoder(File file) throws UnsupportedAudioFileException, IOException {
        this(file, new AudioDecodingUtil());
    }

    public AudioDecoder(String path, AudioDecodingUtil audioDecodingUtil) throws UnsupportedAudioFileException, IOException {
        this(new File(path), audioDecodingUtil);
    }

    public AudioDecoder(File file, AudioDecodingUtil audioDecodingUtil) throws UnsupportedAudioFileException, IOException {
        this.source = file;
        this.audioDecodingUtil = audioDecodingUtil;
        this.decodedStream = buildDecodedAudioStream();
    }

    protected AudioInputStream initSourceStream(File file) {
        try {
            return AudioSystem.getAudioInputStream(file);
        } catch (Exception e) {
            return null;
        }
    }

    public synchronized AudioFormat getDecodedFormat() {
        return decodedStream.getFormat();
    }

    public abstract AudioFormat convertToPcmFormat(AudioFormat baseFormat);

    public boolean tryCloseCurrentStream(AudioInputStream audioInputStream) {
        try {
            audioInputStream.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public synchronized void reDecode() {
        setDecodedStream(buildDecodedAudioStream());
    }

    public AudioInputStream buildDecodedAudioStream() {
        AudioInputStream sourceStream = initSourceStream(source);
        if (sourceStream == null) {
            return null;
        }

        AudioFormat baseFormat = sourceStream.getFormat();
        AudioFormat pcmFormat = convertToPcmFormat(baseFormat);

        return audioDecodingUtil.decodeToPcm(sourceStream, pcmFormat);
    }

    public synchronized AudioInputStream getDecodedStream() {
        return decodedStream;
    }

    public synchronized void setDecodedStream(AudioInputStream decodedStream) {
        tryCloseCurrentStream(this.decodedStream);
        this.decodedStream = decodedStream;
    }

}
