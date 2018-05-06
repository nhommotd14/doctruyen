package nhommot.com.firebase.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import nhommot.com.firebase.R;

/**
 * Created by Nguyen Dung on 4/15/2018.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private ArrayList<String> mDataset;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView mTextView;
        public ViewHolder(View v){
            super(v);
            mTextView = (TextView)v.findViewById(R.id.tvContent);
        }
        public TextView getmTextView(){
            return mTextView;
        }
    }

    public MyAdapter(ArrayList<String> myDataset){
        mDataset = myDataset;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v =LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item,null);
        ViewHolder vh = new ViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.getmTextView().setText(mDataset.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
