package org.aucommon.test;

///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package org.aucommon.test;
//
//import java.io.IOException;
//
///**
// *
// * @author martin
// */
//public class TestBytesCodes {
//    public static void main(String[] args) throws IOException {
//        System.out.println("Combinaciones posibles de bytes: "+(256*256));
//        
//        // 8 para los positivos y 16 para los negativos
////        for (int i = -128; i < 256; i++) {
////            System.out.println(i+": Largo binario: "+Long.toBinaryString(i).length() );
////        }
//
//        String hex;
//        int counter = 0;
//        for (int i = 0; i < 256; i++) {
//            counter+=reducedBin(Long.toBinaryString(i)).length();
//        }
//        System.out.println(counter/4);
//        
////        File file = new File("/home/martin/xd.txt");
////        FileOutputStream fos = new FileOutputStream(file);
////        fos.write(Integer.MAX_VALUE);
////        fos.flush();
////        fos.close();
////        FileInputStream fis = new FileInputStream(file);
////        System.out.println(fis.read());
////        fis.close();
//    }
//
//    public static String reducedBin(String binary){
//        StringBuilder sbBin = new StringBuilder();
//        boolean start = false;
//        char[] chars = binary.toCharArray();
//        for (int i = 0; i < chars.length; i++) {
//            if (chars[i] == '1')
//                start = true;
//            if (start) {
//                sbBin.append(chars[i]);
//            }
//        }
//        return sbBin.toString();
//    }
//    
//}
