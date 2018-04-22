package com.nhommot.doctruyen.models;

import java.util.List;
import java.util.Map;

/**
 * Created by Huy on 4/8/2018.
 */

public class Book {
    private String bookId;
    private String author;
    private String name;
    private String description;
    private String imgPreview;
    private int views;
    private List<Chapter> chapters;
    private Map<String, Boolean> types;
    private List<Rating> ratings;
    private List<Comment> comments;


    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
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

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public List<Chapter> getChapters() {
        return chapters;
    }

    public void setChapters(List<Chapter> chapters) {
        this.chapters = chapters;
    }

    public Map<String, Boolean> getTypes() {
        return types;
    }

    public void setTypes(Map<String, Boolean> types) {
        this.types = types;
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
