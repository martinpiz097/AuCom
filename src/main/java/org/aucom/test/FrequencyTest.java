package org.aucom.test;

import org.aucom.sound.AudioQuality;
import org.aucom.sound.Microphone;
import org.bytebuffer.ByteBuffer;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.LineUnavailableException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;

import static java.nio.file.StandardOpenOption.*;

public class FrequencyTest {
    static final NumberFormat nf = new DecimalFormat("#0.0");
    static final File stats = new File("/home/martin/Testings/AudioTesting/stats.txt");

    public static void main(String[] args) throws LineUnavailableException, IOException {
        stats.createNewFile();
        AudioFormat format = AudioQuality.VOICE;
        Microphone microphone = new Microphone(format);
        microphone.open();

        float frameRate = format.getFrameRate();
        int sampleSizeBytes = format.getSampleSizeInBits()/8;
        final int secBytes = (int) (frameRate*sampleSizeBytes);

        System.out.println("SecBytes: "+secBytes);
        ByteBuffer buffer = new ByteBuffer();

        System.out.println("Read started!");
        for (int i = 0; i < 10; i++)
            buffer.addFrom(microphone.readAudio(secBytes/10));
        System.out.println("Read finished!");
        microphone.close();

        saveData(buffer.drain());
    }

    public static void saveData(byte[] bytes) throws IOException {
        Files.write(stats.toPath(), Arrays.toString(bytes).getBytes(), WRITE);
    }

    public static void printFloats(byte[] bytes) throws IOException {

        double data;
        double divisor =
                1000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000.0;

        StringBuilder sbBytes = new StringBuilder();
        for (int i = 0; i < bytes.length; i+=8) {
            data = Double.longBitsToDouble(
                    NumberUtil.bytesToLong(Arrays.copyOfRange(bytes, i, i+8)));
            sbBytes.append(nf.format(data/divisor)).append(' ');
        }
        sbBytes.deleteCharAt(sbBytes.length()-1);
        sbBytes.append('\n');
        Files.write(stats.toPath(), sbBytes.toString().getBytes(), APPEND);
    }

}
