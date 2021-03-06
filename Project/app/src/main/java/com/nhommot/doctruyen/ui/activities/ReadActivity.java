package com.nhommot.doctruyen.ui.activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
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
import com.nhommot.doctruyen.models.ContentOffline;
import com.nhommot.doctruyen.ui.adapters.ReadAdapter;
import com.nhommot.doctruyen.utils.FirebaseUtils;
import com.nhommot.doctruyen.utils.JsonUtils;
import com.nhommot.doctruyen.utils.SharedPrefsUtils;

import java.util.ArrayList;

public class ReadActivity extends AppCompatActivity {
    private final String TAG = "ReadActivity";
    private String currentContent;
    DatabaseReference contentDatabase;
    RecyclerView recyclerView;
    private ArrayList<Content> contentArrayList = new ArrayList<>();
    private LinearLayoutManager layoutManager;
    private ReadAdapter mAdapter;
    String chapterId = "";
    String userID;
    String bookId;
    ArrayList<ContentOffline> contentOfflineArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);

        recyclerView = findViewById(R.id.recyclerRead);

        //get value từ Adapter
        mAdapter = new ReadAdapter(this, contentArrayList);

        //tạo layout Horizontal
        layoutManager = new LinearLayoutManager(this, layoutManager.HORIZONTAL, false);

        //set các thông số cho recyclerView
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);
//      Set onSwipe

        bookId = SharedPrefsUtils.getCurrentBookId(this);


        //truy xuất vào database

        Intent intent = getIntent();
        if (intent.hasExtra("chapterId")) {
            chapterId = intent.getStringExtra("chapterId");
            Log.d(TAG, "onCreate: ===============" + chapterId);
            chapterId = intent.getStringExtra("chapterId");
            if (SharedPrefsUtils.getOfflineState(this)) {
                //Ket noi database
                final BookOfflineSQLite dbOffline = new BookOfflineSQLite(this, "OfflineBook.sqlite", null, 1);
                contentArrayList = new ArrayList<Content>();
                Cursor cursor = dbOffline.Getdata("select * from chap where idchap='" + chapterId + "'");
                Log.d(TAG, "onCreate: sql: " + "select * from chap where idchap='" + chapterId + "'");
                Log.d(TAG, "onCreate: count " + cursor.getCount());
                while (cursor.moveToNext()) {
                    ContentOffline contentOffline = new ContentOffline();
                    contentOffline.setIdchap(cursor.getString(0));
                    contentOffline.setChapnum(cursor.getString(1));
                    contentOffline.setImg(cursor.getBlob(2));
                    Log.d(TAG, "onCreate: contentOffline " + JsonUtils.encode(contentOffline));
                    contentArrayList.add(contentOffline);
                }
                recyclerView = findViewById(R.id.recyclerRead);

                //get value từ Adapter
                mAdapter = new ReadAdapter(this, contentArrayList);

                //tạo layout Horizontal
                layoutManager = new LinearLayoutManager(this, layoutManager.HORIZONTAL, false);

                //set các thông số cho recyclerView
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(mAdapter);


            } else {
                contentDatabase = FirebaseDatabase.getInstance().getReference().child("contents").child(chapterId);

                contentDatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Iterable<DataSnapshot> nodeChild = dataSnapshot.getChildren();
                        for (DataSnapshot dataSnapshot1 : nodeChild) {
                            final Content content = dataSnapshot1.getValue(Content.class);

                            contentArrayList.add(content);
                        }
                        mAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        } else {
            if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                SharedPrefsUtils.setCurrentUserId(ReadActivity.this, userID);
                FirebaseUtils.getCurrentCotentRef().child(userID).child(bookId).addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(final DataSnapshot dataSnapshot, String s) {
                        if (dataSnapshot.exists()) {
                            Log.d(TAG, "onChildAdded: = dataSnapshot.getKey()" + dataSnapshot.getKey());
                            Log.d(TAG, "onChildAdded: = dataSnapshot.getValue(String.class)" + dataSnapshot.getValue(String.class));
                            FirebaseUtils.getContentRef().child(dataSnapshot.getValue(String.class)).addChildEventListener(new ChildEventListener() {
                                @Override
                                public void onChildAdded(DataSnapshot dataSnapshot1, String s) {
                                    Content content = dataSnapshot1.getValue(Content.class);
                                    contentArrayList.add(content);
                                    mAdapter.notifyDataSetChanged();
                                    if (content.getContentId().equals(dataSnapshot.getKey())) {
                                        recyclerView.scrollToPosition(content.getContentNumber() - 1);
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


                        } else {
                            Log.d(TAG, "onChildAdded: not exist=========");
                            FirebaseUtils.getChapterRef().child(bookId).addChildEventListener(new ChildEventListener() {
                                @Override
                                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                    Chapter chapter = dataSnapshot.getValue(Chapter.class);
                                    contentArrayList = new ArrayList<>();
                                    if (chapter.getChapterNumber() == 1) {
                                        Log.d(TAG, "onChildAdded: chaptrer 1======================");
                                        String chapterId = chapter.getChapterId();
                                        contentDatabase = FirebaseDatabase.getInstance().getReference().child("contents").child(chapterId);

                                        contentDatabase.addValueEventListener(new ValueEventListener() {

                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                Iterable<DataSnapshot> nodeChild = dataSnapshot.getChildren();
                                                for (final DataSnapshot dataSnapshot1 : nodeChild) {

                                                    final Content content = dataSnapshot1.getValue(Content.class);
                                                    contentArrayList.add(content);

                                                }
                                                mAdapter.notifyDataSetChanged();
                                            }

                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {

                                            }
                                        });
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
            } else {
                contentDatabase = FirebaseDatabase.getInstance().getReference().child("chapters").child(bookId);
                Log.d(TAG, "onCreate: else =======================================");
                contentDatabase.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        Chapter chapter = dataSnapshot.getValue(Chapter.class);

                        if (chapter.getChapterNumber() == 1) {
                            Log.d(TAG, "onChildAdded: chaptrer 1======================");
                            String chapterId = chapter.getChapterId();
                            contentDatabase = FirebaseDatabase.getInstance().getReference().child("contents").child(chapterId);

                            contentDatabase.addValueEventListener(new ValueEventListener() {

                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    Iterable<DataSnapshot> nodeChild = dataSnapshot.getChildren();
                                    for (final DataSnapshot dataSnapshot1 : nodeChild) {
                                        final Content content = dataSnapshot1.getValue(Content.class);
                                        contentArrayList.add(content);
                                        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                                            userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                            Log.d(TAG, "onChildAdded: +++++" + userID);
                                            FirebaseUtils.getCurrentCotentRef().child(userID).child(bookId).addChildEventListener(new ChildEventListener() {
                                                @Override
                                                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                                    Log.d(TAG, "onChildAdded: +++++" + dataSnapshot.getKey());
                                                    Log.d(TAG, "onChildAdded: -----" + dataSnapshot1.getKey());
                                                    Log.d(TAG, "onChildAdded: *****" + dataSnapshot.getKey().equals(dataSnapshot1.getKey()));
                                                    if (dataSnapshot.getKey().equals(dataSnapshot1.getKey())) {
                                                        Log.d(TAG, "onChildAdded: =" + dataSnapshot.getKey());
                                                        recyclerView.scrollToPosition(content.getContentNumber() - 1);

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
                                        }
                                    }
                                    mAdapter.notifyDataSetChanged();
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
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
            }


        }

    }

    public void setCurrentContent() {
        Log.d(TAG, "setCurrentContent: userId" + SharedPrefsUtils.getCurrentUserId(this));

        if (SharedPrefsUtils.getCurrentUserId(this) != null) {
            FirebaseUtils.getCurrentCotentRef().child(SharedPrefsUtils.getCurrentUserId(this)).child(bookId).removeValue();
            FirebaseUtils.getCurrentCotentRef().child(SharedPrefsUtils.getCurrentUserId(this)).child(bookId).child(SharedPrefsUtils.getCurrentContentId(this)).setValue(SharedPrefsUtils.getCurrentChapterId(this));
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: =================");
        setCurrentContent();

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: =================");
        setCurrentContent();
    }
}