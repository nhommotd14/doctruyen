package com.nhommot.doctruyen.models;

/**
 * Created by Huy on 4/8/2018.
 */

public class Chapter {
    private int chapterId;
    private int itemId;

    public Chapter(){}

    public Chapter(int chapterId, int itemId, String chapterName) {
        this.chapterId = chapterId;
        this.itemId = itemId;
        this.chapterName = chapterName;
    }

    private String chapterName;

    public int getChapterId() {
        return chapterId;
    }

    public void setChapterId(int chapterId) {
        this.chapterId = chapterId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }
}
