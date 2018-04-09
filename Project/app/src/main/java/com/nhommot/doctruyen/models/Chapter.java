package com.nhommot.doctruyen.models;

import java.util.List;

/**
 * Created by Huy on 4/8/2018.
 */

public class Chapter {
    private int chapterId;
    private int bookId;
    private String chapterName;
    private List<Content> contents;

    public Chapter(int chapterId, int bookId, List<Content> contents, String chapterName) {
        this.chapterId = chapterId;
        this.bookId = bookId;
        this.contents = contents;
        this.chapterName = chapterName;
    }

    public Chapter(){}


    public List<Content> getContents() {
        return contents;
    }

    public void setContents(List<Content> contents) {
        this.contents = contents;
    }

    public int getChapterId() {
        return chapterId;
    }

    public void setChapterId(int chapterId) {
        this.chapterId = chapterId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }
}
