package com.nhommot.doctruyen.ui.adapters;

import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.nhommot.doctruyen.models.Author;
import com.nhommot.doctruyen.models.Book;
import com.nhommot.doctruyen.utils.JsonUtils;

/**
 * Created by Huy on 4/11/2018.
 */

public class BookAdapter {
    private final String TAG = "BookAdapter";
    private static final String BOOK_REF = "authors";
    private DatabaseReference bookRef;

    public BookAdapter(){
        bookRef = FirebaseDatabase.getInstance().getReference(BOOK_REF);
    }

//    public Book addBook(Book book){
//        DatabaseReference authorOfBookRef = bookRef.child(book.getAuthor().getAuthorId());
//        String bookID = authorOfBookRef.push().getKey();
//        book.setBookId(bookID);
//        authorOfBookRef.child(bookID).setValue(book);
//        return book;
//    }

    public void getBook(final String bookId) {
        Log.d(TAG, "getBook: bookId " + bookId);
        bookRef.orderByChild("name").equalTo("Steve 1").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onDataChange: KEY " + dataSnapshot.getKey());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
//        bookRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for (DataSnapshot data : dataSnapshot.getChildren()) {
//                    Log.d(TAG, "onDataChange: KEY" + bookRef.getKey());
//                    if (data.hasChild(bookId)) {
//                        Book book = data.child(bookId).getValue(Book.class);
//                        Log.d(TAG, "onDataChange: " + JsonUtils.encode(book));
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
    }



}
