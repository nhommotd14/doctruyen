package com.nhommot.doctruyen.ui.fragments;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nhommot.doctruyen.R;
import com.nhommot.doctruyen.database.BookOfflineSQLite;
import com.nhommot.doctruyen.models.BookOffline;
import com.nhommot.doctruyen.ui.adapters.AListOfflineBookAdapter;
import com.nhommot.doctruyen.ui.adapters.SimpleDividerItemDecoration;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.ArrayList;

public class BookOfflineFragment extends Fragment {
    ArrayList<BookOffline> bookArray;
    RecyclerView recyclerView;

    private AListOfflineBookAdapter mAdapter;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                Bundle savedInstanceState)  {
        View rootView = inflater.inflate(R.layout.fragment_book_offline, container, false);
        //Database
        final BookOfflineSQLite dbOffline=new BookOfflineSQLite(this.getContext(),"OfflineBook.sqlite",null,1);
        String tableBook="create table if not exists Bookoffline(id integer primary key,name nvarchar,author nvarchar,description nvarchar,img Blob)";
        dbOffline.QueryData(tableBook);
        String tableBookChap="create table if not exists BookChap(idtruyen integer,idchap integer,chapnum integer,hinh Blob)";
        dbOffline.QueryData(tableBookChap);
//        String tableChap="create table if not exists chap(id integer primary key,hinh Blob)";

        bookArray=new ArrayList<BookOffline>();

        //Ket noi database
        Cursor cursor=dbOffline.Getdata("select * from Bookoffline");
        while (cursor.moveToNext()){
            bookArray.add(new BookOffline(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getBlob(4)
            ));
        }
        recyclerView = rootView.findViewById(R.id.bookoffline_recycle_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this.getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(this.getContext()));
        mAdapter = new AListOfflineBookAdapter(this.getContext(), bookArray);
        recyclerView.setAdapter(mAdapter);



        mAdapter.notifyDataSetChanged();
        return rootView;
    }

    public byte[] ImageViewToByte(ImageView imageView){
        BitmapDrawable drawable=(BitmapDrawable) imageView.getDrawable();
        Bitmap bitmap=drawable.getBitmap();

        ByteArrayOutputStream stream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
        byte[] bytes=stream.toByteArray();
        return bytes;
    }

    private class LoadImageInternet extends AsyncTask<String,Void,Bitmap> {
        Bitmap bitmapHinh;

        @Override
        protected Bitmap doInBackground(String... strings) {
            try {
                URL url=new URL(strings[0]);
                InputStream inputStream=url.openConnection().getInputStream();
                bitmapHinh= BitmapFactory.decodeStream(inputStream);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmapHinh;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
//            imageViewLogo.setImageBitmap(bitmap);
        }
    }

    public byte[] BitmapToByte(Bitmap bitmap){
        int size = bitmap.getRowBytes() * bitmap.getHeight();
        ByteBuffer byteBuffer = ByteBuffer.allocate(size);
        bitmap.copyPixelsToBuffer(byteBuffer);
        byte[] byteArray = byteBuffer.array();
        return byteArray;
    }
}
