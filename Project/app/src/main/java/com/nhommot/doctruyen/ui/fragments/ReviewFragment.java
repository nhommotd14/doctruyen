package com.nhommot.doctruyen.ui.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nhommot.doctruyen.R;
import com.nhommot.doctruyen.database.BookOfflineSQLite;
import com.nhommot.doctruyen.models.Book;
import com.nhommot.doctruyen.models.BookOffline;
import com.nhommot.doctruyen.utils.SharedPrefsUtils;

import static android.content.Context.MODE_PRIVATE;

public class ReviewFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private DatabaseReference mDatabase;
    private TextView tvReview;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_review_fragment, null);
        tvReview = (TextView) view.findViewById(R.id.tvReview);
        setFontForTvReview();
        String bookId = SharedPrefsUtils.getCurrentBookId(this.getContext());
        if (SharedPrefsUtils.getOfflineState(getContext())) {
            final BookOfflineSQLite dbOffline = new BookOfflineSQLite(getContext(), "OfflineBook.sqlite", null, 1);
            Cursor cursor = dbOffline.Getdata("select * from bookoff where id='" + bookId + "'");
            BookOffline bookOffline = null;
            while (cursor.moveToNext()) {
                bookOffline = new BookOffline(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getBlob(4),
                        cursor.getString(5),
                        cursor.getDouble(6)
                );
            }
            tvReview.setText(bookOffline.getDescription());
        }else{
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
        }
        return view;
    }

    public void setFontForTvReview() {
        SharedPreferences pre = getActivity().getSharedPreferences("user_setting", MODE_PRIVATE);
        String fontChu = pre.getString("fontChu", "times.ttf");
        AssetManager assetManager = getActivity().getAssets();
        Typeface typeface = Typeface.createFromAsset(assetManager, "fonts/" + fontChu);
        tvReview.setTypeface(typeface);
    }
}
