package com.nhommot.doctruyen.ui.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.nhommot.doctruyen.R;
import com.nhommot.doctruyen.comment_risk;
import com.nhommot.doctruyen.ui.fragments.ReviewFragment;
import com.nhommot.doctruyen.ui.adapters.TabAdapter;
import com.nhommot.doctruyen.ui.fragments.ChapterFragment;
import com.nhommot.doctruyen.ui.fragments.ReviewFragment;

public class ReviewActivity extends AppCompatActivity {
    private TabLayout tabLayout;
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
        adapter.addFragment(new ReviewFragment(), "Review");
        adapter.addFragment(new ChapterFragment(),"Chapter");
        adapter.addFragment(new comment_risk(),"Comment");

        viewPager.setAdapter(adapter);
        viewPager.getOffscreenPageLimit();
    }
}


