package com.nhommot.doctruyen.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.nhommot.doctruyen.R;
import com.nhommot.doctruyen.models.Book;
import com.nhommot.doctruyen.ui.activities.Detail;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by delaroy on 2/10/17.
 */


public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private List<Book> mApps;
    private boolean mHorizontal;
    private boolean mPager;
    public OnItemClickListener mItemClick;
    public Context context;
    public interface OnItemClickListener
    {
        void OnItemClick(int position);

    }
    public void setOnItemClickListener(OnItemClickListener listener)
    {
        mItemClick=listener;
    }
    public Adapter( boolean horizontal, boolean pager, List<Book> apps) {
        mHorizontal = horizontal;
        mApps = apps;
        mPager = pager;this.context=context;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType)
    {

        ViewHolder viewHolder = new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter, parent, false));
        viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(parent.getContext(),Detail.class);
                parent.getContext().startActivity(intent);
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Book app = mApps.get(position);
        Picasso.get().load(app.getImgPreview()).into(holder.imageView);
        holder.nameTextView.setText(app.getName());
        holder.authorTextView.setText(app.getAuthor());
        //holder.ratingTextView.setText(String.valueOf(app.getRating()));
        holder.viewTimeTextView.setText(String.valueOf(app.getViews()));
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return mApps.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout linearLayout;
        public ImageView imageView;
        public TextView nameTextView;
        public TextView authorTextView;
        public TextView ratingTextView;
        public TextView viewTimeTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            nameTextView = (TextView) itemView.findViewById(R.id.nameTextView);
            authorTextView =(TextView) itemView.findViewById(R.id.authorTextView);
            ratingTextView = (TextView) itemView.findViewById(R.id.ratingTextView);
            viewTimeTextView = (TextView) itemView.findViewById(R.id.viewTimeTextView);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linear);

        }

    }

}