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
import com.nhommot.doctruyen.ui.adapters.ChapterAdapter;
import com.nhommot.doctruyen.ui.adapters.MainAdapter;
import com.nhommot.doctruyen.ui.adapters.SimpleDividerItemDecoration;
import com.nhommot.doctruyen.ui.adapters.Snap;
import com.nhommot.doctruyen.ui.adapters.SnapAdapter;
import com.nhommot.doctruyen.utils.FirebaseUtils;
import com.nhommot.doctruyen.utils.JsonUtils;
import com.nhommot.doctruyen.utils.SharedPrefsUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainFragment extends Fragment {
    private final String TAG = "MainFragment";
    private MainAdapter mAdapter;
    private SnapAdapter snapAdapter;

    List<Book> result;
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        result = new ArrayList<>();
//        TODO: replace main_recycle_view to ...
        recyclerView = rootView.findViewById(R.id.recyclerView);
       recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this.getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(this.getContext()));
        snapAdapter= new SnapAdapter(container.getContext());
        snapAdapter.addSnap(new Snap(1,"Truyện mới cập nhật",result));
        snapAdapter.addSnap(new Snap(1,"Truyện nổi bật trong tuần ",result));
        snapAdapter.addSnap(new Snap(1,"Phiêu lưu",result));
        snapAdapter.addSnap(new Snap(1,"Kinh dị",result));
        snapAdapter.addSnap(new Snap(1,"Hài hước",result));
        recyclerView.setAdapter(snapAdapter);
        FirebaseUtils.getLatestRef().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, Boolean> latests = (Map) dataSnapshot.getValue();
                Log.d(TAG, "onDataChange: " + JsonUtils.encode(latests));
                for (Map.Entry<String, Boolean> entry : latests.entrySet()) {
                    System.out.println(entry.getKey() + "/" + entry.getValue());
                    FirebaseUtils.getBookRef().child(entry.getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Book book = dataSnapshot.getValue(Book.class);
                            result.add(book);
                            Log.d(TAG, "onDataChange: " + dataSnapshot.getValue());
                            snapAdapter.notifyDataSetChanged();
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

        return rootView;
    }

}
