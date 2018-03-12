package com.nhommot.doctruyen;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ItemChapters extends AppCompatActivity {
    private ArrayList<String> data = new ArrayList<>();
    private ListView lvChapters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_chapters);
        setControl();
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_checked, data);
        lvChapters.setAdapter(adapter);
        for (int i = 0; i < 10; i++){
            data.add("Chapter " + i);
        }
        adapter.notifyDataSetChanged();

    }
    public void setControl(){
        lvChapters = findViewById(R.id.lvChapters);
    }
}
