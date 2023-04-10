package ru.urfu;

import java.util.*;

/**
 * Для хранения книг будем использовать 3 структуры данных
 * по одной для каждого из полей по которым будет производиться поиск
 *
 * добавление книги addBook() - сложность O(1): сначала мы получаем по ключу(у нас это три поля: имя, автор, год издания)
 *  множество книг O(1), далее мы добавляем книгу в множество O(1), и добавляем это множество по нужному ключу(имя, автор или год издания) O(1)
 *  При правильной работе hashCode() все операции будут обеспечивать сложность в O(1)
 *  С функцией removeBook() аналогично
 *
 * Функции searchByName(), searchByAuthor() и searchByYear() будут иметь сложность O(n) где n - количество книг с определенным
 * название, автором, годом издания соответственно.
 * В качестве значения решил использовать LinkedHashSet - т.к. он обеспечивает более быструю итерацию (она пригодится, когда будем возвращать список книг при поиске, под капотом stream()
 * используется spliterator, который в свою очередь использует iterator())
 * по элементам чем просто HashSet, а операции добавления и удаление умеют такую же сложность.
 *
 * Функция getBooksFromInterval(): пусть n - это интервал в котором мы ищем книги (r - l), m - максимальное количество книг
 * в каком-то из годов данного интервала, тогда худшая сложность будет O(n*m), лучшая сложность будет O(n) или O(m) - когда
 * интервал слишком маленький, либо количество книг небольшое. Среднюю сложность посчитать сложно.
 *
 * В качестве возвращающего значения использовал ArrayList (реализация методов toList вызванная при использовании stream()),
 * т.к. нужно вывести все книги, удобнее всего их добавить в ArrayList, для дальнейшей работы (можно было и вывести все сразу, но мне показалось, что так будет лучше)
 */
public class Library {
    private final Map<String, Set<Book>> mapByName;
    private final Map<String, Set<Book>> mapByAuthor;
    private final Map<Integer, Set<Book>> mapByYear;

    public Library() {
        this.mapByName = new HashMap<>();
        this.mapByAuthor = new HashMap<>();
        this.mapByYear = new HashMap<>();
    }

    public void addBook(Book book) {
        Set<Book> cur = mapByName.getOrDefault(book.getName(), new LinkedHashSet<>());
        cur.add(book);
        mapByName.put(book.getName(), cur);

        cur = mapByAuthor.getOrDefault(book.getAuthor(), new LinkedHashSet<>());
        cur.add(book);
        mapByAuthor.put(book.getAuthor(), cur);

        cur = mapByYear.getOrDefault(book.getYear(), new LinkedHashSet<>());
        cur.add(book);
        mapByYear.put(book.getYear(), cur);
    }

    public void removeBook(Book book) {
        Set<Book> current = mapByName.get(book.getName());
        if (current != null) {
            current.remove(book);
            mapByName.put(book.getName(), current);
        }

        current = mapByAuthor.get(book.getAuthor());
        if (current != null) {
            current.remove(book);
            mapByAuthor.put(book.getAuthor(), current);
        }

        current = mapByYear.get(book.getYear());
        if (current != null) {
            current.remove(book);
            mapByYear.put(book.getYear(), current);
        }
    }

    public List<Book> searchByName(String name) {
        return mapByName.getOrDefault(name, new LinkedHashSet<>()).stream().toList();
    }

    public List<Book> searchByAuthor(String author) {
        return mapByAuthor.getOrDefault(author, new LinkedHashSet<>()).stream().toList();
    }

    public List<Book> searchByYear(Integer year) {
        return mapByYear.getOrDefault(year, new LinkedHashSet<>()).stream().toList();
    }

    public List<Book> getBooksFromInterval(int l, int r) {
        List<Book> result = new ArrayList<>();
        for (int i = l; i <= r; i++)
            result.addAll(mapByYear.getOrDefault(i, new LinkedHashSet<>()).stream().toList());

        return result;
    }
}
