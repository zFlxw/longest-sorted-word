package com.github.zflxw;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class Main {
    public static void main(String[] args) throws FileNotFoundException, InterruptedException {
        File file = new File("./input.txt");
	    BufferedReader reader = new BufferedReader(new FileReader(file));
        ExecutorService service = Executors.newFixedThreadPool(10);

        AtomicReference<String> currentLongest = new AtomicReference<>("");

        System.out.println("Start analyzing");
        service.submit(() -> reader.lines().forEach(line -> {
            char[] chars = line.toLowerCase().toCharArray();
            Arrays.sort(chars);
            String l = new String(chars);

            if (l.equalsIgnoreCase(line)) {
                if (currentLongest.get().length() < l.length()) {
                    currentLongest.set(l);
                } else if (currentLongest.get().length() == l.length()) {
                    System.out.println(line);
                }
            }
        }));

        service.shutdown();
        service.awaitTermination(60, TimeUnit.MINUTES);

        System.out.println("Longest word: " + currentLongest.get());
    }
}
