package com.nhommot.doctruyen.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nhommot.doctruyen.R;
import com.nhommot.doctruyen.models.Content;
import com.nhommot.doctruyen.utils.SharedPrefsUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by hunte on 5/5/2018.
 */

public class ReadAdapter extends RecyclerView.Adapter<ReadAdapter.ContentViewHolder>{
    private static final String TAG = "ReadAdapter";
    ArrayList<Content> contentArrayList;
    Context context;
    private float x1,x2;
    static final int MIN_DISTANCE = 150;


    public ReadAdapter(Context context, ArrayList<Content> contentArrayList) {
        this.contentArrayList = contentArrayList;
        this.context = context;

    }

    @Override
    public ContentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_read,parent, false);

        return new ContentViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ContentViewHolder holder, final int position) {
        holder.imgLeft.setVisibility(View.INVISIBLE);
        holder.imgLeft.setEnabled(false);
        holder.imgRight.setVisibility(View.INVISIBLE);
        holder.imgRight.setEnabled(false);

        final Content content=contentArrayList.get(position);

        String imgUrl=content.getSrc();

        Picasso.get().load(imgUrl).into(holder.img);

        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("123","=============" + position);
                SharedPrefsUtils.setCurrentContentId(context,content.getContentId());
            }
        });

        holder.img.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                SharedPrefsUtils.setCurrentContentId(context,content.getContentId());
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return contentArrayList.size();
    }

    public class ContentViewHolder extends RecyclerView.ViewHolder{
        ImageView img, imgLeft, imgRight;
        public ContentViewHolder(View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.imgRead);
            imgLeft=itemView.findViewById(R.id.imgLeft);
            imgRight=itemView.findViewById(R.id.imgRight);
        }
    }
}
