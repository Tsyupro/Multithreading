package org.example;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class PrimeNumbersThread extends Thread {
    private String inputFilePath;
    private String outputFilePath;

    public PrimeNumbersThread(String inputFilePath, String outputFilePath) {
        this.inputFilePath = inputFilePath;
        this.outputFilePath = outputFilePath;
    }

    public void run() {
        List<Integer> primes = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(inputFilePath));
             FileWriter fileWriter = new FileWriter(outputFilePath);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
            while (scanner.hasNextLine()) {
                int num = Integer.parseInt(scanner.nextLine());
                if (isPrime(num)) {
                    primes.add(num);
                }
            }
            for (int prime : primes) {
                bufferedWriter.write(Integer.toString(prime) + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isPrime(int num) {
        if (num <= 1) {
            return false;
        }
        for (int i = 2; i <= Math.sqrt(num); i++) {
            if (num % i == 0) {
                return false;
            }
        }
        return true;
    }
}

