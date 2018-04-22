package com.nhommot.doctruyen.ui.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.nhommot.doctruyen.R;
import com.nhommot.doctruyen.models.Book;
import com.nhommot.doctruyen.models.Chapter;
import com.nhommot.doctruyen.ui.adapters.ChapterAdapter;
import com.nhommot.doctruyen.utils.SharedPrefsUtils;

/**
 * Created by Huy on 4/12/2018.
 */

public class ChapterFragment extends Fragment {
    private final String TAG = "ChaptperFragment";
    private ChapterAdapter mAdapter;
    private String bookId;
    private ListView listChapters;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_chapters, container, false);
        listChapters = rootView.findViewById(R.id.listChapters);
//        Get current book id
        String currentBookId = SharedPrefsUtils.getCurrentBookId(this.getContext());
        if (currentBookId != null){

        }

        return rootView;
    }
}
