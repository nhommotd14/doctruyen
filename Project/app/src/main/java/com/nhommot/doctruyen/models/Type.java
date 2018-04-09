package com.nhommot.doctruyen.models;

import java.util.List;

/**
 * Created by Huy on 4/8/2018.
 */

public class Type {
    private int typeId;
    private String description;
    private List<Book> books;

    public Type() {
    }

    public Type(int typeId, String description, List<Book> books) {
        this.typeId = typeId;
        this.description = description;
        this.books = books;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
