package com.nhommot.doctruyen;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class review extends AppCompatActivity {

    TextView tvTenTruyen = (TextView) findViewById(R.id.tvTenTruyen);
    TextView tvTacGia = (TextView) findViewById(R.id.tvTacGia);
    TextView tvTheLoai = (TextView) findViewById(R.id.tvTheLoai);
    TextView tvReview = (TextView) findViewById(R.id.tvReview);
    TextView tvSoChuong = (TextView) findViewById(R.id.tvSoChuong);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        tvTacGia.setText(truyen.getTenTacGia().toString());
        tvReview.setText(truyen.getReview().toString());
        tvTenTruyen.setText(truyen.getTenTruyen().toString());
        tvSoChuong.setText("Số chương: "+truyen.getSoChuong());
        tvTheLoai.setText(truyen.getTheLoai().toString());


    }

    String tenTacGia = "Kim Dung";
    String tenTruyen = "Thiên Long Bát Bộ";
    String theLoai = "Kiếm hiệp";
    int soChuong = 1000;
    String review = "Giới thiệu:\n" +
            "\n" +
            "Tiêu Phong, Bang chủ Cái Bang, một vị hảo hán đầu đội trời chân đạp đất tính tình hào sảng vô tư. Nhưng một ngày kia, ông khám phá ra thân thế của mình, từ đó bị mọi người trong võ lâm ghét bõ. Thật ra ông là ai?\n" +
            "\n" +
            "Hư Trúc, một vị tiểu hòa thượng chùa Thiếu Lâm, tính tình ngay thẳng ngốc nghếch. Trong một lần hạ sơn, anh đã được một vị kì nhân truyền thụ võ nghệ và trở thành một vị Cung chủ được mọi người kính trọng. Hơn thế nữa, anh ta còn cưới được một vị tuyệt sắc giai nhân. Liệu phái Thiếu Lâm sẽ sử trí ra sao với Hư Trúc?\n" +
            "\n" +
            "Đoàn Dự, tiểu vương gia nước Đại Lý, là người hiền lành rất ghét võ công. Trong cuộc đời, cậu đã gặp rất nhiều mỹ nhân và hầu như tất cả những người này đều yêu thương cậu. Những người mà cậu luôn theo đuổi thì đã có ý trung nhân. Một ngày kia, cậu phát hiện ra tất cả những cô gái đó đều là em của cậu. Vậy Đoàn thế tử sẽ ra sao?";

    detail truyen = new detail(tenTacGia,tenTruyen,theLoai,soChuong,review);
}
