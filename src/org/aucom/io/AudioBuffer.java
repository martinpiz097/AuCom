/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.aucom.io;

/**
 *
 * @author martin
 */
public class AudioBuffer extends ByteBuffer {

    public AudioBuffer() {
        super();
    }

    public AudioBuffer(int capacity) {
        super(capacity);
    }

    public AudioBuffer(byte[] array) {
        super(array);
    }

    public boolean isValidVoice(){
        byte[] auBytes = getRawArray();
        byte b;
        for (int i = 0; i < auBytes.length; i++) {
            b = auBytes[i];
            if (b != 0 && b != -1)
                return true;
        }
        return false;
    }
}
