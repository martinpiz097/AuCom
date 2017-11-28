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
public class AudioQuality {
    public static final AudioFormat THE_BEST = new AudioFormat(
            AudioFormat.Encoding.PCM_SIGNED, 48000.0f, 16, 2, 2, 48000.0f, true);
    public static final AudioFormat HIGH = new AudioFormat(
            AudioFormat.Encoding.PCM_SIGNED, 44100.0f, 16, 2, 2, 44100.0f, true);
    public static final AudioFormat NORMAL = new AudioFormat(
            AudioFormat.Encoding.PCM_SIGNED, 22000.0f, 16, 1, 1, 22000.0f, true);
    public static final AudioFormat LOW = new AudioFormat(
            AudioFormat.Encoding.PCM_SIGNED, 11000.0f, 8, 1, 1, 11000.0f, true);
}
