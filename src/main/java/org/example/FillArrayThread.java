package org.example;

import java.util.Random;

public class FillArrayThread extends Thread {
    private int[] array;

    public FillArrayThread(int[] array) {
        this.array = array;
    }
    public void run(){
        Random random = new Random();
        for(int i=0; i<array.length; i++){
            array[i] = random.nextInt(100);
        }
        synchronized(this){
            notifyAll();
        }
    }
}
