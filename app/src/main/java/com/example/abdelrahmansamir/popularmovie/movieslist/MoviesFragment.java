package com.example.abdelrahmansamir.popularmovie.movieslist;

import android.app.Fragment;

import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.example.abdelrahmansamir.popularmovie.R;
import com.example.abdelrahmansamir.popularmovie.SettingsActivity;
import com.example.abdelrahmansamir.popularmovie.SwitchActivity;

/**
 * Created by Abdelrahman Samir on 07/09/2016.
 */
public class MoviesFragment extends Fragment {


    GridView gridView;
    TextView textView;
    Button button;
    View rootView = null;
    LayoutInflater inflater;
    SharedPreferences sharedPreferencesSort;
    SharedPreferences sharedPreferencesShow;
    String sort;
    String showAdult;
    String showFavorite;
    public static boolean movieFavoriteChange = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_movies, container, false);
        this.rootView = rootView;
        this.inflater = inflater;
        this.textView = (TextView) rootView.findViewById(R.id.tv_internet);
        this.gridView = (GridView) rootView.findViewById(R.id.lv_test);
        this.button = (Button) rootView.findViewById(R.id.bt_internet);
        this.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ShowMoviesListConnection(MoviesFragment.this.rootView.getContext(), MoviesFragment.this.gridView, MoviesFragment.this.textView,
                        MoviesFragment.this.button, MoviesFragment.this, MoviesFragment.this.inflater).execute("");
            }
        });

        new ShowMoviesListConnection(rootView.getContext(), gridView, textView, button, this, inflater).execute("");

        sharedPreferencesSort = rootView.getContext().getSharedPreferences("Sorting", Context.MODE_PRIVATE);
        sharedPreferencesShow = rootView.getContext().getSharedPreferences("Show", Context.MODE_PRIVATE);
        sort = sharedPreferencesSort.getString("Sort", "Name");
        showAdult = sharedPreferencesShow.getString("ShowAdult", "No");
        showFavorite = sharedPreferencesShow.getString("ShowFavorite", "No");
        return rootView;
    }

    @Override
    public void onStart() {

        if (textView.getText().toString().equals("Check your connection and try again") || textView.getText().toString().equals("Connection timed out")) {
            new ShowMoviesListConnection(MoviesFragment.this.rootView.getContext(), MoviesFragment.this.gridView, MoviesFragment.this.textView,
                    MoviesFragment.this.button, MoviesFragment.this, MoviesFragment.this.inflater).execute("");
            textView.setVisibility(View.INVISIBLE);
            button.setVisibility(View.INVISIBLE);
        }

        if (!sharedPreferencesSort.getString("Sort", "Name").equals(sort) || !sharedPreferencesShow.getString("ShowAdult", "No").equals(showAdult) || !sharedPreferencesShow.getString("ShowFavorite", "No").equals(showFavorite) || movieFavoriteChange == true) {

            ((ShowMoviesListAdapter) (gridView.getAdapter())).getDataView = ((ShowMoviesListAdapter) (gridView.getAdapter())).fetch_moviesDatabase.getDataView();

            if (((ShowMoviesListAdapter) (gridView.getAdapter())).getDataView.get(0)[0].equals("No Movies Found")) {
                textView.setText("No movies in favorites");
                textView.setVisibility(View.VISIBLE);
                gridView.setVisibility(View.INVISIBLE);
            } else {
                textView.setVisibility(View.INVISIBLE);
                gridView.setVisibility(View.VISIBLE);
            }

            ((ShowMoviesListAdapter) (gridView.getAdapter())).notifyDataSetChanged();

            sort = sharedPreferencesSort.getString("Sort", "Name");
            showAdult = sharedPreferencesShow.getString("ShowAdult", "No");
            showFavorite = sharedPreferencesShow.getString("ShowFavorite", "Yes");
            movieFavoriteChange = false;
        }
        super.onStart();
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.activity_movie_options, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.settings_movies) {
            startActivity(new Intent(rootView.getContext(), SettingsActivity.class));
        } else if (item.getItemId() == R.id.refresh_movies) {
            new ShowMoviesListConnection(rootView.getContext(), gridView, textView, button, this, inflater).execute("");
        }
        return super.onOptionsItemSelected(item);
    }

}