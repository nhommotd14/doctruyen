package com.nhommot.doctruyen.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nhommot.doctruyen.R;
import com.nhommot.doctruyen.models.Chapter;
import com.nhommot.doctruyen.models.Content;
import com.nhommot.doctruyen.ui.adapters.ReadAdapter;
import com.nhommot.doctruyen.utils.SharedPrefsUtils;

import java.util.ArrayList;

public class ReadActivity extends AppCompatActivity {
    private final String TAG = "ReadActivity";
    DatabaseReference contentDatabase;
    RecyclerView recyclerView;
    private final ArrayList<Content> contentArrayList = new ArrayList<>();
    private LinearLayoutManager layoutManager;
    private ReadAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);

        recyclerView = findViewById(R.id.recyclerRead);

        //get value từ Adapter
        mAdapter = new ReadAdapter(contentArrayList);

        //tạo layout Horizontal
        layoutManager = new LinearLayoutManager(this, layoutManager.HORIZONTAL,false);

        //set các thông số cho recyclerView
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);

//
        String bookId = SharedPrefsUtils.getCurrentBookId(this);


        //truy xuất vào database
        String chapterId = "";
        Intent intent = getIntent();
        if (intent.hasExtra("chapterId")) {
            chapterId = intent.getStringExtra("chapterId");
            Log.d(TAG, "onCreate: ==============="+ chapterId);
            chapterId = intent.getStringExtra("chapterId");
            contentDatabase = FirebaseDatabase.getInstance().getReference().child("contents").child(chapterId);

            contentDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Iterable<DataSnapshot> nodeChild = dataSnapshot.getChildren();
                    for (DataSnapshot dataSnapshot1:nodeChild){
                        Content content=dataSnapshot1.getValue(Content.class);
                        contentArrayList.add(content);
                    }
                    mAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        } else {
            Log.d(TAG, "============== " + bookId);
            contentDatabase = FirebaseDatabase.getInstance().getReference().child("chapters").child(bookId);
            Log.d(TAG, "onCreate: else =======================================");
            contentDatabase.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    Chapter chapter = dataSnapshot.getValue(Chapter.class);

                    if (chapter.getChapterNumber() == 1){
                        Log.d(TAG, "onChildAdded: chaptrer 1======================" );
                        String chapterId = chapter.getChapterId();
                        contentDatabase = FirebaseDatabase.getInstance().getReference().child("contents").child(chapterId);

                        contentDatabase.addValueEventListener(new ValueEventListener() {

                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                Iterable<DataSnapshot> nodeChild = dataSnapshot.getChildren();
                                for (DataSnapshot dataSnapshot1 : nodeChild){
                                    Content content = dataSnapshot1.getValue(Content.class);
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

}
