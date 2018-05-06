package com.nhommot.doctruyen.ui.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.nhommot.doctruyen.R;
import com.nhommot.doctruyen.database.BookOfflineSQLite;
import com.nhommot.doctruyen.risk.comment_risk;
import com.nhommot.doctruyen.models.Author;
import com.nhommot.doctruyen.models.Book;
import com.nhommot.doctruyen.ui.adapters.TabAdapter;
import com.nhommot.doctruyen.ui.fragments.ChapterFragment;
import com.nhommot.doctruyen.ui.fragments.CommentFragment;
import com.nhommot.doctruyen.ui.fragments.ReviewFragment;
import com.nhommot.doctruyen.utils.FirebaseUtils;
import com.nhommot.doctruyen.utils.JsonUtils;
import com.nhommot.doctruyen.utils.SharedPrefsUtils;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.Map;

public class ReviewActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private static final String TAG = "MainActivity";
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ImageView img;
    private TextView tvTenTruyen;
    private TextView tvTacGia;
    private TextView tvTheLoai;
    private Button btnDocTruyen;
    private String bookId = "";

//    //Test by Toan
//    private Button buttonTest;
//    private String des;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        getBookId();
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPage(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        tvTenTruyen = (TextView) findViewById(R.id.tvTenTruyen);
        tvTacGia = (TextView) findViewById(R.id.tvTacGia);
        tvTheLoai = (TextView) findViewById(R.id.tvTheLoai);
        btnDocTruyen = (Button) findViewById(R.id.doctruyen);

//        //Test
//        buttonTest=(Button) findViewById(R.id.buttonTest);
//        //

        final ImageView img = (ImageView) findViewById(R.id.imgReview);
        ValueEventListener bookListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Book book = dataSnapshot.getValue(Book.class);
                Log.d(TAG, "onDataChange: " + JsonUtils.encode(book));
                tvTenTruyen.setText(String.valueOf(book.getName()));
                String theLoai = "";

                for (Map.Entry<String, Boolean> entry : book.getTypes().entrySet()) {
                    theLoai += entry.getKey() + " ";
                }
                tvTheLoai.setText(theLoai);
                Picasso.with(ReviewActivity.this).load(book.getImgPreview()).into(img);

                FirebaseUtils.getAuthorRef().child(book.getAuthor()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Author author = dataSnapshot.getValue(Author.class);
                        tvTacGia.setText(author.getName());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

//                //Test
//                des=book.getDescription();
//                //
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        FirebaseUtils.getBookRef().child(bookId).addValueEventListener(bookListener);

//        //Test
//        buttonTest.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                final BookOfflineSQLite dbOffline=new BookOfflineSQLite(getApplicationContext(),"OfflineBook.sqlite",null,1);
//                String tableBook="create table if not exists Bookoffline(id integer primary key,name nvarchar,author nvarchar,description nvarchar,img Blob)";
//                dbOffline.QueryData(tableBook);
//                String tableBookChap="create table if not exists BookChap(idtruyen integer,idchap integer,chapnum integer,hinh Blob)";
//                dbOffline.QueryData(tableBookChap);
//
//                dbOffline.InsertBook((double)1,tvTenTruyen.getText().toString(),tvTacGia.getText().toString(),des,ImageViewToByte(img));
//            }
//        });
//        //
    }

    public void onClickDocTruyen(View v) {
        Intent intent = new Intent(ReviewActivity.this, ReadActivity.class);
        startActivity(intent);
    }

    private void setupViewPage(ViewPager viewPager) {
        TabAdapter adapter = new TabAdapter(getSupportFragmentManager());
        adapter.addFragment(new ReviewFragment(), "Review");
        adapter.addFragment(new ChapterFragment(), "Chapter");
        adapter.addFragment(new CommentFragment(), "Comment");

        viewPager.setAdapter(adapter);
    }

//    //Test
//    public byte[] ImageViewToByte(ImageView imageView){
//        BitmapDrawable drawable=(BitmapDrawable) imageView.getDrawable();
//        Bitmap bitmap=drawable.getBitmap();
//
//        ByteArrayOutputStream stream=new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
//        byte[] bytes=stream.toByteArray();
//        return bytes;
//    }
//    //
    public void getBookId() {
        bookId = SharedPrefsUtils.getCurrentBookId(this);
    }
}


