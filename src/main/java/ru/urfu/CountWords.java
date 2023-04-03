package ru.urfu;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;


public class CountWords {
    public static void main(String[] args) {
        StringBuilder current = new StringBuilder();
        Map<String, Integer> countWords = new LinkedHashMap<>();
        int symbol;

        try (BufferedReader bufferedReader = new BufferedReader
                (new InputStreamReader(new FileInputStream("Лев_Толстой_Война_и_мир_Том_1,_2,_3,_4_(UTF-8).txt"), StandardCharsets.UTF_8))) {
            while ((symbol = bufferedReader.read()) != -1) {
                char ch = (char) symbol;
                if (Character.UnicodeBlock.of(ch) == Character.UnicodeBlock.CYRILLIC) {
                    current.append(ch);
                } else {
                    if (!current.isEmpty()) {
                        String string = current.toString().toLowerCase();
                        int count = countWords.getOrDefault(string, 0);
                        countWords.put(string, count + 1);
                        current = new StringBuilder();
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        List<Map.Entry<String, Integer>> sortedWords = countWords.entrySet()
                .stream().sorted(Map.Entry.comparingByValue())
                .toList();

        System.out.println("Top 10 words");
        printList(sortedWords, sortedWords.size() - 1, Math.max(0, sortedWords.size() - 11));
        System.out.println("Last 10 words");
        printList(sortedWords, 0, Math.min(sortedWords.size(), 10));
    }
    public static void printList(List<Map.Entry<String, Integer>> list, int left, int right) {
        int step = right - left > 0 ? 1 : -1;
        for (int i = left; i != right; i += step) {
            Map.Entry<String, Integer> entry = list.get(i);
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        System.out.println();
    }
}