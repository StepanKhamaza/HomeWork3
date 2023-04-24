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
 * Функции searchByName(), searchByAuthor() и searchByYear() будут иметь сложность O(1) т.к. нам нужно будет обратиться к Map по
 * определенному ключу (такая функция обеспечивает O(1) при правильной работе hashCode())
 *
 * В качестве значения решил использовать LinkedHashSet - т.к. он обеспечивает более быструю итерацию, которая пригодится для вывода
 * значений на экран. Также я выбрал множество, потому что нам не важен порядок, и для вывода мы можем воспользоваться forEach
 *
 * Функция getBooksFromInterval(): пусть n - это интервал в котором мы ищем книги (r - l), m - максимальное количество книг
 * в каком-то из годов данного интервала, тогда худшая сложность будет O(n*m)(квадратичная), лучшая сложность будет O(n) или O(m) (то есть линейная) - когда
 * интервал слишком маленький, либо количество книг небольшое. Полагаться на лучший вариант не стоит, поэтому сложность O(n*m).
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
        if (current != null)
            current.remove(book);

        current = mapByAuthor.get(book.getAuthor());
        if (current != null)
            current.remove(book);

        current = mapByYear.get(book.getYear());
        if (current != null)
            current.remove(book);
    }

    public Set<Book> searchByName(String name) {
        return mapByName.getOrDefault(name, new LinkedHashSet<>());
    }

    public Set<Book> searchByAuthor(String author) {
        return mapByAuthor.getOrDefault(author, new LinkedHashSet<>());
    }

    public Set<Book> searchByYear(Integer year) {
        return mapByYear.getOrDefault(year, new LinkedHashSet<>());
    }

    public Set<Book> getBooksFromInterval(int l, int r) {
        Set<Book> result = new LinkedHashSet<>();
        for (int i = l; i <= r; i++)
            result.addAll(searchByYear(i));

        return result;
    }
}
