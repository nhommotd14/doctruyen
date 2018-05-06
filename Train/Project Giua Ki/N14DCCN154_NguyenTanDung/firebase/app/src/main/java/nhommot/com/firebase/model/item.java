package nhommot.com.firebase.model;

/**
 * Created by Nguyen Dung on 4/15/2018.
 */

public class item {
    private String TenTruyen;
    private String TacGia;
    private String TheLoai;
    private int SoChuong;
    private String Review;

    public item() {
    }

    public item(String tenTruyen, String tacGia, String theLoai, int soChuong, String review) {
        TenTruyen = tenTruyen;
        TacGia = tacGia;
        TheLoai = theLoai;
        SoChuong = soChuong;
        Review = review;
    }

    public String getTenTruyen() {
        return TenTruyen;
    }

    public void setTenTruyen(String tenTruyen) {
        TenTruyen = tenTruyen;
    }

    public String getTacGia() {
        return TacGia;
    }

    public void setTacGia(String tacGia) {
        TacGia = tacGia;
    }

    public String getTheLoai() {
        return TheLoai;
    }

    public void setTheLoai(String theLoai) {
        TheLoai = theLoai;
    }

    public int getSoChuong() {
        return SoChuong;
    }

    public void setSoChuong(int soChuong) {
        SoChuong = soChuong;
    }

    public String getReview() {
        return Review;
    }

    public void setReview(String review) {
        Review = review;
    }
}
