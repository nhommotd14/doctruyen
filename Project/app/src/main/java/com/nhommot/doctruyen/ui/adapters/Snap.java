package com.nhommot.doctruyen.ui.adapters;

import com.nhommot.doctruyen.models.Book;

import java.util.List;

/**
 * Created by delaroy on 2/10/17.
 */
public class Snap {

    private int mGravity;
    private String mText;
    private List<Book> mApps;

    public Snap(int gravity, String text, List<Book> apps){
        mGravity = gravity;
        mText = text;
        mApps = apps;
    }

    public String getText() { return mText; }
    public int getGravity() { return  mGravity; }
    public List<Book> getApps() { return mApps; }
}
