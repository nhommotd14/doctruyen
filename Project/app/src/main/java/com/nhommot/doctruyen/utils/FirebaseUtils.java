package com.nhommot.doctruyen.utils;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Huy on 4/22/2018.
 */

public class FirebaseUtils {
    public static final String AUTHOR_PATH = "authors";
    public static final String BOOK_PATH = "books";
    public static final String CHAPTER_PATH = "chapters";
    public static final String COMMENT_PATH = "comments";
    public static final String CONTENT_PATH = "contents";
    public static final String RATING_PATH = "ratings";
    public static final String TYPE_PATH = "types";
    public static final String FAVOURITE_PATH = "favourites";
    public static final String LATEST_PATH = "latest";
    public static final String USER_PATH = "User";

    public static DatabaseReference getBaseRef() {
        return FirebaseDatabase.getInstance().getReference();
    }
    public static DatabaseReference getAuthorRef(){
        return getBaseRef().child(AUTHOR_PATH);
    }
    public static DatabaseReference getBookRef(){
        return getBaseRef().child(BOOK_PATH);
    }
    public static DatabaseReference getChapterRef(){
        return getBaseRef().child(CHAPTER_PATH);
    }
    public static DatabaseReference getCommentRef(){
        return getBaseRef().child(COMMENT_PATH);
    }
    public static DatabaseReference getContentRef(){
        return getBaseRef().child(CONTENT_PATH);
    }
    public static DatabaseReference getRatingRef(){
        return getBaseRef().child(RATING_PATH);
    }
    public static DatabaseReference getTypeRef(){
        return getBaseRef().child(TYPE_PATH);
    }
    public static DatabaseReference getFavouriteRef(){
        return getBaseRef().child(FAVOURITE_PATH);
    }
    public static DatabaseReference getLatestRef(){
        return getBaseRef().child(LATEST_PATH);
    }
    public static DatabaseReference getUserRef(){
        return getBaseRef().child(USER_PATH);
    }

}
