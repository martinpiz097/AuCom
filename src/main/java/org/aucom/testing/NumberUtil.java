package org.aucom.testing;

import java.nio.ByteBuffer;

public class NumberUtil {

    private static final byte MAX_DECIMALS = 20;
    private static final long divisor = (long) Math.pow(10, MAX_DECIMALS);

    public static long bytesToLong(byte[] bytes) {
        return ByteBuffer.wrap(bytes).getLong();

        /*return (((long)bytes[0] << 56) +
                ((long)(bytes[1] & 255) << 48) +
                ((long)(bytes[2] & 255) << 40) +
                ((long)(bytes[3] & 255) << 32) +
                ((long)(bytes[4] & 255) << 24) +
                ((bytes[5] & 255) << 16) +
                ((bytes[6] & 255) <<  8) +
                ((bytes[7] & 255) <<  0));*/
    }

    public static byte[] longToBytes(long value) {
        return ByteBuffer.allocate(8).putLong(value)
                .array();
        /*byte[] bytes = new byte[8];
        bytes[0] = (byte) ((value >>> 56) & 0xFF);
        bytes[1] = (byte) ((value >>> 48) & 0xFF);
        bytes[2] = (byte) ((value >>> 40) & 0xFF);
        bytes[3] = (byte) ((value >>> 32) & 0xFF);
        bytes[4] = (byte) ((value >>> 24) & 0xFF);
        bytes[5] = (byte) ((value >>> 16) & 0xFF);
        bytes[6] = (byte) ((value >>> 8) & 0xFF);
        bytes[7] = (byte) ((value >>> 0) & 0xFF);
        return bytes;*/
    }

    public static int bytesToInt(byte[] bytes) {
        return ByteBuffer.wrap(bytes)
                .getInt();
        /*byte b0 = bytes[0];
        byte b1 = bytes[1];
        byte b2 = bytes[2];
        byte b3 = bytes[3];
        //System.out.println("Input Values: "+b0+"|"+b1+"|"+b2+"|"+b3);

        int v0 = toUnsignedInt(bytes[0]) << 24;
        int v1 = toUnsignedInt(bytes[1]) << 16;
        int v2 = toUnsignedInt(bytes[2]) << 8;
        int v3 = toUnsignedInt(bytes[3]) << 0;

        //System.out.println("Output Values: "+v0+"|"+v1+"|"+v2+"|"+v3);
        return v0+v1+v2+v3;*/
    }

    public static byte[] intToBytes(int value) {
        return ByteBuffer.allocate(4).putInt(value)
                .array();
        /*byte[] bytes = new byte[4];
        bytes[0] = (byte) ((value >>> 24) & 0xFF);
        bytes[1] = (byte) ((value >>> 16) & 0xFF);
        bytes[2] = (byte) ((value >>> 8) & 0xFF);
        bytes[3] = (byte) ((value) & 0xFF);

        /*for (int i = 0; i < bytes.length; i++)
            if (bytes[i] == -1)
                bytes[i] = 0;
        return bytes;*/
    }

    public static short bytesToShort(byte[] bytes) {
        return ByteBuffer.wrap(bytes).getShort();
    }

    public static byte[] shortToBytes(short value) {
        return ByteBuffer.allocate(2).putShort(value)
                .array();
    }

    public static float bytesToFloat(byte[] bytes) {
        return ByteBuffer.wrap(bytes).getFloat();
        //return Float.intBitsToFloat(bytesToInt(bytes));
    }

    public static byte[] floatToBytes(float value) {
        return ByteBuffer.allocate(4).putFloat(value)
                .array();
        //return intToBytes(Float.floatToIntBits(value));
    }

    /*public static double getFloatLost(String floatString) {
        double doubleValue = Double.parseDouble(floatString);
        float floatValue = Float.parseFloat(floatString);
        double diff = ((doubleValue-floatValue))*divisor;

        //System.out.println("Diff: "+diff);
        //double eDiff = diff < 0 ? Math.sqrt(-diff) : Math.sqrt(diff);
        //System.out.println("E/Diff: "+eDiff);
        //System.out.println("FloatEdiff: "+((float)eDiff));

        //System.out.println("Value: "+floatValue);
        //float hypot = Float.MAX_VALUE-floatValue;
        //long lo = floatToLong(hypot);

        //System.out.println(hypot);
        //System.out.println(lo);
        //System.out.println(hypot == lo);
        //System.exit(0);
        return diff;
    }*/

    public static float longToFloat(long value) {
        return value;
    }

    public static long floatToLong(float value) {
        return (long) value;
    }

    public static byte[] encodeLong(long value) {
        return floatToBytes(longToFloat(value));
    }

    public static byte[] encodeLongBytes(byte[] bytes) {
        return encodeLong(bytesToLong(bytes));
    }

    public static byte[] decodeLong(float value) {
        return longToBytes(floatToLong(value));
    }

    public static byte[] decodeLongBytes(byte[] bytes) {
        return decodeLong(bytesToFloat(bytes));
    }

}
