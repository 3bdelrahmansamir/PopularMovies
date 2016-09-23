package com.example.abdelrahmansamir.popularmovie.database;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by Abdelrahman Samir on 03/09/2016.
 */
public class Fetch_MoviesDatabase {

    public Context context;
    public MoviesDatabase moviesDatabase;
    public SQLiteDatabase sqLiteDatabase;

    public Fetch_MoviesDatabase(Context context) {
        this.context = context;
        open();
    }

    public void close() {
        moviesDatabase.close();
    }

    public void open() {
        this.moviesDatabase = new MoviesDatabase(this.context);
        this.sqLiteDatabase = this.moviesDatabase.getWritableDatabase();
    }

    public void insert(int movieId, int day, int month, int year, int voteCount, double voteAverage, double popularity, String adult, String movieName, String movieLanguage, String overview, final String coverSrc, final String backgroundSrc) {
        final ContentValues contentValues = new ContentValues();
        contentValues.put(MoviesDatabase.MOVIE_ID, movieId);
        contentValues.put(MoviesDatabase.DAY, day);
        contentValues.put(MoviesDatabase.MONTH, month);
        contentValues.put(MoviesDatabase.YEAR, year);
        contentValues.put(MoviesDatabase.VOTE_COUNT, voteCount);
        contentValues.put(MoviesDatabase.VOTE_AVERAGE, voteAverage);
        contentValues.put(MoviesDatabase.POPULARITY, popularity);
        contentValues.put(MoviesDatabase.ADULT, adult);
        contentValues.put(MoviesDatabase.NAME, movieName);
        contentValues.put(MoviesDatabase.LANGUAGE, movieLanguage);
        contentValues.put(MoviesDatabase.OVERVIEW, overview);
        contentValues.put(MoviesDatabase.NEW_MOVIE, "NEW");
        contentValues.put(MoviesDatabase.COVER_SRC, coverSrc);
        contentValues.put(MoviesDatabase.BACKGROUND_SRC, backgroundSrc);
        contentValues.put(MoviesDatabase.FAVORITE, "No");

        sqLiteDatabase.insert(MoviesDatabase.TABLE_NAME, null, contentValues);
    }


    public boolean checkForInsert(int movieId) {

        Cursor cursor = sqLiteDatabase.query(MoviesDatabase.TABLE_NAME, new String[]{MoviesDatabase.MOVIE_ID}, MoviesDatabase.MOVIE_ID + '=' + movieId, null, null, null, null);
        if (cursor.moveToFirst()) {
            return true;
        }
        return false;
    }


