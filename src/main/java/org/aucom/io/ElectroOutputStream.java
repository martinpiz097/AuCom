/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.aucom.io;

import org.bytebuffer.ByteBuffer;

import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author martin
 */
public class ElectroOutputStream extends OutputStream {

    private final ElectroInputStream electroInput;
    private boolean closed;
    
    public ElectroOutputStream() {
        this(new ElectroInputStream());
    }

    public ElectroOutputStream(ElectroInputStream electroInput) {
        this.electroInput = electroInput;
        closed = false;
    }

    public ElectroInputStream getElectroInput() {
        return electroInput;
    }

    public ByteBuffer getListBytes() {
        return electroInput.getListBytes();
    }
    
    public byte[] toByteArray(){
        return electroInput.getListBytes().toArray();
    }
    
    public void clear(){
        electroInput.clear();
    }
    
    public int size(){
        try {
            return electroInput.available();
        } catch (IOException ex) {
            Logger.getLogger(ElectroOutputStream.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    
    @Override
    public void close() throws IOException {
        closed = true;
    }

    @Override
    public void flush() throws IOException {
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        //System.out.println("write largo");
        if (!closed)
            for (int i = off; i < len; i++)
                write(b[i]);
    }

    @Override
    public void write(byte[] b) throws IOException {
        //System.out.println("write medio");
        if (!closed) {
            for (int i = 0; i < b.length; i++)
                write(b[i]);
        }
    }

    @Override
    public void write(int b) throws IOException {
        //System.out.println("write");
        electroInput.addBytes((byte) b);
    }
    

}
