package ru.urfu;

import java.util.*;

/**
 * Для хранения книг будем использовать 3 структуры данных
 * по одной для каждого из полей по которым будет производиться поиск
 *
 * добавление книги addBook() - сложность равна O(1), добавление по году издания происходит в конец массива,
 * что также равно O(1)
 *
 * удаление книги removeBook() - средняя сложность равна O(1), исключением является удаление из массива,
 * там сложность будет от O(1) до O(n) где n - количество книг определенного года издания
 *
 * методы searchByName() и searchByAuthor имеют сложность выполнения O(1),
 * так как поиск элемента в HashMap работает за O(1)
 * (по ключу вычисляется хэш, по ней определяется позиция хранения данной пары ключ-значения)
 *
 * поиск книг из интервала getBooksFromInterval() - сложность O(n), где n - количество всех книг,
 * у которых год издания входит в интервал
 */
public class Library {
    private final Map<String, Book> mapByName;
    private final Map<String, Book> mapByAuthor;
    private final Map<Integer, List<Book>> mapByYear;

    public Library() {
        this.mapByName = new HashMap<>();
        this.mapByAuthor = new HashMap<>();
        this.mapByYear = new HashMap<>();
    }

    public void addBook(Book book) {
        mapByName.put(book.getName(), book);
        mapByAuthor.put(book.getAuthor(), book);

        List<Book> current = mapByYear.get(book.getYear());
        if (current == null)
            current = new ArrayList<>();
        current.add(book);
        mapByYear.put(book.getYear(), current);
    }

    public void removeBook(Book book) {
        mapByName.remove(book.getName());
        mapByAuthor.remove(book.getAuthor());
        mapByYear.get(book.getYear()).remove(book);
    }

    public Book searchByName(String name) {
        return mapByName.get(name);
    }

    public Book searchByAuthor(String author) {
        return mapByAuthor.get(author);
    }

    public List<Book> searchByYear(Integer year) {
        return mapByYear.get(year);
    }

    public List<Book> getBooksFromInterval(int l, int r) {
        List<Book> result = new ArrayList<>();
        for (int i = l; i <= r; i++)
            result.addAll(mapByYear.getOrDefault(i, Collections.emptyList()));

        return result;
    }
}
