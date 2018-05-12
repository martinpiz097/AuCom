/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.aucom.test;
import java.io.File;
import java.io.IOException;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javax.sound.sampled.LineUnavailableException;
import org.aucom.sound.AudioQuality;
import org.aucom.sound.Microphone;
import org.aucom.sound.Speaker;

/**
 *
 * @author martin
 */
public class Main {
    public static void main(String[] args) throws LineUnavailableException, IOException {
        File sound = new File("/home/martin/AudioTesting/music/John Petrucci/" +
                "When_The_Keyboard_Breaks_Live_In_Chicago/Universal_Mind.m4a");
        Media song = new Media(sound.toURI().toString());
        MediaPlayer player = new MediaPlayer(song);
        player.play();
        /*Microphone micro = new Microphone(AudioQuality.HIGH);
        Speaker speaker = new Speaker(AudioQuality.HIGH);
        micro.open();speaker.open();
        
//        AudioInputStream ais = micro.getInputStream();
//        File fSound = new File("/home/martin/AudioTesting/voice.wav");
//        fSound.createNewFile();
//        AudioSystem.write(ais, AudioFileFormat.Type.WAVE, fSound);
//        System.out.println("Despues de write");

        while (true)
            speaker.playAudio(micro.readAudio());
        
//        long time = System.currentTimeMillis();
//        LinkedList<byte[]> list = new LinkedList<>();
//        
//        while (System.currentTimeMillis() - time <= 5000)            
//            list.add(micro.readAudio(32));
//        
//        for (byte[] buffer : list)
//            System.out.println(Arrays.toString(buffer));*/
    }

}
