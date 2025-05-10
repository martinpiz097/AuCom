package cl.estencia.labs.aucom.io;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

public abstract class AudioDecoder {
    protected final File source;
    protected final AudioInputStream sourceStream;

    public AudioDecoder(String path) throws UnsupportedAudioFileException, IOException {
        this(new File(path));
    }

    public AudioDecoder(File file) throws UnsupportedAudioFileException, IOException {
        this.source = file;
        this.sourceStream = initSourceStream(file);
    }

    protected AudioInputStream initSourceStream(File file) throws UnsupportedAudioFileException, IOException {
        return AudioSystem.getAudioInputStream(file);
    }

    public AudioFormat getBaseFormat() {
        return sourceStream.getFormat();
    }

    public abstract AudioInputStream getDecodedStream() throws UnsupportedAudioFileException, IOException;
}
