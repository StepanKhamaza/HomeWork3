package ru.urfu;

public class Main {
    public static void main(String[] args) {
        Library library = new Library();
        library.addBook(new Book("Name1", "Author1", 2011));
        library.addBook(new Book("Name2", "Author2", 2012));
        library.addBook(new Book("Name3", "Author2", 2013));
        library.addBook(new Book("Name4", "Author4", 2014));
        library.addBook(new Book("Name5", "Author1", 2015));
        library.addBook(new Book("Name5", "Author1", 2015));
        library.removeBook(new Book("Name6", "Author6", 2020));
        library.removeBook(new Book("Name1", "Author1", 2011));

        for (Book book : library.searchByAuthor("Author1"))
            System.out.println(book.getName() + " " + book.getAuthor() + " " + book.getYear());
        System.out.println();
        for (Book book : library.getBooksFromInterval(2011, 2020))
            System.out.println(book.getName() + " " + book.getAuthor() + " " + book.getYear());

    }
}