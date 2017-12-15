///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package org.aucommon.test;
//
//import java.util.ArrayList;
//import javax.sound.sampled.LineUnavailableException;
//import org.aucommon.sound.AudioInfo;
//import org.aucommon.sound.Microphone;
//import org.aucommon.sound.Speaker;
//
///**
// *
// * @author martin
// */
//public class Main {
//    public static void main(String[] args) throws LineUnavailableException {
//        Microphone micro = new Microphone();
//        Speaker speaker = new Speaker();
//        micro.open();speaker.open();
//
//        int byteCounter = 0;
//        long ti = System.currentTimeMillis();
//        while (true) {            
//            speaker.playAudio(micro.readAudio());
//            byteCounter+=AudioInfo.BUFF_SIZE/1024;
//            if (System.currentTimeMillis()-ti >= 1000) {
//                System.out.print(" \r"+byteCounter+" Kbps");
//                ti = System.currentTimeMillis();
//                byteCounter = 0;
//            }
//        }
//    }
//    
//    public static byte[] compress(byte[] audio){
//        ArrayList<Byte> listBytes = new ArrayList<>();
//        byte b;
//        for (int i = 0; i < audio.length; i++) {
//            b = audio[i];
////            if (b != 128 && b °=)
////                listBytes.add(b);
//            if (i % 16 != 0 || i == 0) {
//                listBytes.add(b);
//            }
//            System.out.print(b+"-");
//        }
//        System.out.println("");
//        byte[] buffer = new byte[listBytes.size()];
//        for (int i = 0; i < listBytes.size(); i++)
//            buffer[i] = listBytes.get(i);
//        return buffer;
//    }
//    
//    
//}
