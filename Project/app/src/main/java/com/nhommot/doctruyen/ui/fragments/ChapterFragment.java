package com.nhommot.doctruyen.ui.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.nhommot.doctruyen.R;
import com.nhommot.doctruyen.models.Chapter;
import com.nhommot.doctruyen.ui.adapters.ChapterAdapter;

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
//        Test add chapters
        Chapter chapter = new Chapter();
        chapter.setBookId("-L9pMk60_ZXDFqNErcI-");
        chapter.setChapterName("Chapter 1: End of the Earth");
        mAdapter = new ChapterAdapter(this, bookId);
        listChapters.setAdapter(mAdapter);

        return rootView;
    }
}
