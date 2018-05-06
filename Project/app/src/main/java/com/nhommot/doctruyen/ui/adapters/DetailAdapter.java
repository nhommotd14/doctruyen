package com.nhommot.doctruyen.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nhommot.doctruyen.R;
import com.nhommot.doctruyen.models.Book;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.myViewHolder> {

    ArrayList<Book> books;


    public DetailAdapter(ArrayList<Book> books) {

        this.books = books;

    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.adapter, parent, false);
        myViewHolder holder = new myViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {
        Book book = books.get(position);
        Picasso.get().load(book.getImgPreview()).into(holder.imageView);
        holder.nameTextView.setText(book.getName());
        holder.authorTextView.setText(book.getAuthor());
        holder.ratingTextView.setText(String.valueOf(book.getRatings()));
        holder.viewTimeTextView.setText(String.valueOf(book.getViews()));
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView nameTextView;
        public TextView authorTextView;
        public TextView ratingTextView;
        public TextView viewTimeTextView;

        public myViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            nameTextView = (TextView) itemView.findViewById(R.id.nameTextView);
            authorTextView = (TextView) itemView.findViewById(R.id.authorTextView);
            ratingTextView = (TextView) itemView.findViewById(R.id.ratingTextView);
            viewTimeTextView = (TextView) itemView.findViewById(R.id.viewTimeTextView);
        }
    }
}
