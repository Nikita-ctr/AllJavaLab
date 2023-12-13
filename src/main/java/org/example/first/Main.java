package org.example.first;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        System.out.println("Enter your name: ");
        String name = scn.nextLine();
        System.out.println("Hello " + name);

        System.out.println("Enter numbers amount: ");
        int n = scn.nextInt();
        System.out.println("Enter numbers: ");
        int[] arr = new int[n];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = scn.nextInt();
        }

        for (int i = 0; i < arr.length; i++) {
            if (arr[i] % 5 == 0 && arr[i] % 7 == 0) {
                System.out.println(arr[i]);
            }
        }
    }
}