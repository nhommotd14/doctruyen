package com.nhommot.doctruyen.models;

public class ChapterOffline {
    private String idBook;
    private String idChap;
    private String chapName;

    public ChapterOffline(String idBook, String idChap, String chapName) {
        this.idBook = idBook;
        this.idChap = idChap;
        this.chapName = chapName;
    }

    public String getIdBook() {
        return idBook;
    }

    public void setIdBook(String idBook) {
        this.idBook = idBook;
    }

    public String getIdChap() {
        return idChap;
    }

    public void setIdChap(String idChap) {
        this.idChap = idChap;
    }

    public String getChapName() {
        return chapName;
    }

    public void setChapName(String chapName) {
        this.chapName = chapName;
    }
}
