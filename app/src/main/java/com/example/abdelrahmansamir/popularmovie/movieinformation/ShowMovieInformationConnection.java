package com.example.abdelrahmansamir.popularmovie.movieinformation;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.abdelrahmansamir.popularmovie.R;
import com.example.abdelrahmansamir.popularmovie.SwitchActivity;
import com.example.abdelrahmansamir.popularmovie.database.Fetch_MoviesDatabase;
import com.example.abdelrahmansamir.popularmovie.json.GetAndFetchDataJsonVideos;
import com.example.abdelrahmansamir.popularmovie.movieslist.MoviesFragment;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Abdelrahman Samir on 12/09/2016.
 */
public class ShowMovieInformationConnection extends AsyncTask<String, Void, String> {

    Context context;
    Fragment fragment;
    Button button;
    TextView textView;
    LinearLayout linearLayout;
    Fetch_MoviesDatabase fetch_moviesDatabase;
    ImageView imageViewCover;
    ImageView imageViewBackground;
    TextView textViewName;
    TextView textViewVoteAverage;
    TextView textViewVoteCount;
    TextView textViewDate;
    TextView textViewLanguage;
    TextView textViewOverview;
    CheckBox checkBoxFavorite;
    ArrayList<String[]> objects = null;

    public ShowMovieInformationConnection(Context context, Fragment fragment, Button button, TextView textView, LinearLayout linearLayout, ImageView imageViewCover, ImageView imageViewBackground, TextView textViewName, TextView textViewVoteAverage, TextView textViewVoteCount, TextView textViewDate, TextView textViewLanguage, TextView textViewOverview, CheckBox checkBoxFavorite) {
        this.context = context;
        this.fragment = fragment;
        this.button = button;
        this.textView = textView;
        this.linearLayout = linearLayout;
        fetch_moviesDatabase = new Fetch_MoviesDatabase(this.context);
        this.imageViewCover = imageViewCover;
        this.imageViewBackground = imageViewBackground;
        this.textViewName = textViewName;
        this.textViewVoteAverage = textViewVoteAverage;
        this.textViewVoteCount = textViewVoteCount;
        this.textViewDate = textViewDate;
        this.textViewLanguage = textViewLanguage;
        this.textViewOverview = textViewOverview;
        this.checkBoxFavorite = checkBoxFavorite;
    }

    @Override
    protected String doInBackground(String... strings) {
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobile = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if (!(wifi.isConnected() || mobile.isConnected())) {
            return "Check your connection and try again";
        }

        try {
            URL urlconnection = new URL("http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=803a540a06baf28b275600b33ceadde3");
            HttpURLConnection httpURLConnection = (HttpURLConnection) urlconnection.openConnection();
            return "Done";
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    protected void onPostExecute(String testConnection) {

        if (testConnection == null) {

            button.setVisibility(View.VISIBLE);
            textView.setVisibility(View.VISIBLE);
            linearLayout.setVisibility(View.INVISIBLE);
            textView.setText("Connection timed out");

        } else if (testConnection.equals("Check your connection and try again")) {

            button.setVisibility(View.VISIBLE);
            textView.setVisibility(View.VISIBLE);
            linearLayout.setVisibility(View.INVISIBLE);
            textView.setText(testConnection);

        } else {
            linearLayout.setVisibility(View.VISIBLE);
            button.setVisibility(View.INVISIBLE);
            textView.setVisibility(View.INVISIBLE);
            final ArrayList objectsInfromation = fetch_moviesDatabase.getMovieInformation(SwitchActivity.NAME);
            textViewName.setText(objectsInfromation.get(0).toString());
            textViewDate.setText("Date: " + objectsInfromation.get(1) + '/' + objectsInfromation.get(2) + '/' + objectsInfromation.get(3));
            textViewVoteCount.setText("Vote Count: " + objectsInfromation.get(4));
            textViewVoteAverage.setText("Vote Average: " + objectsInfromation.get(5) + "/10");
            textViewLanguage.setText("Language: " + objectsInfromation.get(6).toString());
            Picasso.with(context).load("http://image.tmdb.org/t/p/w300/" + objectsInfromation.get(7)).into(imageViewBackground);
            Picasso.with(context).load("http://image.tmdb.org/t/p/w650/" + objectsInfromation.get(8)).into(imageViewCover);
            textViewOverview.setText(objectsInfromation.get(9).toString());
            if (objectsInfromation.get(10).equals("No")) {
                checkBoxFavorite.setChecked(false);
            } else {
                checkBoxFavorite.setChecked(true);
            }
            checkBoxFavorite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b) {
                        fetch_moviesDatabase.addToFavorite(textViewName.getText().toString());
                    } else {
                        fetch_moviesDatabase.removeFromFavorite(textViewName.getText().toString());
                        MoviesFragment.movieFavoriteChange = true;
                    }
                }
            });


            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {

                        ArrayList<String[]> arrayList = GetAndFetchDataJsonVideos.fetchDataJsonVideo(Integer.parseInt(objectsInfromation.get(11).toString()));
                        objects = arrayList;

                    } catch (IOException e) {

                    } catch (JSONException e) {

                    }
                }
            });

            t.start();

            while (t.isAlive()) {
            }

            ListView listView = (ListView) fragment.getView().findViewById(R.id.lv_trailers);
            listView.setVisibility(View.INVISIBLE);
            TrailersAdapter trailersAdapter = new TrailersAdapter(objects, fragment.getActivity().getLayoutInflater());
            listView.setAdapter(trailersAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    fragment.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + objects.get(i)[0])));
                }
            });
            listView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 102 * objects.size()));
            listView.setVisibility(View.VISIBLE);
            trailersAdapter.notifyDataSetChanged();

            Thread scroll = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(60);
                    } catch (InterruptedException e) {

                    }
                    fragment.getView().findViewById(R.id.sv_information).scrollTo(0, 0);
                }
            });
            scroll.start();
        }
    }
}
