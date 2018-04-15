package com.nhommot.doctruyen.ui;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.SeekBar;

import com.nhommot.doctruyen.R;

public class SettingActivity extends AppCompatActivity {
    SeekBar lightBar;
    Window window;
    ContentResolver contentResolver;
    int sBrightness;
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
    }

    private void addControls() {
        lightBar = findViewById(R.id.lightBar);
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
