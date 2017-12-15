/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.aucommon.io;

import java.io.Serializable;
import java.util.Arrays;
import java.util.RandomAccess;
import java.util.function.Predicate;

/**
 *
 * @author martin
 */
public class ByteBuffer implements RandomAccess, Cloneable, Serializable {
    private byte[] array;
    private int size;
    private int capacity;
    
    private static final int DEFAULT_CAPACITY = 1024;
    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE-8;
    
    public ByteBuffer() {
        this(DEFAULT_CAPACITY);
    }

    public ByteBuffer(int capacity) {
        if (capacity <= DEFAULT_CAPACITY)
            array = new byte[DEFAULT_CAPACITY];
        else
            array = new byte[capacity];
        size = 0;
        this.capacity = array.length;
    }

    public ByteBuffer(byte[] array) {
        this.array = array;
        this.size = this.capacity = array.length;
    }
    
    private void updateCapacity(){
        capacity = array.length;
    }
    
    private byte[] createArray(int size){
        return new byte[size];
    }
    
    private void grow(int minCapacity){
        int newCapacity = capacity + (capacity >> 1);
        if (newCapacity - minCapacity < 0)
            newCapacity = minCapacity;
        if (newCapacity - MAX_ARRAY_SIZE > 0)
            newCapacity = Integer.MAX_VALUE;
        array = Arrays.copyOf(array, newCapacity);
        capacity = newCapacity;
    }

    // minCapacity --> la capacidad minima que debe contener
    // la lista, ya que, debe ser por lo menos size+1 para tener
    // espacios para más elementos.
    // capacity --> capacidad del arreglo(lenght).
    private void ensureCapacity(int minCapacity) {
        // si el size+1 es mayor que la capacidad del arreglo
        // si debe agrandar el array.
        if (minCapacity > capacity)
            // Le entrego el size+1
            grow(minCapacity);
    }
    
    private static int hugeCapacity(int minCapacity) {
        if (minCapacity < 0) // overflow
            throw new OutOfMemoryError();
        return (minCapacity > MAX_ARRAY_SIZE) ?
            Integer.MAX_VALUE :
            MAX_ARRAY_SIZE;
    }
    
    private void checkIndex(int index){
        if(index >= size || index < 0)
            throw new IndexOutOfBoundsException(index+"");
    }

    private void checkCapacity(){
        if (size+1 > capacity)
            grow(size+1);
    }

    private void checkMemory(){
        long freeMem = Runtime.getRuntime().freeMemory();
        long maxMemory = Runtime.getRuntime().maxMemory();
        
        if (maxMemory - freeMem <= 80000000)
            System.gc();
    }
    
    public boolean anyMatch(Predicate<Byte> predicate){
        for (int i = 0; i < size; i++)
            if (predicate.test(array[i]))
                return true;
        return false;
    }
    
    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public byte[] toArray() {
        byte[] buffCopy = new byte[size];
        System.arraycopy(array, 0, buffCopy, 0, size);
        return buffCopy;
    }

    public byte[] drain(){
        byte[] bytes = toArray();
        array = new byte[DEFAULT_CAPACITY];
        size = 0;
        return bytes;
    }

    // Ambos inclusivos
    public void removeAt(int start, int end){
        ByteBuffer newBuff = new ByteBuffer();
        for (int i = 0; i < start; i++)
            newBuff.add(array[i]);
        for (int i = end+1; i < size; i++)
            newBuff.add(array[i]);
        array = newBuff.array;
    }
    
    public int read(byte[] b, int off, int len){
//        if (off >= len || off >= array.length || len > array.length)
//            throw new IndexOutOfBoundsException();
        if (len > b.length)
            len = b.length;
        for (int i = 0; i < len; i++)
            b[i] = array[i+off];
        return len-off;
    }
    
    public int read(byte[] b, int len){
        if (len > array.length)
            len = array.length;
        System.arraycopy(array, 0, b, 0, len);
        return len;
    }
    
    public byte[] getArray(){
        return array;
    }
    
    public boolean add(byte e) {
        ensureCapacity(size+1);
        array[size] = e;
        size++;
        //checkMemory();
        return true;
    }

    public void clear() {
        array = null;
        array = new byte[DEFAULT_CAPACITY];
        size = 0;
    }

    /**
     * Devuelve el primer elemento de la lista, si esta vacía 
     * se devuelve null.
     * @return Primer elemento de la lista.
     */
    
    public byte getFirst(){
        checkIndex(0);
        return array[0];
    }
    
    public byte pollFirst(){
        checkIndex(0);
        byte b = array[0];
        byte[] newarray = new byte[array.length];
        for (int i = 1; i < size; i++) {
            newarray[i-1] = array[i];
        }
        return b;
    }
    
     /**
     * Devuelve el último elemento de la lista, si esta vacía 
     * se devuelve null.
     * @return Último elemento de la lista.
     */
    
    public byte getLast(){
        checkIndex(size-1);
        return array[size-1];
    }
    
    public byte get(int index) {
        checkIndex(index);
        return array[index];
    }

    public byte set(int index, byte element) {
        checkIndex(index);
        byte e = get(index);
        array[index] = element;
        return e;
    }
    
}
