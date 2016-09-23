package com.example.abdelrahmansamir.popularmovie.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Abdelrahman Samir on 22/09/2016.
 */
public class GetAndFetchDataJsonVideos {

    public static ArrayList<String[]> fetchDataJsonVideo(int id) throws IOException, JSONException {

        URL urlconnection = new URL("http://api.themoviedb.org/3/movie/" + id + "/videos?api_key=803a540a06baf28b275600b33ceadde3");
        HttpURLConnection httpURLConnection = (HttpURLConnection) urlconnection.openConnection();
        httpURLConnection.setConnectTimeout(5000);
        httpURLConnection.setReadTimeout(5000);
        httpURLConnection.setRequestMethod("GET");
        InputStreamReader inputStreamReader = new InputStreamReader(httpURLConnection.getInputStream());
        BufferedReader resultReader = new BufferedReader(inputStreamReader);

        StringBuilder txtBuilder = new StringBuilder();
        String line;

        while ((line = resultReader.readLine()) != null) {
            txtBuilder.append(line);
        }

        String result = txtBuilder.toString();

        JSONObject jsonObject = new JSONObject(result);
        JSONArray jsonArray = jsonObject.getJSONArray("results");

        String jsonVideoKey = null;
        String jsonTrailerName = null;
        ArrayList<String[]> objects = new ArrayList();

        for (int i = 0; i < jsonArray.length(); i++) {

            JSONObject getDataJson = jsonArray.getJSONObject(i);

            jsonVideoKey = getDataJson.getString("key");
            jsonTrailerName = getDataJson.getString("name");

            objects.add(new String[]{jsonVideoKey, jsonTrailerName});

        }

        return objects;

    }
}
