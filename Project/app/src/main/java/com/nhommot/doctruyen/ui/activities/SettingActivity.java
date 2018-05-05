package com.nhommot.doctruyen.ui.activities;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.nhommot.doctruyen.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;



public class SettingActivity extends AppCompatActivity {
    SeekBar lightBar;
    Window window;
    ContentResolver contentResolver;
    int sBrightness;
    ArrayList<String> dsFont;
    RelativeLayout layoutFontChu,layoutPhucHoi;
    TextView tvFontChu,tvTenFont;
    SharedPreferences pre;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        addControls();
        addEvents();
    }

    private void addEvents() {
        lightBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (i <= 20) {

                    sBrightness = 20;
                } else {

                    sBrightness = i;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Settings.System.putInt(contentResolver,Settings.System.SCREEN_BRIGHTNESS,sBrightness);
                WindowManager.LayoutParams layoutParams = window.getAttributes();
                layoutParams.screenBrightness = sBrightness/(float)255;
                window.setAttributes(layoutParams);
            }
        });

        layoutFontChu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowAlertDialogListFont();
            }
        });
        layoutPhucHoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor edit = pre.edit();
                edit.clear();
                edit.commit();
                tvTenFont.setText("times.ttf (Mặc định) ");
                Toast.makeText(SettingActivity.this,"Phục hồi thành công",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void ShowAlertDialogListFont()
    {
        String[] arrFontName={""};
        AssetManager assetManager = getAssets();
        try {
            arrFontName=assetManager.list("fonts");
            dsFont.addAll(Arrays.asList(arrFontName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle("Fonts");
        final String[] finalArrFontName = arrFontName;
        dialogBuilder.setItems(arrFontName, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                String fontChu = finalArrFontName[item];
                SharedPreferences.Editor edit=pre.edit();
                edit.putString("fontChu", fontChu);
                edit.commit();
                tvTenFont.setText(fontChu);
                Toast.makeText(SettingActivity.this,fontChu,Toast.LENGTH_LONG).show();
            }
        });
        AlertDialog alertDialogObject = dialogBuilder.create();
        alertDialogObject.show();
    }

    private void addControls() {
        lightBar = findViewById(R.id.lightBar);
        layoutFontChu = findViewById(R.id.layoutFontChu);
        layoutPhucHoi = findViewById(R.id.layoutPhucHoi);
        tvFontChu = findViewById(R.id.tvFontChu);
        tvTenFont = findViewById(R.id.tvTenFont);
        dsFont = new ArrayList<>();

        pre=getSharedPreferences("user_setting", MODE_PRIVATE);
        tvTenFont.setText(pre.getString("fontChu", "times.ttf"));

        setLightBar();

    }

    private void setLightBar() {
        contentResolver = getContentResolver();
        window = getWindow();
        lightBar.setMax(255);
        lightBar.setKeyProgressIncrement(1);
        if (Settings.System.canWrite(getApplicationContext())) {
            try {
                sBrightness = Settings.System.getInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS);
            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
            }
            lightBar.setProgress(sBrightness);
        }
        else

        {
            Intent intent = new Intent(android.provider.Settings.ACTION_MANAGE_WRITE_SETTINGS);
            intent.setData(Uri.parse("package:" + this.getPackageName()));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }
}
