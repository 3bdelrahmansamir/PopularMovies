package com.example.abdelrahmansamir.popularmovie.landscape;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.abdelrahmansamir.popularmovie.R;
import com.example.abdelrahmansamir.popularmovie.SettingsActivity;
import com.example.abdelrahmansamir.popularmovie.SwitchActivity;
import com.example.abdelrahmansamir.popularmovie.movieslist.ShowMoviesListAdapter;

/**
 * Created by Abdelrahman Samir on 17/09/2016.
 */
public class LandscapeMoviesFragment extends Fragment {

    View rootView;
    Button button;
    TextView textView;
    LinearLayout linearLayout;
    GridView gridView;
    LayoutInflater inflater;
    SharedPreferences sharedPreferencesSort;
    SharedPreferences sharedPreferencesShow;
    String sort;
    String showAdult;
    String showFavorite;
    ImageView imageViewCover;
    ImageView imageViewBackground;
    TextView textViewName;
    TextView textViewVoteAverage;
    TextView textViewVoteCount;
    TextView textViewDate;
    TextView textViewLanguage;
    TextView textViewOverview;
    CheckBox checkBoxFavorite;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        this.rootView = inflater.inflate(R.layout.fragment_landscape_movies, container, false);
        this.inflater = inflater;

        WindowManager windowManager = getActivity().getWindowManager();
        Display display = windowManager.getDefaultDisplay();

        if (!(display.getHeight() > display.getWidth())) {

            this.button = (Button) rootView.findViewById(R.id.bt_internet);
            this.textView = (TextView) rootView.findViewById(R.id.tv_internet);
            this.gridView = (GridView) rootView.findViewById(R.id.lv_test);
            this.imageViewCover = (ImageView) rootView.findViewById(R.id.iv_cover);
            this.imageViewBackground = (ImageView) rootView.findViewById(R.id.iv_background);
            this.textViewName = (TextView) rootView.findViewById(R.id.tv_name);
            this.textViewVoteAverage = (TextView) rootView.findViewById(R.id.tv_vote_average);
            this.textViewVoteCount = (TextView) rootView.findViewById(R.id.tv_vote_count);
            this.textViewDate = (TextView) rootView.findViewById(R.id.tv_date);
            this.textViewLanguage = (TextView) rootView.findViewById(R.id.tv_language);
            this.textViewOverview = (TextView) rootView.findViewById(R.id.tv_overview);
            this.checkBoxFavorite = (CheckBox) rootView.findViewById(R.id.cb_favorite);


            this.linearLayout = (LinearLayout) rootView.findViewById(R.id.linear_all);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new LandscapeMoviesConnection(rootView.getContext(), LandscapeMoviesFragment.this.inflater, LandscapeMoviesFragment.this, button, textView, gridView, linearLayout, imageViewCover, imageViewBackground, textViewName, textViewVoteAverage, textViewVoteCount, textViewDate, textViewLanguage, textViewOverview, checkBoxFavorite).execute("");
                }
            });

            new LandscapeMoviesConnection(rootView.getContext(), inflater, LandscapeMoviesFragment.this, button, textView, gridView, linearLayout, imageViewCover, imageViewBackground, textViewName, textViewVoteAverage, textViewVoteCount, textViewDate, textViewLanguage, textViewOverview, checkBoxFavorite).execute("");
        }

        sharedPreferencesSort = rootView.getContext().getSharedPreferences("Sorting", Context.MODE_PRIVATE);
        sharedPreferencesShow = rootView.getContext().getSharedPreferences("Show", Context.MODE_PRIVATE);
        sort = sharedPreferencesSort.getString("Sort", "Name");
        showAdult = sharedPreferencesShow.getString("ShowAdult", "No");
        showFavorite = sharedPreferencesShow.getString("ShowFavorite", "No");

        return rootView;
    }


    @Override
    public void onStart() {

        if (!sharedPreferencesSort.getString("Sort", "Name").equals(sort) || !sharedPreferencesShow.getString("ShowAdult", "No").equals(showAdult) || !sharedPreferencesShow.getString("ShowFavorite", "No").equals(showFavorite)) {

            ((ShowMoviesListAdapter) (gridView.getAdapter())).getDataView = ((ShowMoviesListAdapter) (gridView.getAdapter())).fetch_moviesDatabase.getDataView();

            if (((ShowMoviesListAdapter) (gridView.getAdapter())).getDataView.get(0)[0].equals("No Movies Found")) {
                rootView.findViewById(R.id.tv_nomovie).setVisibility(View.VISIBLE);
                rootView.findViewById(R.id.tv_show).setVisibility(View.INVISIBLE);
                gridView.setVisibility(View.INVISIBLE);
            } else {
                rootView.findViewById(R.id.tv_nomovie).setVisibility(View.INVISIBLE);

                if (SwitchActivity.NAME.equals("None")) {
                    rootView.findViewById(R.id.tv_show).setVisibility(View.VISIBLE);
                }

                gridView.setVisibility(View.VISIBLE);
            }

            ((ShowMoviesListAdapter) (gridView.getAdapter())).notifyDataSetChanged();

            sort = sharedPreferencesSort.getString("Sort", "Name");
            showAdult = sharedPreferencesShow.getString("ShowAdult", "No");
            showFavorite = sharedPreferencesShow.getString("ShowFavorite", "Yes");

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
            new LandscapeMoviesConnection(rootView.getContext(), inflater, LandscapeMoviesFragment.this, button, textView, gridView, linearLayout, imageViewCover, imageViewBackground, textViewName, textViewVoteAverage, textViewVoteCount, textViewDate, textViewLanguage, textViewOverview, checkBoxFavorite).execute("");
        }
        return super.onOptionsItemSelected(item);
    }

}
