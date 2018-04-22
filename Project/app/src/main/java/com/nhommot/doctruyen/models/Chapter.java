package com.nhommot.doctruyen.models;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Huy on 4/8/2018.
 */

public class Chapter implements Serializable{
    private String chapterId;
    private String bookId;
    private String chapterName;
    private List<Content> contents;

    public Chapter(){};

    public Chapter(String chapterId, String bookId, String chapterName) {
        this.chapterId = chapterId;
        this.bookId = bookId;
        this.chapterName = chapterName;
    }

    public String getChapterId() {
        return chapterId;
    }

    public void setChapterId(String chapterId) {
        this.chapterId = chapterId;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public List<Content> getContents() {
        return contents;
    }

    public void setContents(List<Content> contents) {
        this.contents = contents;
    }
}
