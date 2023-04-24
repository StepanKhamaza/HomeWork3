package ru.urfu;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;


/**
 * Для подсчета слов будем использовать HashMap, ключ - слово, значение - количество этого слова в тексте.
 * Чтобы найти 10 наиболее и наименее используемых слов я выбрал структуру данных PriorityQueue.
 * Будем хранить две очереди с приоритетом размера 10 и компаратором, который будем сортировать слова по возрастанию и по убыванию.
 * Таким образом в очереди с 10 наиболее используемыми словами на вершине будет слово, которое реже всего встречается в тексте, а
 * в очереди с 10 наименее используемыми словами наоборот.
 *
 * Для хранения в очереди напишем класс Pair - будет хранить слово и его количество.
 * Переопределим методы hashCode() и equals() для корректного удаления объекта из очереди.
 *
 * Далее будем подчитывать каждое слово и добавлять в очереди, сортируя заданным образом.
 * Если размер больше 10, то удаляем значение из вершины очереди.
 * Данная структура данных подходит, т.к. она позволяет сразу сортировать объекты в нужном нам порядке.
 * Сложность: O(n) n - количество слов в тексте.
 * Обоснование:
 * remove(Object) для очереди с приоритетом выполняется за O(n),
 * add(Object) выполняется за O(log n),
 * poll() выполняется за O(log n)
 * где n - количество объектов в очереди.
 * И так как n == 10, то можно считать что все операции выполняются за константу O(1).
 *
 * Отсюда получаем линейную сложность для всей программы.
 */
public class CountWords {
    static class Pair {
        String value;
        Integer count;

        public Pair(String value, Integer count) {
            this.value = value;
            this.count = count;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Pair pair = (Pair) o;
            return Objects.equals(value, pair.value) && Objects.equals(count, pair.count);
        }

        @Override
        public int hashCode() {
            return Objects.hash(value, count);
        }
    }
    public static void normalizedQueues(PriorityQueue<Pair> topTenWords, PriorityQueue<Pair> lastTenWords, Pair pair) {
        topTenWords.remove(pair);
        lastTenWords.remove(pair);

        pair.count++;
        topTenWords.add(pair);
        lastTenWords.add(pair);

        if (topTenWords.size() > 10)
            topTenWords.poll();
        if (lastTenWords.size() > 10)
            lastTenWords.poll();
    }
    public static void main(String[] args) {
        StringBuilder current = new StringBuilder();
        Map<String, Integer> countWords = new HashMap<>();
        PriorityQueue<Pair> topTenWords = new PriorityQueue<>(10, (o1, o2) -> o1.count - o2.count);
        PriorityQueue<Pair> lastTenWords = new PriorityQueue<>(10, (o1, o2) -> o2.count - o1.count);
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
                        Pair curPair = new Pair(string, count);

                        normalizedQueues(topTenWords, lastTenWords, curPair);

                        countWords.put(string, count + 1);
                        current = new StringBuilder();
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Top 10 words");
        while (!topTenWords.isEmpty()) {
            Pair pair = topTenWords.poll();
            System.out.println(pair.value + ": " + pair.count);
        }
        System.out.println("Last 10 words");
        while (!lastTenWords.isEmpty()) {
            Pair pair = lastTenWords.poll();
            System.out.println(pair.value + ": " + pair.count);
        }
    }
}