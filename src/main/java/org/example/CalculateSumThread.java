package org.example;

public class CalculateSumThread extends Thread{
    private int [] array;
    private int sum;

    public CalculateSumThread(int [] array) {
        this.array = array;
    }
    public void run() {
        for (int i : array) {
            sum+=i;
        }
    }
    public int getSum() {
        return sum;
    }
}
