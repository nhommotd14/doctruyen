package com.nhommot.doctruyen.models;

/**
 * Created by Huy on 4/8/2018.
 */

public class Comment {
    private int userId;
    private int itemId;
    private String content;

    public Comment() {
    }

    public Comment(int userId, int itemId, String content) {
        this.userId = userId;
        this.itemId = itemId;
        this.content = content;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
