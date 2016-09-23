package com.example.abdelrahmansamir.popularmovie.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Abdelrahman Samir on 03/09/2016.
 */
public class MoviesDatabase extends SQLiteOpenHelper {


    public static final int SQL_VERSION = 4;
    public static final String DATABASE_NAME = "movies_database";
    public static final String TABLE_NAME = "movies_table";
    public static final String ID = "id";
    public static final String MOVIE_ID = "movie_id";
    public static final String DAY = "day";
    public static final String MONTH = "month";
    public static final String YEAR = "year";
    public static final String VOTE_COUNT = "vote_count";
    public static final String POPULARITY = "popularity";
    public static final String VOTE_AVERAGE = "vote_average";
    public static final String ADULT = "adult";
    public static final String NAME = "name";
    public static final String LANGUAGE = "language";
    public static final String OVERVIEW = "overview";
    public static final String NEW_MOVIE = "new_movie";
    public static final String COVER_SRC = "cover_src";
    public static final String BACKGROUND_SRC = "background_src";
    public static final String FAVORITE = "favorite";


    public MoviesDatabase(Context context) {
        super(context, DATABASE_NAME, null, SQL_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(
                "CREATE TABLE " + TABLE_NAME
                        + " (" + ID + " INTEGER PRIMARY KEY , "
                        + MOVIE_ID + " INTEGER UNIQUE NOT NULL , "
                        + DAY + " INTEGER NOT NULL , "
                        + MONTH + " INTEGER NOT NULL , "
                        + YEAR + " INTEGER NOT NULL , "
                        + VOTE_COUNT + " INTEGER NOT NULL , "
                        + VOTE_AVERAGE + " REAL NOT NULL , "
                        + POPULARITY + " REAL NOT NULL , "
                        + ADULT + " TEXT NOT NULL , "
                        + NAME + " TEXT UNIQUE NOT NULL , "
                        + LANGUAGE + " TEXT NOT NULL , "
                        + OVERVIEW + " TEXT UNIQUE NOT NULL , "
                        + NEW_MOVIE + " TEXT NOT NULL , "
                        + COVER_SRC + " TEXT NOT NULL , "
                        + BACKGROUND_SRC + " TEXT NOT NULL , "
                        + FAVORITE + " TEXT NOT NULL ) "
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXIST" + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
