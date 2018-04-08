package com.nhommot.doctruyen.models;

/**
 * Created by Huy on 4/8/2018.
 */

public class Rating {
    private int userId;
    private int bookId;
    private int stars;

    public Rating() {
    }

    public Rating(int userId, int bookId, int stars) {
        this.userId = userId;
        this.bookId = bookId;
        this.stars = stars;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }
}
