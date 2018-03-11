package com.example.thuanpro.commentlayout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> dsBinhLuan;
    ArrayAdapter<String> dsBinhLuanAdapter;
    ListView lvdsBinhLuan;

    EditText txtBinhLuan;
    Button btnPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();
        addEvents();
    }

    private void addEvents() {
        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xuLyPost();
            }
        });
    }

    private void xuLyPost() {
        String comment=txtBinhLuan.getText().toString();
        dsBinhLuan.add(comment);
        dsBinhLuanAdapter.notifyDataSetChanged();
        txtBinhLuan.setText("");
        txtBinhLuan.requestFocus();
    }

    private void addControls() {
        lvdsBinhLuan = (ListView) findViewById(R.id.lvdsBinhLuan);
        dsBinhLuan = new ArrayList<>();



        dsBinhLuanAdapter =new ArrayAdapter<String>(MainActivity.this,    //man hinh su dung adapter nay
                android.R.layout.simple_list_item_1,       // nguon giao dien
                dsBinhLuan);          // nguon du lieu truyen vao

        lvdsBinhLuan.setAdapter(dsBinhLuanAdapter);
        txtBinhLuan = (EditText) findViewById(R.id.txtBinhLuan);
        btnPost = (Button) findViewById(R.id.btnPost);


    }
}
