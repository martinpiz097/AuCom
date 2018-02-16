/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.aucommon.test;

import java.io.File;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import static java.lang.Thread.MIN_PRIORITY;
import java.nio.file.Files;
import java.nio.file.Path;
import static java.nio.file.StandardOpenOption.APPEND;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;
import org.aucom.io.ByteBuffer;
import org.aucom.sound.AudioQuality;
import org.aucom.sound.Microphone;
import org.aucom.sound.Speaker;

/**
 *
 * @author martin
 */
public class TestAUFormat {
    public static void main(String[] args) throws LineUnavailableException, IOException, InterruptedException {

        // Grabar con audiosystem es lo mismo que grabar con target solo pero 
        // esta opcion me permite grabar en formato au que es un poco mas liviano que wav
        
        // Verificar nuevamente si al reproducir con audioplayer no se genera el eco
        
        AudioFormat format = AudioQuality.LOW;
        
        Microphone micro = new Microphone();
        micro.configure(format);
        micro.open();
        TargetDataLine tdl = micro.getDriver();
        
        //ElectroOutputStream electroOutput = new ElectroOutputStream();
        PipedOutputStream output = new PipedOutputStream();
        PipedInputStream input = new PipedInputStream(output);
        
        Thread tRecord = new Thread(()->{
            try {
                AudioSystem.write(new AudioInputStream(tdl), AudioFileFormat.Type.AU, output);
            } catch (IOException ex) {
                Logger.getLogger(TestAUFormat.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        tRecord.start();
//        System.out.println("Escuchando..........");
//        System.out.println("Bytes: "+electroOutput.getWritedBytes().length);
//        byte[] audioBytes = electroOutput.getWritedBytes();
//        
//        
//        
//        int major = -200;
//        int minor = 200;
//        int b;
//        
//        for (int i = 0; i < audioBytes.length; i++){
//            b = audioBytes[i];
//            if (b > major)
//                major = b;
//            if (b < minor) 
//                minor = b;
//        }
//        System.out.println("Mayor: "+major);
//        System.out.println("Menor: "+minor);
    
        //ElectroInputStream electroInput = baos.getElectroInput();
        //AudioPlayer player = AudioPlayer.player;
        //player.start(new ByteArrayInputStream(baos.toByteArray()));
        
        Speaker speaker = new Speaker();
        speaker.configure(format);
        speaker.open();

        byte[] buff = new byte[1024];
        double kbytes = (double)buff.length/1024;
        double byteCount = 0;
        NumberFormat nf = new DecimalFormat("#0.00");
        long ti = System.currentTimeMillis();
        
        
        LinkedList<byte[]> listBytes = new LinkedList<>();
        Thread tPlayer = new Thread(() -> {
            byte[] first;
            while (true) {
                first = listBytes.pollFirst();
                if (first != null) {
                    //speaker.playAudio(reduceAudio2(first));
                    reduceAudio2(first);
                }
//            if (System.currentTimeMillis()-ti >= 1000) {
//                System.out.println(nf.format(byteCount)+" kbps");
//                byteCount = 0;
//                ti = System.currentTimeMillis();
//            }
//            byteCount+=kbytes;
                System.out.print("");
            }
        });
        tPlayer.setPriority(MIN_PRIORITY);
        tPlayer.start();
        while (true) {
            buff = new byte[1024];
            input.read(buff);
            listBytes.add(buff);
        }
    }
    
    public static byte[] reduceAudio(byte[] audio) {
        try {
            Path path = new File("/home/martin/AudioTesting/bytes.txt").toPath();
            if (audio == null) {
                System.out.println("AudioNulo");
                return null;
            }
            ByteBuffer buffer = new ByteBuffer();
            byte b;
            short major = -200;
            short minor = 200;
            for (int i = 0; i < audio.length; i++) {
                b = audio[i];
                if(b <= 126 && b != -1)
                    buffer.add(b);
                //System.out.print(b+" - ");
                if (b > major)
                    major = b;
                if (b < minor)
                    minor = b;
                System.out.print(b+" . ");
            }
            System.out.println("");
            System.out.println("Mayor: "+major);
            System.out.println("Menor: "+minor);
            System.out.println("BuffSize: "+(audio.length-(audio.length-buffer.size())));
            System.out.println("------------");
            Files.write(path, (Arrays.toString(audio)+"\n").getBytes(), APPEND);
            return buffer.getArray();
        } catch (IOException ex) {
            Logger.getLogger(TestAUFormat.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static byte[] reduceAudio2(byte[] audio){
        ByteBuffer buffer = new ByteBuffer();
        boolean append = false;
        short b = -200;
        byte repCount = 0;
        buffer.add(audio[0]);
        
        for (int i = 1; i < audio.length; i++) {
            if (audio[i] != -1) {
                buffer.add((byte) (audio[i-1]-audio[i]));
            }
        }
        audio = buffer.drain();
        
        for (int i = 1; i < audio.length; i++) {
            buffer.add((byte) (audio[i]*4));
        }
        
        // Falto agregar el ultimo
        if (repCount > 0) {
            buffer.add((byte) b);
            buffer.add(repCount);
        }
        byte[] array = buffer.getArray();
        System.out.println(array.length);
        byte major = -128, minor = 127;
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i]+ "   ");
            if (array[i] > major) {
                major = array[i];
            }
            if (array[i] < minor) {
                minor = array[i];
            }
        }
        System.out.println("\nMayor: "+major);
        System.out.println("Menor: "+minor);
        System.out.println("Diferencia: "+(major-minor));
        System.out.println("------------");
        return buffer.getArray();
    }
    
}
