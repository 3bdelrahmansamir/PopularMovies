package com.example.abdelrahmansamir.popularmovie.movieslist;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.example.abdelrahmansamir.popularmovie.R;
import com.example.abdelrahmansamir.popularmovie.SwitchActivity;
import com.example.abdelrahmansamir.popularmovie.movieinformation.ShowMovieActivity;
import com.example.abdelrahmansamir.popularmovie.json.FetchDataJson;
import com.example.abdelrahmansamir.popularmovie.json.GetDataJsonFromUrl;

import org.json.JSONException;

import java.io.IOException;


/**
 * Created by Abdelrahman Samir on 07/09/2016.
 */
public class ShowMoviesListConnection extends AsyncTask<String, Void, String> {

    Context context;
    GridView gridView;
    TextView textView;
    Button button;
    Fragment fragment;
    LayoutInflater inflater;
    ShowMoviesListAdapter showMoviesListAdapter;

    public ShowMoviesListConnection(Context context, GridView gridView, TextView textView, Button button, Fragment fragment, LayoutInflater inflater) {
        this.context = context;
        this.gridView = gridView;
        this.textView = textView;
        this.button = button;
        this.fragment = fragment;
        this.inflater = inflater;
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
            return GetDataJsonFromUrl.getDataJsonFromUrl();
        } catch (IOException e) {
            return null;
        }

    }

    @Override
    protected void onPostExecute(String urlInformation) {
        if (urlInformation == null) {

            button.setVisibility(View.VISIBLE);
            textView.setVisibility(View.VISIBLE);
            gridView.setVisibility(View.INVISIBLE);
            textView.setText("Connection timed out");
            fragment.setHasOptionsMenu(false);

        } else if (urlInformation.equals("Check your connection and try again")) {
            button.setVisibility(View.VISIBLE);
            textView.setVisibility(View.VISIBLE);
            gridView.setVisibility(View.INVISIBLE);
            textView.setText(urlInformation);
            fragment.setHasOptionsMenu(false);
        } else
            try {
                this.button.setVisibility(View.INVISIBLE);
                this.textView.setVisibility(View.INVISIBLE);
                this.gridView.setVisibility(View.VISIBLE);

                FetchDataJson.fetchDataJson(urlInformation, this.context);
                this.showMoviesListAdapter = new ShowMoviesListAdapter(inflater, context);
                this.gridView.setAdapter(showMoviesListAdapter);
                if (((ShowMoviesListAdapter) (gridView.getAdapter())).getDataView.get(0)[0].equals("No Movies Found")) {
                    textView.setText("No movies in favorites");
                    textView.setVisibility(View.VISIBLE);
                    gridView.setVisibility(View.INVISIBLE);
                } else {
                    textView.setVisibility(View.INVISIBLE);
                    gridView.setVisibility(View.VISIBLE);
                }
                showMoviesListAdapter.notifyDataSetChanged();
                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent = new Intent(ShowMoviesListConnection.this.context, ShowMovieActivity.class);
                        SwitchActivity.NAME = ((TextView) view.findViewById(R.id.tv_show_adapter)).getText().toString();
                        ShowMoviesListConnection.this.fragment.getActivity().startActivityForResult(intent, 100);
                    }
                });
                fragment.setHasOptionsMenu(true);

            } catch (JSONException e) {

            } catch (IOException e) {

            }
    }
}
