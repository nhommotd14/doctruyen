package com.nhommot.doctruyen.ui.activities;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
import com.nhommot.doctruyen.models.BookOffline;
import com.nhommot.doctruyen.models.Chapter;
import com.nhommot.doctruyen.models.Content;
import com.nhommot.doctruyen.models.Author;
import com.nhommot.doctruyen.models.Book;
import com.nhommot.doctruyen.models.Rating;
import com.nhommot.doctruyen.ui.adapters.TabAdapter;
import com.nhommot.doctruyen.ui.fragments.ChapterFragment;
import com.nhommot.doctruyen.ui.fragments.CommentFragment;
import com.nhommot.doctruyen.ui.fragments.ReviewFragment;
import com.nhommot.doctruyen.utils.FirebaseUtils;
import com.nhommot.doctruyen.utils.JsonUtils;
import com.nhommot.doctruyen.utils.SharedPrefsUtils;
import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ReviewActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private static final String TAG = "MainActivity";
    private String urlImg;
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
    private ImageButton btnLike;
    private BookOfflineSQLite dbOffline;
    DatabaseReference contentDatabase;

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
        btnLike = findViewById(R.id.imageButtonLike);


//        //Test
        buttonDownload = findViewById(R.id.imageButtonDownload);
//        //

        dbOffline = new BookOfflineSQLite(getApplicationContext(), "OfflineBook.sqlite", null, 1);


        img = (ImageView) findViewById(R.id.imgReview);
        Log.d(TAG, "onCreate: " + SharedPrefsUtils.getOfflineState(this));
        if (SharedPrefsUtils.getOfflineState(this)) {
            Cursor cursor = dbOffline.Getdata("select * from bookoff where id='" + bookId + "'");
            BookOffline bookOffline = null;
            while (cursor.moveToNext()) {
                bookOffline = new BookOffline(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getBlob(4),
                        cursor.getString(5),
                        cursor.getDouble(6)
                );
            }
            tvTenTruyen.setText(bookOffline.getName());
            tvTacGia.setText(bookOffline.getAuthor());
            tvTheLoai.setText(bookOffline.getType());
            ratingBar.setRating((float) bookOffline.getStar());
            Bitmap bitmap = BitmapFactory.decodeByteArray(bookOffline.getImg(), 0, bookOffline.getImg().length);
            img.setImageBitmap(bitmap);

        } else {

            ValueEventListener bookListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Book book = dataSnapshot.getValue(Book.class);
                    urlImg = book.getImgPreview();
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
                    des = book.getDescription();
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
                        if (rate != null) {
                            ratingBar.setRating(rate.getStars());
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                addListenerOnRatingBar();
            }
        }
