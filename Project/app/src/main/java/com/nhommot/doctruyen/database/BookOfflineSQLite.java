package com.nhommot.doctruyen.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

public class BookOfflineSQLite extends SQLiteOpenHelper {
    public BookOfflineSQLite(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    //insert, update, delete, create
    public void QueryData(String sql){
        SQLiteDatabase db=getWritableDatabase();
        db.execSQL(sql);
    }

    //select
    public Cursor Getdata(String sql){
        SQLiteDatabase db=getWritableDatabase();
        return db.rawQuery(sql,null);
    }

    public void InsertBook(Double id,String name,String author,String description, byte[] img){
        SQLiteDatabase db=getWritableDatabase();
        String insert="insert into Bookoffline values(?,?,?,?,?)";
        SQLiteStatement statement=db.compileStatement(insert);
        statement.clearBindings();
        statement.bindDouble(1,id);
        statement.bindString(2,name);
        statement.bindString(3,author);
        statement.bindString(4,description);
        statement.bindBlob(5,img);
        statement.execute();
    }

//    public boolean DeleteRow(String name){
//        SQLiteDatabase db=getWritableDatabase();
//        return db.delete("truyentranh","tentruyen='"+name+"'",null)>0;
//    }

    public void InsertChap(Double idBook,Double idChap,Double chapNum, byte[] img){
        SQLiteDatabase db=getWritableDatabase();
        String insert="insert into BookChap values(?,?,?,?)";
        SQLiteStatement statement=db.compileStatement(insert);
        statement.clearBindings();
        statement.bindDouble(1,idBook);
        statement.bindDouble(2,idChap);
        statement.bindDouble(3,chapNum);
        statement.bindBlob(4,img);
        statement.execute();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
