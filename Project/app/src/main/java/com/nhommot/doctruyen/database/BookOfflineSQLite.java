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

    public void InsertBook(String id,String name,String author,String description, byte[] img){
        SQLiteDatabase db=getWritableDatabase();
        String insert="insert into bookoff values(?,?,?,?,?)";
        SQLiteStatement statement=db.compileStatement(insert);
        statement.clearBindings();
        statement.bindString(1,id);
        statement.bindString(2,name);
        statement.bindString(3,author);
        statement.bindString(4,description);
        statement.bindBlob(5,img);
        statement.execute();
    }

    public boolean DeleteBook(String name){
        SQLiteDatabase db=getWritableDatabase();
        return db.delete("bookoff","name='"+name+"'",null)>0;
    }

    public void InsertChap(String idBook,String idChap,String chapName){
        SQLiteDatabase db=getWritableDatabase();
        String insert="insert into BookChapoff values(?,?,?)";
        SQLiteStatement statement=db.compileStatement(insert);
        statement.clearBindings();
        statement.bindString(1,idBook);
        statement.bindString(2,idChap);
        statement.bindString(3,chapName);
        statement.execute();
    }

    public void InsertContent(String idChap,String chapNum,byte[] img){
        SQLiteDatabase db=getWritableDatabase();
        String insert="insert into Chap values(?,?,?)";
        SQLiteStatement statement=db.compileStatement(insert);
        statement.clearBindings();
        statement.bindString(1,idChap);
        statement.bindString(2,chapNum);
        statement.bindBlob(3,img);
        statement.execute();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}