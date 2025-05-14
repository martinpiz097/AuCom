package cl.estencia.labs.aucom.io;

import cl.estencia.labs.aucom.util.DecoderFormatUtil;
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
    protected final DecoderFormatUtil decoderFormatUtil;

    protected volatile AudioInputStream decodedStream;

    public AudioDecoder(String path) throws UnsupportedAudioFileException, IOException {
        this(new File(path), new DecoderFormatUtil());
    }

    public AudioDecoder(File file) throws UnsupportedAudioFileException, IOException {
        this(file, new DecoderFormatUtil());
    }

    public AudioDecoder(String path, DecoderFormatUtil decoderFormatUtil) throws UnsupportedAudioFileException, IOException {
        this(new File(path), decoderFormatUtil);
    }

    public AudioDecoder(File file, DecoderFormatUtil decoderFormatUtil) throws UnsupportedAudioFileException, IOException {
        this.source = file;
        this.decoderFormatUtil = decoderFormatUtil;
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

    protected abstract AudioFormat convertToPcmFormat(AudioFormat baseFormat);

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

        return decoderFormatUtil.decodeToPcm(sourceStream, pcmFormat);
    }

    public synchronized AudioInputStream getDecodedStream() {
        return decodedStream;
    }

    public synchronized void setDecodedStream(AudioInputStream decodedStream) {
        tryCloseCurrentStream(this.decodedStream);
        this.decodedStream = decodedStream;
    }

}
