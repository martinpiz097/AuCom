/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.aucommon.io;

import java.io.IOException;
import java.io.OutputStream;

/**
 *
 * @author martin
 */
public class ElectroOutputStream extends OutputStream {

    private final ElectroInputStream electroInput;
    private ByteBuffer listBytes = new ByteBuffer();
    
    public ElectroOutputStream() {
        electroInput = new ElectroInputStream();
        listBytes = electroInput.getListBytes();
    }

    public ElectroInputStream getElectroInput() {
        return electroInput;
    }

    @Override
    public void close() throws IOException {
        super.close(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void flush() throws IOException {
        super.flush(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        System.out.println("write largo");
        for (int i = off; i < len; i++)
            write(b[i]);
    }

    @Override
    public void write(byte[] b) throws IOException {
        System.out.println("write medio");
        for (int i = 0; i < b.length; i++)
            write(b[i]);
    }

    @Override
    public void write(int b) throws IOException {
        System.out.println("write");
        electroInput.addBytes((byte) b);
    }

}
