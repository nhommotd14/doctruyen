package com.nhommot.doctruyen.ui.fragments;

import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.content.SharedPreferences;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nhommot.doctruyen.R;
import com.nhommot.doctruyen.models.Book;
import com.nhommot.doctruyen.utils.SharedPrefsUtils;

import static android.content.Context.MODE_PRIVATE;

public class ReviewFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    private DatabaseReference mDatabase;
    public TextView tvReview;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_review_fragment,null);
        tvReview = (TextView) view.findViewById(R.id.tvReview);
        setFontForTvReview();
        String bookId = SharedPrefsUtils.getCurrentBookId(this.getContext());

        mDatabase = FirebaseDatabase.getInstance().getReference("books").child(bookId);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Book book = dataSnapshot.getValue(Book.class);
                tvReview.setText(String.valueOf(book.getDescription()));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return view;
    }

    public void setFontForTvReview(){
        SharedPreferences pre = getActivity().getSharedPreferences("user_setting",MODE_PRIVATE);
        String fontChu = pre.getString("fontChu","At Most Sphere.ttf");
        AssetManager assetManager = getActivity().getAssets();
        Typeface typeface = Typeface.createFromAsset(assetManager,"fonts/"+fontChu);
        tvReview.setTypeface(typeface);
    }
}
