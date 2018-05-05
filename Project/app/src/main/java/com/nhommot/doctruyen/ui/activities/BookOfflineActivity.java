package com.nhommot.doctruyen.ui.activities;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;

import com.nhommot.doctruyen.R;
import com.nhommot.doctruyen.database.BookOfflineSQLite;
import com.nhommot.doctruyen.models.BookOffline;
import com.nhommot.doctruyen.ui.adapters.listOfflineBookAdapter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.ArrayList;

public class BookOfflineActivity extends AppCompatActivity {
    ListView listViewOfflineBook;
    ArrayList<BookOffline> bookArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_offline);

        listViewOfflineBook=(ListView) findViewById(R.id.listBookOffline);

        //Database
        final BookOfflineSQLite dbOffline=new BookOfflineSQLite(getApplicationContext(),"OfflineBook.sqlite",null,1);
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
        listOfflineBookAdapter listAdapter=new listOfflineBookAdapter(getApplicationContext(),R.layout.activity_downloaded_book,bookArray);
        listViewOfflineBook.setAdapter(listAdapter);
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
