/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.aucom.test;
import org.aucom.sound.AudioQuality;
import org.aucom.sound.Microphone;
import org.aucom.sound.Speaker;


import static javax.sound.sampled.FloatControl.Type;

import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;
import java.io.IOException;

/**
 *
 * @author martin
 */
public class Main {
    public static void main(String[] args) throws LineUnavailableException, IOException {
        /*File sound = new File("/home/martin/AudioTesting/audio2/au.mp3");
        Media song = new Media(sound.toURI().toString());
        MediaPlayer player = new MediaPlayer(song);
        player.play();*/
        Microphone micro = new Microphone(AudioQuality.VOICE);
        Speaker speaker = new Speaker(AudioQuality.VOICE);
        micro.open();
        speaker.open();

        SourceDataLine driver = speaker.getDriver();
        System.out.println("Soporte de controles en line");
        System.out.println("---------------");
        System.out.println("Pan: "+
                driver.isControlSupported(Type.PAN));

        System.out.println("AuxReturn: "+
                driver.isControlSupported(Type.AUX_RETURN));

        System.out.println("AuxSend: "+
                driver.isControlSupported(Type.AUX_SEND));

        System.out.println("Balance: "+
                driver.isControlSupported(Type.BALANCE));

        System.out.println("ReverbReturn: "+
                driver.isControlSupported(Type.REVERB_RETURN));

        System.out.println("ReberbSend: "+
                driver.isControlSupported(Type.REVERB_SEND));

        System.out.println("Volume: "+
                driver.isControlSupported(Type.VOLUME));

        System.out.println("SampleRate: "+
                driver.isControlSupported(Type.SAMPLE_RATE));

        System.out.println("MasterGain: "+
                driver.isControlSupported(Type.MASTER_GAIN));

        /*while (true)
            speaker.playAudio(micro.readAudio());
*/
//        AudioInputStream ais = micro.getInputStream();
//        File fSound = new File("/home/martin/AudioTesting/voice.wav");
//        fSound.createNewFile();
//        AudioSystem.write(ais, AudioFileFormat.Type.WAVE, fSound);
//        System.out.println("Despues de write");

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
