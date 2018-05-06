package com.nhommot.doctruyen.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nhommot.doctruyen.R;
import com.nhommot.doctruyen.models.Comment;
import com.nhommot.doctruyen.ui.activities.LoginActivity;
import com.nhommot.doctruyen.ui.adapters.CommentAdapter;
import com.nhommot.doctruyen.utils.AuthorUtils;
import com.nhommot.doctruyen.utils.FirebaseUtils;
import com.nhommot.doctruyen.utils.SharedPrefsUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CommentFragment extends Fragment {

    EditText edtComment;
    Button btnPost;
    RecyclerView rvComments;
    CommentAdapter mAdapter;
    List<Comment> mComments;

    public CommentFragment() {
        // Required empty public constructor
    }
    public static CommentFragment newInstance(String param1, String param2) {
        CommentFragment fragment = new CommentFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() == null){
            Intent intent = new Intent(getContext(), LoginActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_comment, container, false);

        addControls(view);
        addEvents();
        loadData();
        return view;
    }

    private void loadData() {

        String bookId = SharedPrefsUtils.getCurrentBookId(getContext());
        FirebaseUtils.getCommentRef().child(bookId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mComments.clear();
                for(DataSnapshot snapshotPost : dataSnapshot.getChildren()){
                    Comment comment = snapshotPost.getValue(Comment.class);
                    mComments.add(comment);
                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void addEvents() {
        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = edtComment.getText().toString();
                if(content.length() == 0) return;
                Comment comment = new Comment();
                comment.setContent(content);
                comment.setBookId(SharedPrefsUtils.getCurrentBookId(getContext()));
                comment.setUserId(AuthorUtils.getCurrentUserId());
                DatabaseReference databaseReference = FirebaseUtils.getCommentRef();
                Date date = new Date();
                comment.setCommentId(date.getTime()+"");
                databaseReference.child(comment.getBookId()).child(comment.getCommentId()).setValue(comment);
                edtComment.setText("");
            }
        });
    }

    private void addControls(View view) {
        edtComment = view.findViewById(R.id.edtComment);
        btnPost = view.findViewById(R.id.btnPost);
        rvComments = view.findViewById(R.id.rvComments);
        mComments = new ArrayList<>();
        mAdapter = new CommentAdapter(getContext(), mComments);
        rvComments.setLayoutManager(new LinearLayoutManager(getContext()));
        rvComments.setAdapter(mAdapter);
    }
}
