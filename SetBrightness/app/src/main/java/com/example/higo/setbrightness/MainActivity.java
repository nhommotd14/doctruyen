package com.example.higo.setbrightness;

import android.content.ContentResolver;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    SeekBar lightBar;
    TextView tvLight;
    Window window;
    ContentResolver contentResolver;
    int brightness;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lightBar = findViewById(R.id.lightbar);
        tvLight = findViewById(R.id.tvLight);
        contentResolver = getContentResolver();
        window = getWindow();
        lightBar.setMax(255);
        lightBar.setKeyProgressIncrement(1);
        try {
            brightness = Settings.System.getInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        lightBar.setProgress(brightness);
        lightBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(progress<=20){
                    brightness = 20;
                }
                else brightness=progress;
                float per = (brightness/(float)255)*100;
                tvLight.setText((int)per+"%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Settings.System.putInt(contentResolver,Settings.System.SCREEN_BRIGHTNESS,brightness);
                WindowManager.LayoutParams layoutParams = window.getAttributes();
                layoutParams.screenBrightness = brightness/(float)255;
                window.setAttributes(layoutParams);
            }
        });
    }
}
