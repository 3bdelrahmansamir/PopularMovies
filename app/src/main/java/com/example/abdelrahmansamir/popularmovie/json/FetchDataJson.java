package com.example.abdelrahmansamir.popularmovie.json;

import android.content.Context;

import com.example.abdelrahmansamir.popularmovie.database.Fetch_MoviesDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by Abdelrahman Samir on 04/09/2016.
 */

public class FetchDataJson {
    static Fetch_MoviesDatabase fetch_moviesDatabase;
    static JSONObject jsonObject;
    static JSONArray jsonArray;
    static JSONObject getDataJson;
    static int fetchJsonArray;
    static int id;
    static int day;
    static int month;
    static int year;
    static int voteCount;
    static double voteAverage;
    static double popularity;
    static String adult;
    static String name;
    static String language;
    static String overview;
    static String cover_scr;
    static String background_src;

    public static void fetchDataJson(String jsonInformation, final Context context) throws JSONException, IOException {

        fetch_moviesDatabase = new Fetch_MoviesDatabase(context);
        jsonObject = new JSONObject(jsonInformation);
        jsonArray = jsonObject.getJSONArray("results");

        for (int i = 0; i < jsonArray.length(); i++) {

            getDataJson = jsonArray.getJSONObject(i);
            fetchJsonArray = getDataJson.getInt("id");

            if (fetch_moviesDatabase.checkForInsert(fetchJsonArray)) {
                voteCount = getDataJson.getInt("vote_count");
                voteAverage = getDataJson.getDouble("vote_average");
                popularity = getDataJson.getDouble("popularity");
                fetch_moviesDatabase.updateNew(fetchJsonArray, voteCount, voteAverage, popularity);

            } else {

                id = getDataJson.getInt("id");
                year = Integer.parseInt(getDataJson.getString("release_date").substring(0, 4));
                month = Integer.parseInt(getDataJson.getString("release_date").substring(5, 7));
                day = Integer.parseInt(getDataJson.getString("release_date").substring(8, 10));
                voteCount = getDataJson.getInt("vote_count");
                voteAverage = getDataJson.getDouble("vote_average");
                popularity = getDataJson.getDouble("popularity");
                adult = getDataJson.getBoolean("adult") ? "Yes" : "No";
                name = getDataJson.getString("title");
                language = getDataJson.getString("original_language");
                overview = getDataJson.getString("overview");
                cover_scr = getDataJson.getString("backdrop_path");
                background_src = getDataJson.getString("poster_path");

                fetch_moviesDatabase.insert(id, day, month, year, voteCount, voteAverage, popularity, adult, name, language, overview, cover_scr, background_src);
            }
        }

        fetch_moviesDatabase.delete();
        fetch_moviesDatabase.updateOld();
    }
}