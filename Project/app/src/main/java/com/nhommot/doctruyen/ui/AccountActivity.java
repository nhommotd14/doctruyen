package com.nhommot.doctruyen.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.nhommot.doctruyen.R;

public class AccountActivity extends AppCompatActivity {
    TextView tvCaiDat;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.taikhoan_layout);
        addControls();
        addEvents();
    }

    private void addEvents() {
        tvCaiDat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AccountActivity.this, SettingActivity.class);
                startActivity(intent);
            }
        });
    }

    private void addControls() {
        tvCaiDat = findViewById(R.id.tvCaiDat);
    }
}
