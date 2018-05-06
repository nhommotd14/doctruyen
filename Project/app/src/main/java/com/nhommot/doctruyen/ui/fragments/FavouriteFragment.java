package com.nhommot.doctruyen.ui.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.nhommot.doctruyen.R;
import com.nhommot.doctruyen.models.Book;
import com.nhommot.doctruyen.models.Chapter;
import com.nhommot.doctruyen.ui.activities.MainActivity;
import com.nhommot.doctruyen.ui.adapters.ChapterAdapter;
import com.nhommot.doctruyen.ui.adapters.FavouriteAdapter;
import com.nhommot.doctruyen.ui.adapters.SimpleDividerItemDecoration;
import com.nhommot.doctruyen.ui.adapters.Snap;
import com.nhommot.doctruyen.ui.adapters.SnapAdapter;
import com.nhommot.doctruyen.utils.FirebaseUtils;
import com.nhommot.doctruyen.utils.JsonUtils;
import com.nhommot.doctruyen.utils.SharedPrefsUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Huy on 4/22/2018.
 */

public class FavouriteFragment extends Fragment {
    private final String TAG = "FavouriteFragment";
    private FavouriteAdapter mAdapter;
    private SnapAdapter snapAdapter;
    List<Book> result;
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        snapAdapter = new SnapAdapter();

//        Get current user id
        String userId = "4mPxG86sC0OuSalGRDeR3XBU3Uh2";
        result = new ArrayList<>();
//        TODO: replace favourite_recycle_view to ...ma
        recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this.getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(this.getContext()));
        mAdapter = new FavouriteAdapter(this.getContext(), result);

        FirebaseUtils.getFavouriteRef().child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, Boolean> favourites = (Map) dataSnapshot.getValue();
                Log.d(TAG, "onDataChange: " + JsonUtils.encode(favourites));
                for (Map.Entry<String, Boolean> entry : favourites.entrySet()) {
                    System.out.println(entry.getKey() + "/" + entry.getValue());
                    FirebaseUtils.getBookRef().child(entry.getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Book book = dataSnapshot.getValue(Book.class);
                            result.add(book);
                            mAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        ArrayList<Book> books = new ArrayList<>();
        books.add(new Book("Naruto","Thikute","https://firebasestorage.googleapis.com/v0/b/doctruyen-697d3.appspot.com/o/books%2FbookRandomStr1%2Fpreview.jpeg?alt=media&token=158efc95-8b3a-41de-8790-04387dd006af",12334));
        snapAdapter.addSnap(new Snap(1,"TOP 10",books));
        recyclerView.setAdapter(snapAdapter);
        return rootView;
    }
}
