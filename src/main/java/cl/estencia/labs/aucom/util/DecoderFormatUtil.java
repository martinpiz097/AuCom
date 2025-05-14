package cl.estencia.labs.aucom.util;

import javax.sound.sampled.*;

import static cl.estencia.labs.aucom.common.AudioConstants.DEFAULT_MAX_VOL;

public class DecoderFormatUtil {

    public static final float DEFAULT_VOLUME = DEFAULT_MAX_VOL;

//    public AudioInputStream getSourceAudioStream(Object source) throws IOException, UnsupportedAudioFileException {
//        if (source instanceof File) {
//            return AudioSystem.getAudioInputStream((File) source);
//        } else if (source instanceof InputStream) {
//            return AudioSystem.getAudioInputStream((InputStream) source);
//        } else {
//            return AudioSystem.getAudioInputStream((URL) source);
//        }
//    }
//
//    public AudioInputStream getSourceAudioStream(AudioFileReader audioReader, Object source) throws IOException, UnsupportedAudioFileException {
//        if (source == null) {
//            return null;
//        }
//        if (source instanceof File) {
//            return audioReader.getAudioInputStream((File) source);
//        } else if (source instanceof InputStream) {
//            return audioReader.getAudioInputStream((InputStream) source);
//        } else {
//            return audioReader.getAudioInputStream((URL) source);
//        }
//    }

//    public AudioFileFormat getAudioFileFormat(Object source) throws UnsupportedAudioFileException, IOException {
//        if (source == null) {
//            return null;
//        }
//        if (source instanceof File) {
//            return AudioSystem.getAudioFileFormat((File) source);
//        } else if (source instanceof InputStream) {
//            return AudioSystem.getAudioFileFormat((InputStream) source);
//        } else {
//            return AudioSystem.getAudioFileFormat((URL) source);
//        }
//    }

//    public AudioInputStream decodeToPcm(AudioFileReader audioReader, Object source) throws IOException, UnsupportedAudioFileException {
//        AudioInputStream encodedAudioStream = getSourceAudioStream(audioReader, source);
//        return decodeToPcm(encodedAudioStream);
//    }

    public AudioInputStream decodeToPcm(AudioInputStream sourceAis, AudioFormat pcmFormat) {
        return AudioSystem.getAudioInputStream(pcmFormat, sourceAis);
    }

}
