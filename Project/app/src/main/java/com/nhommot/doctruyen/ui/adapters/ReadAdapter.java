package com.nhommot.doctruyen.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nhommot.doctruyen.R;
import com.nhommot.doctruyen.models.Content;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by hunte on 5/5/2018.
 */

public class ReadAdapter extends RecyclerView.Adapter<ReadAdapter.ContentViewHolder>{

    ArrayList<Content> contentArrayList;


    public ReadAdapter(ArrayList<Content> contentArrayList) {
        this.contentArrayList = contentArrayList;

    }

    @Override
    public ContentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_read,parent, false);

        return new ContentViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ContentViewHolder holder, int position) {
        holder.imgLeft.setVisibility(View.INVISIBLE);
        holder.imgLeft.setEnabled(false);

        holder.imgRight.setVisibility(View.INVISIBLE);
        holder.imgRight.setEnabled(false);

        
        Content content=contentArrayList.get(position);

        String imgUrl=content.getSrc();

        Picasso.with(holder.img.getContext()).load(imgUrl).into(holder.img);
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
