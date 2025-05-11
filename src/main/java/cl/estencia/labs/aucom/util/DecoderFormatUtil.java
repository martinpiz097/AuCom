package cl.estencia.labs.aucom.util;

import javax.sound.sampled.*;
import javax.sound.sampled.spi.AudioFileReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.ByteOrder;

import static cl.estencia.labs.aucom.common.AudioConstants.DEFAULT_MAX_VOL;

public abstract class DecoderFormatUtil {

    public static final float DEFAULT_VOLUME = DEFAULT_MAX_VOL;

    public boolean isSystemBigEndian() {
        return ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN;
    }

    public abstract AudioFormat convertToPcmFormat(AudioFormat baseFormat);

    public AudioInputStream getSourceAudioStream(Object source) throws IOException, UnsupportedAudioFileException {
        if (source instanceof File) {
            return AudioSystem.getAudioInputStream((File) source);
        } else if (source instanceof InputStream) {
            return AudioSystem.getAudioInputStream((InputStream) source);
        } else {
            return AudioSystem.getAudioInputStream((URL) source);
        }
    }

    public AudioInputStream getSourceAudioStream(AudioFileReader audioReader, Object source) throws IOException, UnsupportedAudioFileException {
        if (source == null) {
            return null;
        }
        if (source instanceof File) {
            return audioReader.getAudioInputStream((File) source);
        } else if (source instanceof InputStream) {
            return audioReader.getAudioInputStream((InputStream) source);
        } else {
            return audioReader.getAudioInputStream((URL) source);
        }
    }

    public AudioFileFormat getAudioFileFormat(Object source) throws UnsupportedAudioFileException, IOException {
        if (source == null) {
            return null;
        }
        if (source instanceof File) {
            return AudioSystem.getAudioFileFormat((File) source);
        } else if (source instanceof InputStream) {
            return AudioSystem.getAudioFileFormat((InputStream) source);
        } else {
            return AudioSystem.getAudioFileFormat((URL) source);
        }
    }

    public AudioInputStream decodeToPcm(AudioFileReader audioReader, Object source) throws IOException, UnsupportedAudioFileException {
        AudioInputStream encodedAudioStream = getSourceAudioStream(audioReader, source);
        return decodeToPcm(encodedAudioStream);
    }

    public AudioInputStream decodeToPcm(AudioInputStream sourceAis) {
        AudioFormat pcmFormat = convertToPcmFormat(sourceAis.getFormat());
        return AudioSystem.getAudioInputStream(pcmFormat, sourceAis);
    }
}
