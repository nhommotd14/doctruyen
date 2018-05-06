package com.nhommot.doctruyen.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nhommot.doctruyen.R;
import com.nhommot.doctruyen.models.BookOffline;
import com.nhommot.doctruyen.ui.activities.ReviewActivity;
import com.nhommot.doctruyen.utils.SharedPrefsUtils;

import java.util.List;


/**
 * Created by Huy on 4/22/2018.
 */

public class AListOfflineBookAdapter extends RecyclerView.Adapter<AListOfflineBookAdapter.BookOfflineViewHolder> {
    private final String TAG = "AListOfflineBookAdapter";
    private List<BookOffline> bookOfflines;

    private Context mContext;


    public AListOfflineBookAdapter(Context context, List<BookOffline> bookOfflines) {
        this.bookOfflines = bookOfflines;
        this.mContext = context;
    }

    @Override
    public BookOfflineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        TODO: replace item_chapters to layout list book @Quang
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_downloaded_book, parent, false);
        return new BookOfflineViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BookOfflineViewHolder holder, int position) {
        final BookOffline bookOffline = bookOfflines.get(position);

        if (bookOffline != null) {
            holder.textViewName.setText(bookOffline.getBookName());
            holder.textViewAuthor.setText(bookOffline.getBookAuthor());
            Bitmap bitmap = BitmapFactory.decodeByteArray(bookOffline.getImg(), 0, bookOffline.getImg().length);
            holder.imageViewHinh.setImageBitmap(bitmap);
        }
        holder.setItemClickListener(new AListOfflineBookAdapter.ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
//                Toast.makeText(mContext, " "+favourites.get(position), Toast.LENGTH_SHORT).show();
                SharedPrefsUtils.setCurrentBookId(mContext, bookOfflines.get(position).getBookId());
                Intent intent = new Intent(mContext, ReviewActivity.class);
//                intent.putExtra("bookOffline",);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookOfflines.size();
    }

    class BookOfflineViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textViewName;
        TextView textViewAuthor;
        ImageView imageViewHinh;
        Bitmap bitmap;
        TextView textFavouritename;
        private AListOfflineBookAdapter.ItemClickListener itemClickListener;


        public BookOfflineViewHolder(View itemView) {
            super(itemView);
            textViewName = (TextView) itemView.findViewById(R.id.textViewTenTruyen);
            textViewAuthor = (TextView) itemView.findViewById(R.id.textViewTacgia);
            imageViewHinh = (ImageView) itemView.findViewById(R.id.imageViewDownloadedBook);
            itemView.setOnClickListener(this);
        }

        public void setItemClickListener(AListOfflineBookAdapter.ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onClick(view, getAdapterPosition(), false);
        }
    }

    public interface ItemClickListener {
        void onClick(View view, int position, boolean isLongClick);
    }
}
