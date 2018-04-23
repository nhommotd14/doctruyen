package com.nhommot.doctruyen.ui.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nhommot.doctruyen.R;
import com.nhommot.doctruyen.models.Book;
import com.nhommot.doctruyen.ui.activities.ReadActivity;
import com.nhommot.doctruyen.ui.activities.ReviewActivity;
import com.nhommot.doctruyen.utils.SharedPrefsUtils;

import java.util.List;


/**
 * Created by Huy on 4/22/2018.
 */

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.FavouriteViewHolder> {
    private final String TAG = "FavouriteAdapter";
    private List<Book> favourites;
    private Context mContext;


    public FavouriteAdapter(Context context, List<Book> favourites) {
        this.favourites = favourites;
        this.mContext = context;
    }

    @Override
    public FavouriteAdapter.FavouriteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        TODO: replace item_chapters to layout list book @Quang
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chapters, parent, false);
        return new FavouriteAdapter.FavouriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FavouriteAdapter.FavouriteViewHolder holder, int position) {
        Book favourite = favourites.get(position);
//        TODO: setText all items in layout list book @Quang
        holder.textFavouritename.setText(favourite.getName());
        holder.setItemClickListener(new FavouriteAdapter.ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
//                Toast.makeText(mContext, " "+favourites.get(position), Toast.LENGTH_SHORT).show();
                SharedPrefsUtils.setCurrentBookId(mContext, favourites.get(position).getBookId());
                Intent intent = new Intent(mContext, ReviewActivity.class);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return favourites.size();
    }

    class FavouriteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView textFavouritename;
        private FavouriteAdapter.ItemClickListener itemClickListener;


        public FavouriteViewHolder(View itemView) {
            super(itemView);
            Log.d(TAG, "FavouriteViewHolder: ");
//            TODO: replace tvChapterName to tvName of layout list book @Quang
            textFavouritename = itemView.findViewById(R.id.tvChapterName);
            itemView.setOnClickListener(this);
        }
        public void setItemClickListener(FavouriteAdapter.ItemClickListener itemClickListener)
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
