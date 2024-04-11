package org.example;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

class FactorialsThread extends Thread {
    private String inputFilePath;
    private String outputFilePath;

    public FactorialsThread(String inputFilePath, String outputFilePath) {
        this.inputFilePath = inputFilePath;
        this.outputFilePath = outputFilePath;
    }

    public void run() {
        try (Scanner scanner = new Scanner(new File(inputFilePath));
             FileWriter fileWriter = new FileWriter(outputFilePath);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
            while (scanner.hasNextLine()) {
                int num = Integer.parseInt(scanner.nextLine());
                long factorial = computeFactorial(num);
                bufferedWriter.write(Long.toString(factorial) + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private long computeFactorial(int num) {
        if (num == 0 || num == 1) {
            return 1;
        }
        long factorial = 1;
        for (int i = 2; i <= num; i++) {
            factorial *= i;
        }
        return factorial;
    }
}
