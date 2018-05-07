package com.nhommot.doctruyen.models;

import java.util.HashMap;

/**
 * Created by Huy on 4/8/2018.
 */

public class Type {
    private  String name;
    private String typeId;
    private String description;
    private HashMap<String, Boolean> books;

    public Type(String typeId, String description) {
        this.typeId = typeId;
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public  Type(){}
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

    public HashMap<String, Boolean> getBooks() {
        return books;
    }

    public void setBooks(HashMap<String, Boolean> books) {
        this.books = books;
    }
}
