package com.nhommot.doctruyen.ui.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nhommot.doctruyen.R;
import com.nhommot.doctruyen.database.BookOfflineSQLite;
import com.nhommot.doctruyen.models.Chapter;
import com.nhommot.doctruyen.models.ChapterOffline;
import com.nhommot.doctruyen.models.Content;
import com.nhommot.doctruyen.risk.comment_risk;
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

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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

//    //Test by Toan
    private ImageButton buttonDownload;
    private String des;

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

//        //Test
        buttonDownload=findViewById(R.id.imageButtonDownload);
//        //
//        final BookOfflineSQLite dbOffline=new BookOfflineSQLite(getApplicationContext(),"OfflineBook.sqlite",null,1);
//        dbOffline.DeleteBook("Naruto");
//        dbOffline.QueryData("Drop table OfflineBook.bookoffline");
        final BookOfflineSQLite dbOffline=new BookOfflineSQLite(getApplicationContext(),"OfflineBook.sqlite",null,1);
        String tableBook="create table if not exists bookoff(id nvarchar,name nvarchar,author nvarchar,description nvarchar,img Blob)";
        dbOffline.QueryData(tableBook);
        String tableBookChap="create table if not exists BookChapoff(idtruyen nvarchar,idchap nvarchar,chapname nvarchar)";
        dbOffline.QueryData(tableBookChap);
        String tableChap="create table if not exists Chap(idchap nvarchar,chapnum integer,img Blob)";
        dbOffline.QueryData(tableChap);

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

//                //Test
                des=book.getDescription();
//                //
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

//        //Test
        buttonDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                final BookOfflineSQLite dbOffline=new BookOfflineSQLite(getApplicationContext(),"OfflineBook.sqlite",null,1);
//                String tableBook="create table if not exists bookoff(id nvarchar,name nvarchar,author nvarchar,description nvarchar,img Blob)";
//                dbOffline.QueryData(tableBook);
//                String tableBookChap="create table if not exists BookChap(idtruyen nvarchar,idchap nvarchar,chapname nvarchar)";
//                dbOffline.QueryData(tableBookChap);
//                String tableChap="create table if not exists Chap(idchap nvarchar,chapnum integer,img Blob)";
//                dbOffline.QueryData(tableChap);

                //InsertBook
                dbOffline.InsertBook(bookId,tvTenTruyen.getText().toString(),tvTacGia.getText().toString(),des,ImageViewToByte(img));

                //InsertChap
                String currentBookId = SharedPrefsUtils.getCurrentBookId(getApplicationContext());
                final List<Chapter> result= new ArrayList<>();
                FirebaseUtils.getChapterRef().child(currentBookId).addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        Log.d(TAG, "onChildAdded: KEY " + dataSnapshot.getKey());
                        Chapter chapter = dataSnapshot.getValue(Chapter.class);
                        dbOffline.InsertChap(chapter.getBookId(),chapter.getChapterId(),chapter.getChapterName());

                        //InsertContent
                        FirebaseUtils.getContentRef().child(chapter.getChapterId()).addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                Content content=dataSnapshot.getValue(Content.class);
                                try {
                                    dbOffline.InsertContent(content.getChapterId(),content.getContentId(),urlImgToByte(content.getSrc()));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            @Override
                            public void onChildChanged(DataSnapshot dataSnapshot, String s) { }
                            @Override
                            public void onChildRemoved(DataSnapshot dataSnapshot) { }
                            @Override
                            public void onChildMoved(DataSnapshot dataSnapshot, String s) { }
                            @Override
                            public void onCancelled(DatabaseError databaseError) { }
                        });
                    }
                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) { }
                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) { }
                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) { }
                    @Override
                    public void onCancelled(DatabaseError databaseError) { }
                });


            }
        });
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
    public byte[] ImageViewToByte(ImageView imageView){
        BitmapDrawable drawable=(BitmapDrawable) imageView.getDrawable();
        Bitmap bitmap=drawable.getBitmap();

        ByteArrayOutputStream stream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
        byte[] bytes=stream.toByteArray();
        return bytes;
    }
//    //
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

    public byte[] urlImgToByte(String urlText) throws Exception {
        URL url = new URL(urlText);
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        try (InputStream inputStream = url.openStream()) {
            int n = 0;
            byte [] buffer = new byte[ 1024 ];
            while (-1 != (n = inputStream.read(buffer))) {
                output.write(buffer, 0, n);
            }
        }
        return output.toByteArray();
    }
}


