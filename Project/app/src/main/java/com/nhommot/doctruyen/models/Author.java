package com.nhommot.doctruyen.models;

import java.util.List;

/**
 * Created by Huy on 4/8/2018.
 */

public class Author {
    private int authorId;

    private List<Book> books;

    private String name;
    private String desciption;
    public Author() {
    }

    public Author(int authorId, List<Book> books, String name, String desciption) {
        this.authorId = authorId;
        this.books = books;
        this.name = name;
        this.desciption = desciption;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesciption() {
        return desciption;
    }

    public void setDesciption(String desciption) {
        this.desciption = desciption;
    }
}
