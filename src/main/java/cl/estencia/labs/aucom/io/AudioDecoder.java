package cl.estencia.labs.aucom.io;

import cl.estencia.labs.aucom.util.DecoderFormatUtil;
import lombok.Data;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

@Data
public abstract class AudioDecoder {
    protected final File source;
    protected final AudioInputStream sourceStream;
    protected final DecoderFormatUtil decoderFormatUtil;

    public AudioDecoder(String path, DecoderFormatUtil decoderFormatUtil) throws UnsupportedAudioFileException, IOException {
        this(new File(path), decoderFormatUtil);
    }

    public AudioDecoder(File file, DecoderFormatUtil decoderFormatUtil) throws UnsupportedAudioFileException, IOException {
        this.source = file;
        this.sourceStream = initSourceStream(file);
        this.decoderFormatUtil = decoderFormatUtil;
    }

    protected AudioInputStream initSourceStream(File file) throws UnsupportedAudioFileException, IOException {
        return AudioSystem.getAudioInputStream(file);
    }

    public AudioFormat getBaseFormat() {
        return sourceStream.getFormat();
    }

    public abstract AudioInputStream getDecodedStream();
}
