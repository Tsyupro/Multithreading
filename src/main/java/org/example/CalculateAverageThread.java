package org.example;

public class CalculateAverageThread extends Thread{
    private int[] array;
    private double average;

    public CalculateAverageThread(int[] array) {
        this.array = array;
    }

    public void run(){
        double sum=0;
        for (int i : array) {
            sum+=i;
        }
        average = sum/array.length;
    }
    public double getAverage(){
        return average;
    }
}
