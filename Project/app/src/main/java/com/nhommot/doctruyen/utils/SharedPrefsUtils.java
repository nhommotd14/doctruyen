package com.nhommot.doctruyen.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.nhommot.doctruyen.Constants;
import com.nhommot.doctruyen.models.Book;

/**
 * Created by Huy on 4/22/2018.
 */

public class SharedPrefsUtils {
    private static final String CURRENT_BOOK = "currentBook";
    private static final String CURRENT_BOOK_ID = "currentBookId";
    private static final String CURRENT_CONTENT_ID = "currentContentId";
    private static final String CURRENT_CHAPTER_ID = "currentChapterId";
    private static final String CURRENT_USER_ID = "currentUserId";
    private static final String OFFLINE_STATE = "offlineState";


    public static SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences(Constants.PACKAGE_NAME, Context.MODE_PRIVATE);
    }
    public static Book getCurrentBook(Context context) {
        String currentBookString = getPrefs(context).getString(CURRENT_BOOK, "");
        Book currentBook = JsonUtils.decode(currentBookString, Book.class);
        return currentBook;
    }

    public static void setCurrentBook(Context context, Book book) {
        getPrefs(context).edit().putString(CURRENT_BOOK, JsonUtils.encode(book)).commit();
    }

    public static void removeCurrentBook(Context context) {
        getPrefs(context).edit().remove(CURRENT_BOOK).commit();
    }
    public static String getCurrentBookId(Context context) {
        return getPrefs(context).getString(CURRENT_BOOK_ID, "");
    }

    public static void setCurrentBookId(Context context, String bookId) {
        getPrefs(context).edit().putString(CURRENT_BOOK_ID, bookId).commit();
    }

    public static void removeCurrentBookId(Context context) {
        getPrefs(context).edit().remove(CURRENT_BOOK_ID).commit();
    }
    public static String getCurrentContentId(Context context) {
        return getPrefs(context).getString(CURRENT_CONTENT_ID, "");
    }

    public static void setCurrentContentId(Context context, String contentId) {
        getPrefs(context).edit().putString(CURRENT_CONTENT_ID, contentId).commit();
    }

    public static void removeCurrentContentId(Context context) {
        getPrefs(context).edit().remove(CURRENT_CONTENT_ID).commit();
    }
    public static String getCurrentUserId(Context context) {
        return getPrefs(context).getString(CURRENT_USER_ID, "");
    }

    public static void setCurrentUserId(Context context, String contentId) {
        getPrefs(context).edit().putString(CURRENT_USER_ID, contentId).commit();
    }

    public static void removeCurrentUserId(Context context) {
        getPrefs(context).edit().remove(CURRENT_USER_ID).commit();
    }
    public static String getCurrentChapterId(Context context) {
        return getPrefs(context).getString(CURRENT_CHAPTER_ID, "");
    }

    public static void setCurrentChapterId(Context context, String contentId) {
        getPrefs(context).edit().putString(CURRENT_CHAPTER_ID, contentId).commit();
    }

    public static void removeCurrentChapterId(Context context) {
        getPrefs(context).edit().remove(CURRENT_CHAPTER_ID).commit();
    }
    public static boolean getOfflineState (Context context) {
        return getPrefs(context).getBoolean(OFFLINE_STATE, false);
    }

    public static void setOfflineState(Context context, boolean b) {
        getPrefs(context).edit().putBoolean(OFFLINE_STATE, b).commit();
    }

    public static void removeOfflineState(Context context) {
        getPrefs(context).edit().remove(OFFLINE_STATE).commit();
    }


}
