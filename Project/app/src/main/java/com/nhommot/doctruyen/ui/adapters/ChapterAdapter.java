package com.nhommot.doctruyen.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import com.nhommot.doctruyen.ui.activities.ReadActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Huy on 4/11/2018.
 */

public class ChapterAdapter extends RecyclerView.Adapter<ChapterAdapter.ChapterViewHolder> {
    private final String TAG = "ChapterAdapter";
    private List<Chapter> chapters;
    private Context mContext;


    public ChapterAdapter(Context context, List<Chapter> chapters) {
        this.chapters = chapters;
        this.mContext = context;
    }

    @Override
    public ChapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chapters, parent, false);
        return new ChapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ChapterViewHolder holder, int position) {
        Chapter chapter = chapters.get(position);
        holder.textChaptername.setText(chapter.getChapterName());
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
//                Toast.makeText(mContext, " "+chapters.get(position), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mContext, ReadActivity.class);
                intent.putExtra("chapter", chapters.get(position));
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return chapters.size();
    }

    class ChapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView textChaptername;
        private ItemClickListener itemClickListener;


        public ChapterViewHolder(View itemView) {
            super(itemView);
            Log.d(TAG, "ChapterViewHolder: ");
            textChaptername = itemView.findViewById(R.id.tvChapterName);
            itemView.setOnClickListener(this);
        }
        public void setItemClickListener(ItemClickListener itemClickListener)
        {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onClick(view,getAdapterPosition(),false);
        }
    }
    public interface ItemClickListener {
        void onClick(View view, int position,boolean isLongClick);
    }

}