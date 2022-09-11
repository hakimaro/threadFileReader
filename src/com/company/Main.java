package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        String[] files = {"Hello.txt", "World.txt", "HowAreYou.txt"};
        Scanner in = new Scanner(System.in);
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter count of files: ");
        String[] names = new String[in.nextInt()];
        for (int i = 0; i < names.length; i++) {
            System.out.println("Enter name of file: ");
            names[i] = reader.readLine();
        }
        FileThreads ft = new FileThreads(names);
        System.out.println(ft);
    }
}
