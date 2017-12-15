/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.aucommon.sound;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.LineUnavailableException;

/**
 *
 * @author martin
 */
public abstract class AudioInterface {

    public AudioInterface() {}
    
    public AudioInterface(AudioFormat quality) {}
    
    public abstract void configure(AudioFormat format) throws LineUnavailableException;
    public abstract boolean isOpen();
    public abstract void open() throws LineUnavailableException;
    public abstract void stop();
}
