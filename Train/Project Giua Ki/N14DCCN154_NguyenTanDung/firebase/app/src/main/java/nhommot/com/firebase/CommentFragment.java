package nhommot.com.firebase;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class CommentFragment extends Fragment {
    private DatabaseReference mDatabase;
    private RecyclerView mRecycleView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<String> mDataset;
    private static final int DATASET_COUNT = 60;
    private ListView lv;
    private Button btnAdd;
    private EditText etComment;
    private String[] comment;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_comment, null);
        lv = (ListView) view.findViewById(R.id.lv);
        btnAdd = (Button) view.findViewById(R.id.btnAdd);
        etComment = (EditText) view.findViewById(R.id.etComment);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                writeComment(etComment.getText().toString());
                etComment.setText("");
            }
        });
        mDatabase = FirebaseDatabase.getInstance().getReference("N14DCCN154").child("Comment");
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, String> td = (HashMap<String, String>) dataSnapshot.getValue();
                ArrayList<String> contents = new ArrayList(td.values());

//                Log.d("testttttt:  ", contents.get(0) + "size: " + contents.size());
                comment = new String[contents.size()];

                contents.toArray(comment);
//                Log.d("comment:  ", comment[0] + "size: " + comment[0]);
                ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(), R.layout.support_simple_spinner_dropdown_item, comment);
                lv.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mDatabase.addValueEventListener(valueEventListener);

//        initDataset();
//        mRecycleView = (RecyclerView) view.findViewById(R.id.recyclerViewItem);
//        mRecycleView.setHasFixedSize(true);
//        mLayoutManager = new LinearLayoutManager(getActivity());
//        mRecycleView.setLayoutManager(mLayoutManager);
//        mAdapter = new MyAdapter(mDataset);
//        mRecycleView.setAdapter(mAdapter);

        return view;
    }

    private void initDataset() {
        mDataset = new ArrayList<>();
//        mDataset = new String[DATASET_COUNT];
        mDatabase = FirebaseDatabase.getInstance().getReference("N14DCCN154").child("Comment");
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, String> td = (HashMap<String, String>) dataSnapshot.getValue();
                ArrayList<String> contents = new ArrayList(td.values());

//                Log.d("testttttt:  ", contents.get(0) + "size: " + contents.size());

                mDataset = contents;
//                Log.d("testttttt:  ", mDataset.get(0) + "size: " + mDataset.size());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mDatabase.addValueEventListener(valueEventListener);
//        mDatabase.addChildEventListener(commentListener);
//        Log.d("testttttt:  ", mDataset.get(0) +"size : "+mDataset.size());

//        for (int i = 0; i < DATASET_COUNT; i++) {
//            mDataset[i] = "This is element #"+i;
//        }

    }

    private void writeComment(String comment) {
        mDatabase = FirebaseDatabase.getInstance().getReference("N14DCCN154");
        String key = mDatabase.child("Comment").push().getKey();
        mDatabase.child("Comment").child(key).setValue(comment);
    }

    ChildEventListener commentListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            String comment = dataSnapshot.getValue(String.class);
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            String newComment = dataSnapshot.getValue(String.class);
            String commentKey = dataSnapshot.getKey();
//            Log.d("testttttt:  ", commentKey + "size : " + newComment);

        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {
            String commentKey = dataSnapshot.getKey();

        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            String movedComment = dataSnapshot.getValue(String.class);
            String commentKey = dataSnapshot.getKey();
//            Log.d("testttttt:  ", commentKey + "size: " + commentListener);

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

}
