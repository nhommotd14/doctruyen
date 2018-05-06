package com.nhommot.doctruyen.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.nhommot.doctruyen.R;
import com.nhommot.doctruyen.models.Book;
import com.nhommot.doctruyen.ui.adapters.DetailAdapter;

import java.util.ArrayList;

public class Detail extends AppCompatActivity {
    public RecyclerView recyclerView;
    DetailAdapter adapter;
    String type;
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
       ArrayList<Book> books=new ArrayList<>();
        return books;
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
