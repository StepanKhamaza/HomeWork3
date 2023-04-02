package ru.urfu;

import java.util.*;

public class Library {
    private Set<Book> books;

    public Library() {
        this.books = new LinkedHashSet<>();
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void removeBook(Book book) {
        books.remove(book);
    }

    public List<Book> searchByName(String name) {
        return books.stream().filter(book -> book.getName().equals(name)).toList();
    }

    public List<Book> searchByAuthor(String author) {
        return books.stream().filter(book -> book.getAuthor().equals(author)).toList();
    }

    public List<Book> searchByYear(int year) {
        return books.stream().filter(book -> book.getYear() == year).toList();
    }

    public List<Book> getBooksFromInterval(int l, int r) {
        return books.stream().filter(book -> book.getYear() >= l && book.getYear() <= r).toList();
    }
}
