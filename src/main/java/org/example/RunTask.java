package org.example;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public abstract class RunTask {
    public static void StartTask1(){
        int[] array = new int[10];
        FillArrayThread fillArrayThread = new FillArrayThread(array);
        CalculateSumThread calculateSumThread = new CalculateSumThread(array);
        CalculateAverageThread calculateAverageThread = new CalculateAverageThread(array);

        fillArrayThread.start();

        synchronized (fillArrayThread) {
            try {
                fillArrayThread.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        calculateSumThread.start();
        calculateAverageThread.start();

        try {
            calculateSumThread.join();
            calculateAverageThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        int sum = calculateSumThread.getSum();
        double average = calculateAverageThread.getAverage();

        System.out.println("Масив:");
        for (int num : array) {
            System.out.print(num + " ");
        }
        System.out.println("\nСума елементів: " + sum);
        System.out.println("Середнє арифметичне: " + average);
    }
    public static void StartTask2(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введіть шлях до файлу:");
        String filePath = scanner.nextLine();
        scanner.close();

        String primesFilePath = "primes.txt";
        String factorialsFilePath = "factorials.txt";

        // Створення та запуск потоків
        RandomFillThread randomFillThread = new RandomFillThread(filePath);
        PrimeNumbersThread primeNumbersThread = new PrimeNumbersThread(filePath, primesFilePath);
        FactorialsThread factorialsThread = new FactorialsThread(filePath, factorialsFilePath);

        randomFillThread.start();

        try {
            randomFillThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        primeNumbersThread.start();
        factorialsThread.start();

        try {
            primeNumbersThread.join();
            factorialsThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Статистика:");
        System.out.println("Прості числа записані у файл " + primesFilePath);
        printFileContent(primesFilePath);
        System.out.println("Факторіали записані у файл " + factorialsFilePath);
        printFileContent(factorialsFilePath);
    }
    private static void printFileContent(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void StartTask3(){
        Scanner scanner = new Scanner(System.in);

        // Запит користувача на введення шляхів до директорій
        System.out.println("Введіть шлях до існуючої директорії:");
        String sourceDirPath = scanner.nextLine();
        System.out.println("Введіть шлях до нової директорії:");
        String destinationDirPath = scanner.nextLine();

        // Створення об'єктів директорій за введеними шляхами
        File sourceDir = new File(sourceDirPath);
        File destinationDir = new File(destinationDirPath);

        // Перевірка, чи існують введені директорії
        if (!sourceDir.exists() || !destinationDir.exists()) {
            System.out.println("Одна з введених директорій не існує.");
            return;
        }

        // Створення та запуск потоку копіювання директорії
        CopyDirectoryThread copyThread = new CopyDirectoryThread(sourceDir, destinationDir);
        copyThread.start();

        try {
            copyThread.join();
            System.out.println("Копіювання завершено.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        scanner.close();
    }
    public static void StartTask4(){
        Scanner scanner = new Scanner(System.in);

        // Запит користувача на введення шляху до директорії та слова для пошуку
        System.out.println("Введіть шлях до директорії:");
        String directoryPath = scanner.nextLine();
        System.out.println("Введіть слово для пошуку:");
        String searchWord = scanner.nextLine();

        // Створення файлу для результатів злиття та файлу з вирізаними забороненими словами
        String mergedFilePath = "merged.txt";
        String censoredFilePath = "censored.txt";

        // Створення та запуск потоків
        SearchAndMergeThread searchAndMergeThread = new SearchAndMergeThread(directoryPath, searchWord, mergedFilePath);
        CensorThread censorThread = new CensorThread(mergedFilePath, censoredFilePath, "censored_words.txt");

        searchAndMergeThread.start();

        try {
            searchAndMergeThread.join();
            System.out.println("Злиття файлів завершено.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        censorThread.start();

        try {
            censorThread.join();
            System.out.println("Цензура завершена.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Відображення статистики
        System.out.println("Статистика:");
        System.out.println("Злиття файлів: " + mergedFilePath);
        System.out.println("Цензурований файл: " + censoredFilePath);

        scanner.close();
    }
}
