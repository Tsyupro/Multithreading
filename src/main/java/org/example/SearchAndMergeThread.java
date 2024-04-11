package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

class SearchAndMergeThread extends Thread {
    private String directoryPath;
    private String searchWord;
    private String mergedFilePath;

    public SearchAndMergeThread(String directoryPath, String searchWord, String mergedFilePath) {
        this.directoryPath = directoryPath;
        this.searchWord = searchWord;
        this.mergedFilePath = mergedFilePath;
    }

    @Override
    public void run() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(mergedFilePath))) {
            // Пошук файлів, які містять задане слово та їх злиття
            File directory = new File(directoryPath);
            List<File> filesContainingWord = searchFiles(directory, searchWord);
            for (File file : filesContainingWord) {
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        writer.println(line);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<File> searchFiles(File directory, String searchWord) {
        List<File> result = new ArrayList<>();
        if (directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        result.addAll(searchFiles(file, searchWord));
                    } else if (file.isFile() && containsWord(file, searchWord)) {
                        result.add(file);
                    }
                }
            }
        }
        return result;
    }

    private boolean containsWord(File file, String searchWord) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains(searchWord)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}

