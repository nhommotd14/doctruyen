package com.nhommot.doctruyen.ui.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nhommot.doctruyen.R;
import com.nhommot.doctruyen.models.Author;
import com.nhommot.doctruyen.models.Book;
import com.nhommot.doctruyen.models.Rating;
import com.nhommot.doctruyen.ui.adapters.TabAdapter;
import com.nhommot.doctruyen.ui.fragments.ChapterFragment;
import com.nhommot.doctruyen.ui.fragments.CommentFragment;
import com.nhommot.doctruyen.ui.fragments.ReviewFragment;
import com.nhommot.doctruyen.utils.FirebaseUtils;
import com.nhommot.doctruyen.utils.SharedPrefsUtils;
import com.squareup.picasso.Picasso;

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
    private RatingBar ratingBar;
    private String userId;
    private Bitmap bmp;

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
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);

        final ImageView img = (ImageView) findViewById(R.id.imgReview);
        ValueEventListener bookListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Book book = dataSnapshot.getValue(Book.class);
//                Log.d(TAG, "onDataChange: " + JsonUtils.encode(book));
                tvTenTruyen.setText(String.valueOf(book.getName()));
                String theLoai = "";

                for (Map.Entry<String, Boolean> entry : book.getTypes().entrySet()) {
                    theLoai += entry.getKey() + " ";
                }
                tvTheLoai.setText(theLoai);
                Picasso.get().load(book.getImgPreview()).into(img);

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
        FirebaseUtils.getBookRef().child(bookId).addValueEventListener(bookListener);
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
            FirebaseDatabase.getInstance().getReference("ratings").child(bookId).child(userId).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Rating rate = dataSnapshot.getValue(Rating.class);
//                    Toast.makeText(ReviewActivity.this,"ass "+rate.getBookId(),Toast.LENGTH_LONG).show();
                    ratingBar.setRating(rate.getStars());
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            addListenerOnRatingBar();
        }


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

    public void addListenerOnRatingBar() {
        FirebaseAuth.getInstance().getCurrentUser();
        ratingBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Rating rating = new Rating();
                rating.setBookId(bookId);
                rating.setUserId(FirebaseAuth.getInstance().getCurrentUser().getUid());
                rating.setStars(Math.round(ratingBar.getRating()));
                FirebaseDatabase.getInstance().getReference("ratings").child(bookId).child(rating.getUserId()).setValue(rating);
                return false;
            }

        });
    }

    public void getBookId() {
        bookId = SharedPrefsUtils.getCurrentBookId(this);
    }
}


