package com.nhommot.doctruyen.ui.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.nhommot.doctruyen.R;
import com.nhommot.doctruyen.database.BookOfflineSQLite;
import com.nhommot.doctruyen.models.Book;
import com.nhommot.doctruyen.models.BookOffline;
import com.nhommot.doctruyen.models.Chapter;
import com.nhommot.doctruyen.models.ChapterOffline;
import com.nhommot.doctruyen.models.User;
import com.nhommot.doctruyen.ui.adapters.ChapterAdapter;
import com.nhommot.doctruyen.ui.adapters.SimpleDividerItemDecoration;
import com.nhommot.doctruyen.utils.FirebaseUtils;
import com.nhommot.doctruyen.utils.SharedPrefsUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Huy on 4/12/2018.
 */

public class ChapterFragment extends Fragment {
    private final String TAG = "ChaptperFragment";
    private ChapterAdapter mAdapter;

    List<Chapter> result;
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_chapters, container, false);

//        Get current book id
        String currentBookId = SharedPrefsUtils.getCurrentBookId(this.getContext());
//                String currentBookId = "bookRandomStr1";
        if (currentBookId != null){
            result = new ArrayList<>();
            recyclerView = rootView.findViewById(R.id.chapter_recycler_view);
            recyclerView.setHasFixedSize(true);
            LinearLayoutManager llm = new LinearLayoutManager(this.getContext());
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(llm);
            recyclerView.addItemDecoration(new SimpleDividerItemDecoration(this.getContext()));
            mAdapter = new ChapterAdapter(this.getContext(), result);
            if (SharedPrefsUtils.getOfflineState(getContext())) {
                final BookOfflineSQLite dbOffline=new BookOfflineSQLite(this.getContext(),"OfflineBook.sqlite",null,1);

                //Ket noi database
                Cursor cursor=dbOffline.Getdata("select * from bookchapoff where idtruyen='"+currentBookId+"'");
                while (cursor.moveToNext()){
                    result.add(new Chapter(
                            cursor.getString(0),
                            cursor.getString(1),
                            cursor.getString(2)
                    ));
                }
                recyclerView.setAdapter(mAdapter);

            }else {
                recyclerView.setAdapter(mAdapter);
                FirebaseUtils.getChapterRef().child(currentBookId).addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        Log.d(TAG, "onChildAdded: KEY " + dataSnapshot.getKey());
                        Chapter chapter = dataSnapshot.getValue(Chapter.class);
                        result.add(chapter);
                        mAdapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

        }

        return rootView;
    }
}
