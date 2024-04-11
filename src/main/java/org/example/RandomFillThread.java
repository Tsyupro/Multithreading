package org.example;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

class RandomFillThread extends Thread {
    private String filePath;

    public RandomFillThread(String filePath) {
        this.filePath = filePath;
    }

    public void run() {
        try (FileWriter fileWriter = new FileWriter(filePath);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
            Random random = new Random();
            for (int i = 0; i < 10; i++) {
                bufferedWriter.write(Integer.toString(random.nextInt(100)) + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
