/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.aucommon.io;

import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author martin
 */
public class ElectroInputStream extends InputStream{

    private final ByteBuffer buffer;
    private int regexRead;

    public ElectroInputStream() {
        buffer = new ByteBuffer(10240);
        regexRead = 0;
    }

    public ByteBuffer getListBytes() {
        return buffer;
    }
    
    void addBytes(byte b){
        buffer.add(b);
    }
    
//    public byte[] readBytes(){
//        return readBytes(1024);
//    }
//    
//    public byte[] readBytes(int len){
//        int listSize = listBytes.size();
//        byte[] buff = new byte[len > listSize ? listSize : len];
//        int count = 0;
//        int i = 0;
//        for (Byte b : listBytes){
//            if (count >= regexRead)
//                buff[i++] = b;
//            count++;
//            if (count == buff.length)
//                break;
//        }
//        regexRead+=buff.length;
//        if (regexRead >=listBytes.size())
//            regexRead =  0;
//        if (listBytes.size() >= 1024*1024) {
//            listBytes.clear();
//            regexRead = 0;
//        }
//        return buff;
//    }

    @Override
    public int available() throws IOException {
        return buffer.size();
    }
    
    public boolean hasBytes(){
        return !buffer.isEmpty();
    }
    
    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        System.out.println("read largo");
        if (!hasBytes())
            return -1;
        return buffer.read(b, off, len);
    }

    @Override
    public int read(byte[] b) throws IOException {
        System.out.println("read medio");
        if (!hasBytes())
            return -1;
        return buffer.read(b, b.length);
    }

    @Override
    public int read() throws IOException {
        System.out.println("read");
        return buffer.isEmpty() ? -1 : buffer.pollFirst();
    }
    
}
