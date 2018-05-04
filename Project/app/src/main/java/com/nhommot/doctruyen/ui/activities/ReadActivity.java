package com.nhommot.doctruyen.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.ViewFlipper;

import com.nhommot.doctruyen.R;

public class ReadActivity extends AppCompatActivity {

    float toadox1, toadox2;
    ViewFlipper viewFillper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);

        viewFillper = (ViewFlipper) findViewById(R.id.viewFlipper);
        viewFillper.setInAnimation(ReadActivity.this,android.R.anim.fade_in);
        viewFillper.setOutAnimation(ReadActivity.this,android.R.anim.fade_out);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //Toast.makeText(ReadActivity.this, "Bạn đã chạm vào màn hình", Toast.LENGTH_SHORT).show();
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN :
                toadox1 = event.getX(); break;
            case MotionEvent.ACTION_UP :
                toadox2 = event.getX();
                if(toadox2 > toadox1)
                {
                    //prev
                    viewFillper.showPrevious();
                }else{
                    //next
                    viewFillper.showNext();
                }
        }
        return super.onTouchEvent(event);
    }
}