    public void updateNew(int movieId, int voteCount, double voteAverage, double popularity) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MoviesDatabase.NEW_MOVIE, "NEW");
        contentValues.put(MoviesDatabase.VOTE_COUNT, voteCount);
        contentValues.put(MoviesDatabase.VOTE_AVERAGE, voteAverage);
        contentValues.put(MoviesDatabase.POPULARITY, popularity);
        sqLiteDatabase.update(MoviesDatabase.TABLE_NAME, contentValues, MoviesDatabase.MOVIE_ID + '=' + movieId, null);
    }

    public void updateOld() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MoviesDatabase.NEW_MOVIE, "OLD");
        sqLiteDatabase.update(MoviesDatabase.TABLE_NAME, contentValues, null, null);
    }

    public void delete() {
        sqLiteDatabase.delete(MoviesDatabase.TABLE_NAME, MoviesDatabase.NEW_MOVIE + " = 'OLD' AND " + MoviesDatabase.FAVORITE + " = 'No'", null);
    }

    public ArrayList<String[]> getDataView() {

        SharedPreferences sharedPreferences = context.getSharedPreferences("Show", Context.MODE_PRIVATE);

        String showAdult = sharedPreferences.getString("ShowAdult", "No");
        String showFavorite = sharedPreferences.getString("ShowFavorite", "No");

        String where = null;

        if (showFavorite.equals("Yes") && showAdult.equals("Yes")) {
            where = MoviesDatabase.FAVORITE + " = 'Yes'";
        } else if (showFavorite.equals("Yes")) {
            where = MoviesDatabase.FAVORITE + " = 'Yes' AND " + MoviesDatabase.ADULT + " = 'No'";
        } else if (showAdult.equals("Yes")) {
            where = null;
        } else {
            where = MoviesDatabase.ADULT + " = 'No'";
        }

        ArrayList<String[]> getDataView = new ArrayList();
        Cursor cursor = null;
        if (context.getSharedPreferences("Sorting", Context.MODE_PRIVATE).getString("Sort", "Name").equals("Name")) {
            cursor = sqLiteDatabase.query(MoviesDatabase.TABLE_NAME, new String[]{MoviesDatabase.NAME, MoviesDatabase.BACKGROUND_SRC}, where, null, null, null, MoviesDatabase.NAME + " ASC");
        } else if (context.getSharedPreferences("Sorting", Context.MODE_PRIVATE).getString("Sort", "Name").equals("Date")) {
            cursor = sqLiteDatabase.query(MoviesDatabase.TABLE_NAME, new String[]{MoviesDatabase.NAME, MoviesDatabase.BACKGROUND_SRC}, where, null, null, null, MoviesDatabase.YEAR + " DESC , " + MoviesDatabase.MONTH + " DESC , " + MoviesDatabase.DAY + " DESC");
        } else if (context.getSharedPreferences("Sorting", Context.MODE_PRIVATE).getString("Sort", "Name").equals("Vote")) {
            cursor = sqLiteDatabase.query(MoviesDatabase.TABLE_NAME, new String[]{MoviesDatabase.NAME, MoviesDatabase.BACKGROUND_SRC}, where, null, null, null, MoviesDatabase.VOTE_COUNT + " DESC");
        } else if (context.getSharedPreferences("Sorting", Context.MODE_PRIVATE).getString("Sort", "Name").equals("VoteAverage")) {
            cursor = sqLiteDatabase.query(MoviesDatabase.TABLE_NAME, new String[]{MoviesDatabase.NAME, MoviesDatabase.BACKGROUND_SRC}, where, null, null, null, MoviesDatabase.VOTE_AVERAGE + " DESC");
        } else if (context.getSharedPreferences("Sorting", Context.MODE_PRIVATE).getString("Sort", "Name").equals("Popularity")) {
            cursor = sqLiteDatabase.query(MoviesDatabase.TABLE_NAME, new String[]{MoviesDatabase.NAME, MoviesDatabase.BACKGROUND_SRC}, where, null, null, null, MoviesDatabase.POPULARITY + " DESC");
        }

        if (cursor.getCount() == 0) {
            getDataView.add(new String[]{"No Movies Found"});
            return getDataView;
        }

        if (cursor.moveToFirst()) {

            do {
                getDataView.add(new String[]{cursor.getString(cursor.getColumnIndex(MoviesDatabase.NAME)), cursor.getString(cursor.getColumnIndex(MoviesDatabase.BACKGROUND_SRC))});
            } while (cursor.moveToNext());

        }
        return getDataView;
    }

    public ArrayList<Object> getMovieInformation(String movieName) {
        ArrayList movieInformations = new ArrayList();
        Cursor cursor = sqLiteDatabase.query(MoviesDatabase.TABLE_NAME, new String[]{MoviesDatabase.NAME, MoviesDatabase.DAY, MoviesDatabase.MONTH, MoviesDatabase.YEAR, MoviesDatabase.VOTE_COUNT, MoviesDatabase.VOTE_AVERAGE, MoviesDatabase.LANGUAGE, MoviesDatabase.BACKGROUND_SRC, MoviesDatabase.COVER_SRC, MoviesDatabase.OVERVIEW, MoviesDatabase.FAVORITE, MoviesDatabase.MOVIE_ID}, MoviesDatabase.NAME + " = ?", new String[]{movieName}, null, null, null);
        if (cursor.moveToFirst()) {
            movieInformations.add(cursor.getString(cursor.getColumnIndex(MoviesDatabase.NAME)));
            movieInformations.add(cursor.getInt(cursor.getColumnIndex(MoviesDatabase.DAY)));
            movieInformations.add(cursor.getInt(cursor.getColumnIndex(MoviesDatabase.MONTH)));
            movieInformations.add(cursor.getInt(cursor.getColumnIndex(MoviesDatabase.YEAR)));
            movieInformations.add(cursor.getInt(cursor.getColumnIndex(MoviesDatabase.VOTE_COUNT)));
            movieInformations.add(cursor.getDouble(cursor.getColumnIndex(MoviesDatabase.VOTE_AVERAGE)));
            movieInformations.add(cursor.getString(cursor.getColumnIndex(MoviesDatabase.LANGUAGE)));
            movieInformations.add(cursor.getString(cursor.getColumnIndex(MoviesDatabase.BACKGROUND_SRC)));
            movieInformations.add(cursor.getString(cursor.getColumnIndex(MoviesDatabase.COVER_SRC)));
            movieInformations.add(cursor.getString(cursor.getColumnIndex(MoviesDatabase.OVERVIEW)));
            movieInformations.add(cursor.getString(cursor.getColumnIndex(MoviesDatabase.FAVORITE)));
            movieInformations.add(cursor.getString(cursor.getColumnIndex(MoviesDatabase.MOVIE_ID)));
        }

        return movieInformations;
    }

    public void addToFavorite(String movieName) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(MoviesDatabase.FAVORITE, "Yes");
        sqLiteDatabase.update(MoviesDatabase.TABLE_NAME, contentValues, MoviesDatabase.NAME + " = ?", new String[]{movieName});
    }

    public void removeFromFavorite(String movieName) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(MoviesDatabase.FAVORITE, "No");
        sqLiteDatabase.update(MoviesDatabase.TABLE_NAME, contentValues, MoviesDatabase.NAME + " = ?", new String[]{movieName});

    }

}