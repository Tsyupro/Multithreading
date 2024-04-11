package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

class CensorThread extends Thread {
    private String sourceFilePath;
    private String destinationFilePath;
    private String forbiddenWordsFilePath;

    public CensorThread(String sourceFilePath, String destinationFilePath, String forbiddenWordsFilePath) {
        this.sourceFilePath = sourceFilePath;
        this.destinationFilePath = destinationFilePath;
        this.forbiddenWordsFilePath = forbiddenWordsFilePath;
    }

    @Override
    public void run() {
        try (BufferedReader reader = new BufferedReader(new FileReader(sourceFilePath));
             PrintWriter writer = new PrintWriter(new FileWriter(destinationFilePath))) {
            List<String> forbiddenWords = readForbiddenWords();
            String line;
            while ((line = reader.readLine()) != null) {
                for (String word : forbiddenWords) {
                    line = line.replaceAll("\\b" + word + "\\b", "*".repeat(word.length()));
                }
                writer.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<String> readForbiddenWords() {
        List<String> forbiddenWords = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(forbiddenWordsFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                forbiddenWords.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return forbiddenWords;
    }
}
