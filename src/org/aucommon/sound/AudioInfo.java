/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.aucommon.sound;

import javax.sound.sampled.AudioFormat;

/**
 *
 * @author martin
 */
public class AudioInfo {
            
    public static final AudioFormat DEFAULT_FORMAT = new AudioFormat(
            AudioFormat.Encoding.PCM_SIGNED, 12288.0f, 8, 1, 1, 16000.0f, true);
            //16000.0f, 8, 1, true, true);
    public static final short BUFF_SIZE = 1024;
    
//    static {
//        System.out.println("SampleRate: "+FORMAT.getSampleRate());
//        System.out.println("SampleSize: "+FORMAT.getSampleSizeInBits());
//        System.out.println("FrameRate: "+FORMAT.getFrameRate());
//        System.out.println("FrameSize: "+FORMAT.getFrameSize());
//        System.exit(0);
//    }
    
}
