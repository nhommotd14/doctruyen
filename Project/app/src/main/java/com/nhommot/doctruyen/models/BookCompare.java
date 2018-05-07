package com.nhommot.doctruyen.models;

import java.util.Comparator;

public class BookCompare implements Comparator<Book> {
    @Override
    public int compare(Book o1, Book o2) {
        int value=0;
        int compare = o1.getViews()-o2.getViews();
        if(compare!=0)
           value=compare;
        return value;

    }
}
