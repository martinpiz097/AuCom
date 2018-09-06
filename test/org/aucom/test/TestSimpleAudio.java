/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.aucom.test;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;
import org.aucom.sound.AudioQuality;

/**
 *
 * @author martin
 */
public class TestSimpleAudio {
    public static void main(String[] args) throws LineUnavailableException {
        AudioFormat format = AudioQuality.LOW;
        TargetDataLine microphone = (TargetDataLine)
                AudioSystem.getLine(new DataLine.Info(TargetDataLine.class, format));
        SourceDataLine speaker = (SourceDataLine)
                AudioSystem.getLine(new DataLine.Info(SourceDataLine.class, format));
        microphone.open();
        microphone.start();
        speaker.open();
        speaker.start();
        byte[] buff = new byte[512];
        while (true) {
            microphone.read(buff, 0, buff.length);
            speaker.write(buff, 0, buff.length);
        }
    }
}
