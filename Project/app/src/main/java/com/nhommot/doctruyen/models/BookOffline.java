package com.nhommot.doctruyen.models;

import java.io.Serializable;

public class BookOffline extends Book {

    private byte[] img;
    private String type;
    private double star;

    public BookOffline(){}

    public BookOffline(String bookId, String bookName, String bookAuthor, String bookDescription, byte[] img,String type,double star) {
        super(bookId,bookName,bookAuthor,bookDescription);
        this.img = img;
        this.type=type;
        this.star=star;
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }
}
