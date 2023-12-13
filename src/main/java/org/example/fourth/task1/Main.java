package org.example.fourth.task1;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String filePath = "input.txt";

        List<String> lines = readLinesFromFile(filePath);
        System.out.println("Number of elements in the list: " + lines.size());

        Collections.reverse(lines);
        String outputFilePath = "output.txt";
        writeLinesToFile(lines, outputFilePath);

        System.out.println("Lines have been written to the file in reverse order.");
    }

    private static List<String> readLinesFromFile(String filePath) {
        List<String> lines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading from the file: " + e.getMessage());
        }

        return lines;
    }

    private static void writeLinesToFile(List<String> lines, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing to the file: " + e.getMessage());
        }
    }
}