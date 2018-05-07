package com.nhommot.doctruyen.models;

public class ContentOffline extends Content{
    private String idchap;
    private String chapnum;
    private byte[] img;

    public ContentOffline(){}
    public ContentOffline(String idchap, String chapnum, byte[] img) {
        this.idchap = idchap;
        this.chapnum = chapnum;
        this.img = img;
    }

    public String getIdchap() {
        return idchap;
    }

    public void setIdchap(String idchap) {
        this.idchap = idchap;
    }

    public String getChapnum() {
        return chapnum;
    }

    public void setChapnum(String chapnum) {
        this.chapnum = chapnum;
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }
}
