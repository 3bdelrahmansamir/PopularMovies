package com.example.abdelrahmansamir.popularmovie.json;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Abdelrahman Samir on 06/09/2016.
 */
public class GetDataJsonFromUrl {

    public static String getDataJsonFromUrl() throws IOException {

        String result = null;
        URL urlconnection = new URL("http://api.themoviedb.org/3/discover/movie?api_key=803a540a06baf28b275600b33ceadde3");
        HttpURLConnection httpURLConnection = (HttpURLConnection) urlconnection.openConnection();
        httpURLConnection.setConnectTimeout(5000);
        httpURLConnection.setReadTimeout(5000);
        httpURLConnection.setRequestMethod("GET");
        InputStreamReader inputStreamReader = new InputStreamReader(httpURLConnection.getInputStream());
        BufferedReader resultReader = new BufferedReader(inputStreamReader);

        final StringBuilder txtBuilder = new StringBuilder();
        String line;

        while ((line = resultReader.readLine()) != null) {
            txtBuilder.append(line);
        }

        result = txtBuilder.toString();


        return result;
    }
}
