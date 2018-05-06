package com.nhommot.doctruyen.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.nhommot.doctruyen.R;
import com.nhommot.doctruyen.models.Book;
import com.nhommot.doctruyen.ui.adapters.DetailAdapter;
import com.nhommot.doctruyen.utils.FirebaseUtils;
import com.nhommot.doctruyen.utils.JsonUtils;

import java.util.ArrayList;
import java.util.Map;

public class Detail extends AppCompatActivity {
    public RecyclerView recyclerView;
    DetailAdapter adapter;
    String type;
    ArrayList<Book> result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        recyclerView =(RecyclerView) findViewById(R.id.myrecyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this,3));
        TextView title = (TextView) findViewById(R.id.snapTitle);
        Intent intent = getIntent();
        type = intent.getStringExtra("title");
        title.setText(type);
        setupAdapter();

    }
    private void setupAdapter() {
        ArrayList<Book> books = getBookByType(type);
        adapter = new DetailAdapter(books);
        recyclerView.setAdapter(adapter);
    }

    private ArrayList<Book> getBooks() {
       result= new ArrayList<>();
        FirebaseUtils.getLatestRef().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, Boolean> latests = (Map) dataSnapshot.getValue();
                Log.d("Mess", "onDataChange: " + JsonUtils.encode(latests));
                for (Map.Entry<String, Boolean> entry : latests.entrySet()) {
                    System.out.println(entry.getKey() + "/" + entry.getValue());
                    FirebaseUtils.getBookRef().child(entry.getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Book book = dataSnapshot.getValue(Book.class);
                            result.add(book);
                            Log.d("Mess", "onDataChange: " + dataSnapshot.getValue());
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return result;
    }
    private ArrayList<Book> getBookByType(String type)
    {
        ArrayList<Book> books = new ArrayList<>();
        for (Book i : getBooks())
        {
            if(i.getTypes().equals(type))
            {
                books.add(i);
            }
        }
        return books;
    }

}
