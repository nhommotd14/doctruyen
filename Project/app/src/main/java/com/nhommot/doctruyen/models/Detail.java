package com.nhommot.doctruyen.models;

/**
 * Created by Nguyen Dung on 3/12/2018.
 */

public class Detail {
    String tenTacGia;
    String tenTruyen;
    String theLoai;
    int soChuong;
    String review;

    public Detail(){};
    public Detail(String tenTacGia, String tenTruyen, String theLoai, int soChuong, String review){
        this.tenTacGia = tenTacGia;
        this.theLoai = theLoai;
        this.tenTruyen= tenTruyen;
        this.review = review;
        this.soChuong = soChuong;
    }
    public String getTenTruyen() {
        return tenTruyen;
    }
    public void setTenTruyen(String tenTruyen) {
        this.tenTruyen = tenTruyen;
    }

    public String getTenTacGia() {
        return tenTacGia;
    }

    public void setTenTacGia(String tenTacGia) {
        this.tenTacGia = tenTacGia;
    }

    public int getSoChuong() {
        return soChuong;
    }

    public void setSoChuong(int soChuong) {
        this.soChuong = soChuong;
    }

    public String getTheLoai() {
        return theLoai;
    }

    public void setTheLoai(String theLoai) {
        this.theLoai = theLoai;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }
}
