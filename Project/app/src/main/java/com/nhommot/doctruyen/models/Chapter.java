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
    private int chapterNumber;
    private List<Content> contents;
    public Chapter(String bookId, String chapterId, String chapterName) {
        this.bookId = bookId;
        this.chapterId = chapterId;
        this.chapterName = chapterName;
    }


    public int getChapterNumber() {
        return chapterNumber;
    }

    public void setChapterNumber(int chapterNumber) {
        this.chapterNumber = chapterNumber;
    }

    public Chapter(){};



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
