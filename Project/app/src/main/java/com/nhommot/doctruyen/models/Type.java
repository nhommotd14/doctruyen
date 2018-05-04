package com.nhommot.doctruyen.models;

import java.util.List;

/**
 * Created by Huy on 4/8/2018.
 */

public class Type {
    private String typeId;
    private String description;
    private List<Book> books;

    public Type(String typeId, String description) {
        this.typeId = typeId;
        this.description = description;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
