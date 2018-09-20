package com.gaucow.betterbartersystem.models;

public class Listing {
    private String isbn;
    private String userName;
    private String bookName;

    public Listing() {}

    public Listing(String isbn, String userName, String bookName) {
        this.isbn = isbn;
        this.userName = userName;
        this.bookName = bookName;
    }

    public String getName() {
        return userName;
    }

    public void setName(String name) {
        this.userName = name;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }
}
