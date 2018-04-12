package com.nhommot.doctruyen.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.nhommot.doctruyen.Constants;
import com.nhommot.doctruyen.R;
import com.nhommot.doctruyen.models.Chapter;

import java.util.ArrayList;

/**
 * Created by Huy on 4/11/2018.
 */

public class ChapterAdapter extends BaseAdapter {
    private final String TAG = "ChapterAdapter";
    private final Context mContext;

    private DatabaseReference mChapterRef;
    private ArrayList<Chapter> mData;


    public ChapterAdapter(Context mContext, ArrayList<Chapter> mData) {
        this.mContext = mContext;
        this.mData = mData;
        mChapterRef = FirebaseDatabase.getInstance().getReference(Constants.CHAPTER_PATH);
    }

    public void addChapter(final Chapter chapter) {
        Query query = mChapterRef.child(chapter.getBookId()).orderByChild("chapterName").equalTo(chapter.getChapterName());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onDataChange: " + dataSnapshot.getKey());
                if (dataSnapshot.exists()) {
                    Toast.makeText(mContext, "Da co chapter nay roi", Toast.LENGTH_LONG).show();
                } else {
                    String chapterId = mChapterRef.push().getKey();
                    chapter.setChapterId(chapterId);
                    mChapterRef.child(chapterId).setValue(chapter);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }



    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(mContext);
            view = inflater.inflate(R.layout.item_chapters, viewGroup, false);
            viewHolder.tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            viewHolder.tvDateTime = (TextView) view.findViewById(R.id.tvDateTime);
            viewHolder.img = (ImageView) view.findViewById(R.id.img_icon_chapter);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.tvTitle.setText(mData.get(i).getChapterName());
        return view;
    }

    private static class ViewHolder {
        private TextView tvTitle;
        private TextView tvDateTime;
        private TextView tvCategory;
        private ImageView img;
    }


}
