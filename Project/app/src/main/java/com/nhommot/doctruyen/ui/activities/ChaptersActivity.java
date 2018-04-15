package com.nhommot.doctruyen.ui;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.nhommot.doctruyen.R;
import com.nhommot.doctruyen.database.ChapterSQLiteManager;
import com.nhommot.doctruyen.database.DatabaseSQLiteHelper;
import com.nhommot.doctruyen.models.Chapter;
import com.nhommot.doctruyen.utils.JsonUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ChaptersActivity extends AppCompatActivity {
    private ArrayList<String> data = new ArrayList<>();
    private ListView lvChapters;
    private final String TAG = "ChaptetsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapters);
        setControl();
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_checked, data);
        lvChapters.setAdapter(adapter);
        for (int i = 0; i < 10; i++){
            data.add("Chapter " + i);
        }
        adapter.notifyDataSetChanged();
        test();
    }
    public void test(){
//        Create db for the first time
        try {
            DatabaseSQLiteHelper db = new DatabaseSQLiteHelper(this);
            db.createDatabase();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        Get chapters
        ChapterSQLiteManager mDbHelper = new ChapterSQLiteManager(this);
        mDbHelper.open();
        Log.d(TAG, "test: opened ===================");
        List<Chapter> chapters = mDbHelper.getAll(0);
        Log.d(TAG, "test: " + JsonUtils.encode(chapters));
        mDbHelper.close();
    }
    public void setControl(){
        lvChapters = findViewById(R.id.lvChapters);
    }
}
