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

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder> {
    private final String TAG = "MainAdapter";
    private List<Book> latests;
    private Context mContext;


    public MainAdapter(Context context, List<Book> latests) {
        this.latests = latests;
        this.mContext = context;
    }

    @Override
    public MainAdapter.MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        TODO: replace item_chapters to layout list book @Quang
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chapters, parent, false);
        return new MainAdapter.MainViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MainAdapter.MainViewHolder holder, int position) {
        Book main = latests.get(position);
//        TODO: setText all items in layout list book @Quang
        holder.textMainname.setText(main.getName());
        holder.setItemClickListener(new MainAdapter.ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
//                Toast.makeText(mContext, " "+latests.get(position), Toast.LENGTH_SHORT).show();
                SharedPrefsUtils.setCurrentBookId(mContext, latests.get(position).getBookId());
                Intent intent = new Intent(mContext, ReviewActivity.class);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return latests.size();
    }

    class MainViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView textMainname;
        private MainAdapter.ItemClickListener itemClickListener;


        public MainViewHolder(View itemView) {
            super(itemView);
            Log.d(TAG, "MainViewHolder: ");
//            TODO: replace tvChapterName to tvName of layout list book @Quang
            textMainname = itemView.findViewById(R.id.tvChapterName);
            itemView.setOnClickListener(this);
        }
        public void setItemClickListener(MainAdapter.ItemClickListener itemClickListener)
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
