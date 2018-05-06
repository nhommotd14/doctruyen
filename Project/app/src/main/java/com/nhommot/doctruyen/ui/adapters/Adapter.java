package com.nhommot.doctruyen.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.nhommot.doctruyen.R;
import com.nhommot.doctruyen.models.Author;
import com.nhommot.doctruyen.models.Book;
import com.nhommot.doctruyen.ui.activities.Detail;
import com.nhommot.doctruyen.ui.activities.ReviewActivity;
import com.nhommot.doctruyen.utils.FirebaseUtils;
import com.nhommot.doctruyen.utils.SharedPrefsUtils;
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
    public interface OnItemClickListener {
        void OnItemClick(int position);

    }
    public void setOnItemClickListener(OnItemClickListener listener)
    {
        mItemClick=listener;
    }
    public Adapter(Context context, boolean horizontal, boolean pager, List<Book> apps) {
        mHorizontal = horizontal;
        mApps = apps;
        mPager = pager;
        this.context=context;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType)
    {

        ViewHolder viewHolder = new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter, parent, false));

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Book app = mApps.get(position);
        Picasso.get().load(app.getImgPreview()).into(holder.imageView);
        holder.nameTextView.setText(app.getName());
        FirebaseUtils.getAuthorRef().child(app.getAuthor()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Author author = dataSnapshot.getValue(Author.class);
                holder.authorTextView.setText(author.getName());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //holder.ratingTextView.setText(String.valueOf(app.getRating()));
        holder.viewTimeTextView.setText(String.valueOf(app.getViews()));

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("AVC", "onClick: " + app.getBookId());
                Log.d("AVC", "onClick: context " + context);

                SharedPrefsUtils.setCurrentBookId(context, app.getBookId());
                Intent intent = new Intent(context,ReviewActivity.class);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return mApps.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public LinearLayout linearLayout;
        public ImageView imageView;
        public TextView nameTextView;
        public TextView authorTextView;
        public TextView ratingTextView;
        public TextView viewTimeTextView;
        private Adapter.ItemClickListener itemClickListener;
        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            nameTextView = (TextView) itemView.findViewById(R.id.nameTextView);
            authorTextView =(TextView) itemView.findViewById(R.id.authorTextView);
            ratingTextView = (TextView) itemView.findViewById(R.id.ratingTextView);
            viewTimeTextView = (TextView) itemView.findViewById(R.id.viewTimeTextView);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linear);

        }
        public void setItemClickListener(Adapter.ItemClickListener itemClickListener)
        {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v,getAdapterPosition(),false);
        }
    }
    public interface ItemClickListener {
        void onClick(View view, int position,boolean isLongClick);
    }

}