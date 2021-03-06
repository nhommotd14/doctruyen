package com.nhommot.doctruyen.ui.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.nhommot.doctruyen.R;
import com.nhommot.doctruyen.models.Book;
import com.nhommot.doctruyen.models.Chapter;
import com.nhommot.doctruyen.ui.adapters.ChapterAdapter;
import com.nhommot.doctruyen.ui.adapters.FavouriteAdapter;
import com.nhommot.doctruyen.ui.adapters.SimpleDividerItemDecoration;
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

    List<Book> result;
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_favourite, container, false);

//        Get current user id



        result = new ArrayList<>();
//        TODO: replace favourite_recycle_view to ...
        recyclerView = rootView.findViewById(R.id.favourite_recycle_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this.getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(this.getContext()));
        mAdapter = new FavouriteAdapter(this.getContext(), result);
        recyclerView.setAdapter(mAdapter);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null){
            FirebaseUtils.getFavouriteRef().child(currentUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
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
        }
        return rootView;
    }
}