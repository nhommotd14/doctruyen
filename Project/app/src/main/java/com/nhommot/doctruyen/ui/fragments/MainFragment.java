package com.nhommot.doctruyen.ui.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.nhommot.doctruyen.R;
import com.nhommot.doctruyen.models.Book;
<<<<<<< HEAD
import com.nhommot.doctruyen.models.BookCompare;
import com.nhommot.doctruyen.models.Chapter;
import com.nhommot.doctruyen.models.Type;
import com.nhommot.doctruyen.ui.adapters.ChapterAdapter;
import com.nhommot.doctruyen.ui.adapters.DetailAdapter;
=======
>>>>>>> 57a0a51c498a8f8a4770a3502a0089350406e0ef
import com.nhommot.doctruyen.ui.adapters.MainAdapter;
import com.nhommot.doctruyen.ui.adapters.SimpleDividerItemDecoration;
import com.nhommot.doctruyen.ui.adapters.Snap;
import com.nhommot.doctruyen.ui.adapters.SnapAdapter;
import com.nhommot.doctruyen.utils.FirebaseUtils;
import com.nhommot.doctruyen.utils.JsonUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MainFragment extends Fragment {
    private final String TAG = "MainFragment";
    private MainAdapter mAdapter;
    private SnapAdapter snapAdapter;
    private ArrayList<Type> types;
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
        getBooks();
        getType();
        //recyclerView.addItemDecoration(new SimpleDividerItemDecoration(this.getContext()));
        snapAdapter= new SnapAdapter(container.getContext());
        setupSnap();
        recyclerView.setAdapter(snapAdapter);
        return rootView;

    }
    public  void setupSnap()
    {
        ArrayList<Book> books= new ArrayList<>();

        Collections.sort(result,new BookCompare());
        snapAdapter.addSnap(new Snap(1,"Top truyện được đọc nhiều nhất ",result));
        snapAdapter.addSnap(new Snap(1,"Truyện mới cập nhật",result));



    }
    public void getBooks()
    {
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
    }
    public void getType()
    {
        types = new ArrayList<>();
      FirebaseUtils.getTypeRef().addChildEventListener(new ChildEventListener() {
          @Override
          public void onChildAdded(DataSnapshot dataSnapshot, String s) {
              final Type type= dataSnapshot.getValue(Type.class);


             new Thread(new Runnable() {
                 @Override
                 public void run() {
                     List<Book> books = new ArrayList<>();
                     for(Book book:result)
                     {
                          if(book.getTypes().get(book.getName()))
                          {
                              books.add(book);
                          }
                     }
                     snapAdapter.addSnap(new Snap(1,type.getName(),result));
                     snapAdapter.notifyDataSetChanged();
                 }
             }).start();


              snapAdapter.notifyDataSetChanged();
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