//        //Test
        if (userId != null) {
            FirebaseUtils.getFavouriteRef().child(userId).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(final DataSnapshot dataSnapshot, String s) {
                    if (dataSnapshot.getKey().equals(bookId)) {
                        Log.d(TAG, "onChildAdded: true");
                        btnLike.setImageResource(R.drawable.ic_favorite_pressed);
                        btnLike.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                FirebaseUtils.getFavouriteRef().child(userId).child(dataSnapshot.getKey()).removeValue();
                                btnLike.setImageResource(R.drawable.ic_favorite);
                                btnLike.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        if (userId != null) {
                                            FirebaseUtils.getFavouriteRef().child(userId).child(bookId).setValue(true);
                                            btnLike.setImageResource(R.drawable.ic_favorite_pressed);
                                        }
                                    }
                                });
                            }
                        });
                    } else {

                    }
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


            btnLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (userId != null) {
                        FirebaseUtils.getFavouriteRef().child(userId).child(bookId).setValue(true);
                        btnLike.setImageResource(R.drawable.ic_favorite_pressed);
                    }
                }
            });

        }
        buttonDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: btndwnnnnnn====");
                Cursor cursor = dbOffline.Getdata("select 1 from bookoff where id='" + bookId + "'");
                if (cursor.getCount() > 0) {
                    Toast.makeText(ReviewActivity.this, "Truyen Da Tai", Toast.LENGTH_SHORT).show();
                } else {
                    //InsertBook
                    BookOffline bookOffline = new BookOffline();
                    bookOffline.setBookId(bookId);
                    bookOffline.setName(tvTenTruyen.getText().toString());
                    bookOffline.setAuthor(tvTacGia.getText().toString());
                    bookOffline.setDescription(des);
                    bookOffline.setImg(ImageViewToByte(img));
                    bookOffline.setType(tvTheLoai.getText().toString());
                    bookOffline.setStar(ratingBar.getNumStars());

                    dbOffline.InsertBook(bookOffline);

                    //InsertChap
                    final String currentBookId = SharedPrefsUtils.getCurrentBookId(getApplicationContext());
                    final List<Chapter> result = new ArrayList<>();
                    FirebaseUtils.getChapterRef().child(currentBookId).addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            Log.d(TAG, "onChildAdded: KEY " + dataSnapshot.getKey());
                            final Chapter chapter = dataSnapshot.getValue(Chapter.class);
                            Cursor cursor = dbOffline.Getdata("Select * from bookchapoff where idtruyen='" + currentBookId + "' AND idchap='" + chapter.getChapterId() + "'");
                            if (cursor.getCount() == 0)
                                dbOffline.InsertChap(chapter.getBookId(), chapter.getChapterId(), chapter.getChapterName());

                            //InsertContent
                            contentDatabase = FirebaseDatabase.getInstance().getReference().child("contents").child(chapter.getChapterId());

                            contentDatabase.addValueEventListener(new ValueEventListener() {

                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    Iterable<DataSnapshot> nodeChild = dataSnapshot.getChildren();
                                    for (final DataSnapshot dataSnapshot1 : nodeChild) {


                                        final Content content = dataSnapshot1.getValue(Content.class);
                                        try {
                                            new LoadImageInternet().execute(content.getChapterId(),String.valueOf(content.getContentNumber()),content.getSrc());
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        }

                        @Override
                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onChildRemoved(DataSnapshot dataSnapshot) {

                        }

                        @Override
                        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


                    Toast.makeText(ReviewActivity.this, "Da Tai Thanh Cong", Toast.LENGTH_SHORT).show();
                }
            }
        });
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
    public byte[] ImageViewToByte(ImageView imageView) {
        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
        Bitmap bitmap = drawable.getBitmap();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] bytes = stream.toByteArray();
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

    public static Drawable LoadImageFromWebOperations(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        } catch (Exception e) {
            return null;
        }
    }

    public byte[] urlImgToByte(String src) throws Exception {
        byte[] byteArray=null;
        try {
            Bitmap bitmap = BitmapFactory.decodeStream((InputStream)new URL(src).getContent());
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byteArray = stream.toByteArray();
            bmp.recycle();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  byteArray;
    }

    public byte[] getByteFromUrl(String urlImg) {
        URL url = null;
        byte[] response = null;

        try {
            url = new URL(urlImg);
            InputStream in = new BufferedInputStream(url.openStream());
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            int n = 0;
            while (-1!=(n=in.read(buf)))
            {
                out.write(buf, 0, n);
            }
            out.close();
            in.close();
            response = out.toByteArray();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  response;

    }

    private class LoadImageInternet extends AsyncTask<String, Void, byte[]> {
        Bitmap bitmapHinh;

        @Override
        protected byte[] doInBackground(String... strings) {
            String chapterid = strings[0];
            int chaptetNum = Integer.parseInt(strings[1]);
            String src = strings[2];
            URL url = null;
            byte[] response = null;

            try {
                url = new URL(src);
                InputStream in = new BufferedInputStream(url.openStream());
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                byte[] buf = new byte[1024];
                int n = 0;
                while (-1!=(n=in.read(buf)))
                {
                    out.write(buf, 0, n);
                }
                out.close();
                in.close();
                response = out.toByteArray();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Cursor cursor = dbOffline.Getdata("Select * from chap where chapnum='" + chaptetNum + "' AND idchap='" + chapterid + "'");
            if (cursor.getCount() == 0) {
                dbOffline.InsertContent(chapterid, chaptetNum, response);
            }
            Log.d(TAG, "doInBackground: byteeeeee "+ response);

            return  response;
        }

        @Override
        protected void onPostExecute(byte[] bytes) {
            super.onPostExecute(bytes);
        }
    }
}

