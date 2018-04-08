package com.nhommot.doctruyen.models;

/**
 * Created by Huy on 4/8/2018.
 */

public class Author {
    private int authorId;
    private String name;
    private String desciption;

    public Author() {
    }

    public Author(int authorId, String name, String desciption) {
        this.authorId = authorId;
        this.name = name;
        this.desciption = desciption;
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
