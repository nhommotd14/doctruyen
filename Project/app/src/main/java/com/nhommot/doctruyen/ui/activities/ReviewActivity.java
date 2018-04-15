package com.nhommot.doctruyen.ui.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.nhommot.doctruyen.R;
import com.nhommot.doctruyen.adapter.TabAdapter;
import com.nhommot.doctruyen.comment_risk;
import com.nhommot.doctruyen.review_fragment;

public class ReviewActivity extends AppCompatActivity {
    private String[] tabs = {"Giới thiệu", "Danh sách chương", "Comment"};
    private TabLayout tabLayout;
    public static int[] resourceId = {
            R.layout.activity_review_fragment,
            R.layout.item_chapters,
            R.layout.activity_comment,
    };
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPage(viewPager);
        tabLayout.setupWithViewPager(viewPager);


    }

    private void setupViewPage(ViewPager viewPager) {
        TabAdapter adapter = new TabAdapter(getSupportFragmentManager());
        adapter.addFragment(new review_fragment(), "Review");
        adapter.addFragment(new comment_risk(),"Comment");
        viewPager.setAdapter(adapter);
        viewPager.getOffscreenPageLimit();
    }
}


