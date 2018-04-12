package com.nhommot.doctruyen.ui.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nhommot.doctruyen.R;

/**
 * Created by Huy on 4/12/2018.
 */

public class ChapterFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_chapters, container, false);


        return rootView;
    }
}
