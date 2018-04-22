package com.nhommot.doctruyen.ui.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.nhommot.doctruyen.R;
import com.nhommot.doctruyen.comment_risk;
import com.nhommot.doctruyen.models.Author;
import com.nhommot.doctruyen.models.Book;
import com.nhommot.doctruyen.ui.adapters.TabAdapter;
import com.nhommot.doctruyen.ui.fragments.ChapterFragment;
import com.nhommot.doctruyen.ui.fragments.ReviewFragment;
import com.nhommot.doctruyen.utils.FirebaseUtils;
import com.nhommot.doctruyen.utils.JsonUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPage(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        tvTenTruyen = (TextView) findViewById(R.id.tvTenTruyen);
        tvTacGia = (TextView) findViewById(R.id.tvTacGia);
        tvTheLoai = (TextView) findViewById(R.id.tvTheLoai);
        final ImageView img = (ImageView) findViewById(R.id.imgReview);
        ValueEventListener bookListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Book book = dataSnapshot.getValue(Book.class);
                Log.d(TAG, "onDataChange: " + JsonUtils.encode(book));
                tvTenTruyen.setText(String.valueOf(book.getName()));
                String theLoai ="";

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

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        FirebaseUtils.getBookRef().child("bookRandomStr1").addValueEventListener(bookListener);


    }

    private void setupViewPage(ViewPager viewPager) {
        TabAdapter adapter = new TabAdapter(getSupportFragmentManager());
        adapter.addFragment(new ReviewFragment(), "Review");
        adapter.addFragment(new ChapterFragment(), "Chapter");
        adapter.addFragment(new comment_risk(), "Comment");

        viewPager.setAdapter(adapter);
//        viewPager.getOffscreenPageLimit();
    }
}


