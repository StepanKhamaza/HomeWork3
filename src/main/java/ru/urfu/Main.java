package ru.urfu;

public class Main {
    public static void main(String[] args) {
        Library library = new Library();
        library.addBook(new Book("Name1", "Author1", 2011));
        library.addBook(new Book("Name2", "Author2", 2012));
        library.addBook(new Book("Name3", "Author2", 2013));
        library.addBook(new Book("Name4", "Author4", 2014));
        library.addBook(new Book("Name5", "Author1", 2015));

        Book bookByAuthor = library.searchByAuthor("Author4");
        System.out.println(bookByAuthor.getName() + " " + bookByAuthor.getAuthor() + " " + bookByAuthor.getYear() + "\n");
        for (Book book : library.getBooksFromInterval(2011, 2014))
            System.out.println(book.getName() + " " + book.getAuthor() + " " + book.getYear());

    }
}