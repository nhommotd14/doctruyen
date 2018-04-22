package com.nhommot.doctruyen.database;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.nhommot.doctruyen.models.Chapter;
import com.nhommot.doctruyen.utils.JsonUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Huy on 4/9/2018.
 */

public class ChapterManager {
    private final String TAG = "ChapterManager";
    private static ChapterManager chapterManager;

    public ChapterManager() {
    }

    private SQLiteDatabase mDb;
    private DatabaseHelper mDbHelper;

    private Context mContext;

    public ChapterManager(Context context) {
        this.mContext = context;
        mDbHelper = new DatabaseHelper(mContext);
    }

    public ChapterManager open() throws SQLException {
        try {
            mDbHelper.openDatabase();
            mDbHelper.close();
            mDb = mDbHelper.getReadableDatabase();
        } catch (SQLException mSQLException) {
            Log.e(TAG, "open >>" + mSQLException.toString());
            throw mSQLException;
        }
        return this;
    }

    public void close() {
        mDbHelper.close();
    }

    public List<Chapter> getAll(int bookId) {
        String sql = "SELECT * FROM Chapter";

        Cursor mCur = mDb.rawQuery(sql, null);
        if (mCur != null) {
            mCur.moveToFirst();
        }
        List<Chapter> chapters = new ArrayList<>();
        if (mCur.getCount() > 0) {
            Log.d(TAG, "getAll: count ================== " + mCur.getCount());
            do {
                Chapter chapter = new Chapter();
                chapter.setChapterId(mCur.getInt(0));
                chapter.setBookId(mCur.getInt(1));
                chapter.setChapterName(mCur.getString(2));
                chapters.add(chapter);
                Log.d(TAG, "chapter: " + JsonUtils.encode(chapter));
            } while (mCur.moveToNext());

        }
        return chapters;
    }

}