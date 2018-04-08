package com.nhommot.doctruyen.models;

/**
 * Created by Huy on 4/8/2018.
 */

public class Book {

    private int itemId;
    private String author;
    private String name;
    private String description;
    private String imgPreview;
    private int typeId;
    private int views;

    public Book(){

    }

    public Book(int itemId, String author, String name, String description, String imgPreview, int typeId, int views) {
        this.itemId = itemId;
        this.author = author;
        this.name = name;
        this.description = description;
        this.imgPreview = imgPreview;
        this.typeId = typeId;
        this.views = views;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgPreview() {
        return imgPreview;
    }

    public void setImgPreview(String imgPreview) {
        this.imgPreview = imgPreview;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }


}
