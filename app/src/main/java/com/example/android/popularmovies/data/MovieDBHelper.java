package com.example.android.popularmovies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.android.popularmovies.data.MovieContract.MovieEntry;

public class MovieDBHelper extends SQLiteOpenHelper {

    static final String DATABASE_NAME = "movies.db";
    static final int DATABASE_VERSION = 1;


    public MovieDBHelper(Context c){
        super(c, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CREATE_TABLE = "create table " + MovieEntry.TABLE_NAME + " (" +
                MovieEntry.COLUMN_ID + " INTEGER PRIMARY KEY, " +
                MovieEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                MovieEntry.COLUMN_OVERVIEW + " TEXT NOT NULL, " +
                MovieEntry.COLUMN_POSTER + " TEXT NOT NULL, " +
                MovieEntry.COLUMN_BACKDROP + " TEXT NOT NULL, " +
                MovieEntry.COLUMN_RATING + " TEXT NOT NULL, " +
                MovieEntry.COLUMN_RELEASE + " TEXT NOT NULL" +
                ")";
        Log.d("TABLE", "creating table " + CREATE_TABLE);
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MovieEntry.TABLE_NAME);
        onCreate(db);
    }
}
