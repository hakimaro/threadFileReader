package com.company;

import java.io.*;
import java.util.HashMap;

public class FileThreads {
    private final HashMap<String, Integer> count = new HashMap<>();

    public FileThreads(String[] name) throws FileNotFoundException, InterruptedException {
        FileThreadReader[] threads = new FileThreadReader[name.length];

        for(int i = 0; i < name.length; i++) {
            threads[i] = new FileThreadReader(new File(name[i]));
            threads[i].start();
        }
        for(int i = 0; i < name.length; i++) {
            threads[i].join();
        }
    }

    public class FileThreadReader extends Thread {
        private final BufferedReader reader;

        FileThreadReader(File file) throws FileNotFoundException {
            reader = new BufferedReader(new FileReader(file));
        }

        @Override
        public void run() {
            try {
                StringBuilder line = new StringBuilder();
                line.append(reader.readLine()); // Получили первую строку
                while (!line.isEmpty()) {
                    int end = 0; // конец слова
                    int start = 0; // начало слова
                    while(end < line.length()) {
                        if (!isLet(line.charAt(end))) {
                            if (isLet(line.charAt(end-1))) {
                                synchronized (count) {
                                    count.merge(line.substring(start, end), 1, Integer::sum);
                                    start = end + 1;
                                }
                            } else {
                                start++;
                            }
                        } else if (isLet(line.charAt(end)) && end == line.length()-1) {
                            synchronized (count) {
                                count.merge(line.substring(start, end+1), 1, Integer::sum);
                            }
                        }
                        end++;
                    }
                    line.delete(0, line.length());
                    String read = reader.readLine();
                    if (read != null) line.append(read);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isLet(char let) {
        return let >= 65 && let <= 90 || let >= 97 && let <= 122;
    }

    @Override
    public String toString() {
        return count.toString();
    }
}
