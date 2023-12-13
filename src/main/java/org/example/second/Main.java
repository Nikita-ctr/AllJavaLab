package org.example.second;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
/**
 * This program sorts words from a file in alphabetical
 * order and prints all numbers from 1 to
 * 100 that are divisible by 3.
 */
public class Main {
    public static void main(String[] args) throws IOException {
        //Читаем каждую строку файла
        BufferedReader reader = new BufferedReader(new FileReader("text.txt"));
        StringBuilder sb = new StringBuilder();
        while (reader.ready()) {
            sb.append(reader.readLine());
        }

        String[] words = sb.toString().split(",");

        for (int i = 0; i < words.length; i++) {
            for (int j = i + 1; j < words.length; j++) {
                if (words[i].compareToIgnoreCase(words[j]) > 0) {
                    String temp = words[i];
                    words[i] = words[j];
                    words[j] = temp;
                }
            }
        }
        System.out.println("Result for first task: ");
        System.out.println(Arrays.toString(words));

        System.out.println("Result for second task: ");
        for (int i = 1; i <= 100; i++) {
            if (i % 3 == 0) {
                System.out.println(i);
            }
        }
        System.out.println("Developed by: Anna Chernetskaya");
        System.out.println("Completion date and time: " + LocalDateTime.now());
    }
}
