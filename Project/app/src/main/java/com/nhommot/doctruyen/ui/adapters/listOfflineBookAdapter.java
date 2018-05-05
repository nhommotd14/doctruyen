package com.nhommot.doctruyen.ui.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nhommot.doctruyen.R;
import com.nhommot.doctruyen.models.BookOffline;

import java.util.List;

public class listOfflineBookAdapter extends ArrayAdapter<BookOffline>{
    public listOfflineBookAdapter(@NonNull Context context, int resource, @NonNull List<BookOffline> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v=convertView;

        if(v==null){
            LayoutInflater vi;
            vi=LayoutInflater.from(getContext());
            v=vi.inflate(R.layout.activity_downloaded_book,null);
        }
        BookOffline book=getItem(position);

        if(book!=null){
            TextView textViewName=(TextView) v.findViewById(R.id.textViewTenTruyen);
            textViewName.setText(book.getBookName());
            TextView textViewAuthor=(TextView) v.findViewById(R.id.textViewTacgia);
            textViewAuthor.setText(book.getBookAuthor());
            ImageView imageViewHinh=(ImageView) v.findViewById(R.id.imageViewDownloadedBook);
            Bitmap bitmap = BitmapFactory.decodeByteArray(book.getImg(),0,book.getImg().length);
            imageViewHinh.setImageBitmap(bitmap);
        }
        return v;
    }
}
