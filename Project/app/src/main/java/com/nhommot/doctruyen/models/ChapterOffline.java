package com.nhommot.doctruyen.models;

public class ChapterOffline {
    private int idBook;
    private int idChap;
    private int chapNum;
    private byte[] img;

    public ChapterOffline(int idBook, int idChap, int chapNum, byte[] img) {
        this.idBook = idBook;
        this.idChap = idChap;
        this.chapNum = chapNum;
        this.img = img;
    }

    public int getIdBook() {
        return idBook;
    }

    public void setIdBook(int idBook) {
        this.idBook = idBook;
    }

    public int getIdChap() {
        return idChap;
    }

    public void setIdChap(int idChap) {
        this.idChap = idChap;
    }

    public int getChapNum() {
        return chapNum;
    }

    public void setChapNum(int chapNum) {
        this.chapNum = chapNum;
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }
}
