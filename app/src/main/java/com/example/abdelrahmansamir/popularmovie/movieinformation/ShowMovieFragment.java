package com.example.abdelrahmansamir.popularmovie.movieinformation;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.abdelrahmansamir.popularmovie.R;

/**
 * Created by Abdelrahman Samir on 10/09/2016.
 */
public class ShowMovieFragment extends Fragment {

    View rootview;
    TextView textView;
    Button button;
    LinearLayout linearLayout;
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

        this.rootview = inflater.inflate(R.layout.fragment_show_movie, container, false);
        this.textView = (TextView) rootview.findViewById(R.id.tv_internet);
        this.button = (Button) rootview.findViewById(R.id.bt_internet);
        this.imageViewCover = (ImageView) rootview.findViewById(R.id.iv_cover);
        this.imageViewBackground = (ImageView) rootview.findViewById(R.id.iv_background);
        this.linearLayout = (LinearLayout) rootview.findViewById(R.id.linear_information);
        this.textViewName = (TextView) rootview.findViewById(R.id.tv_name);
        this.textViewVoteAverage = (TextView) rootview.findViewById(R.id.tv_vote_average);
        this.textViewVoteCount = (TextView) rootview.findViewById(R.id.tv_vote_count);
        this.textViewDate = (TextView) rootview.findViewById(R.id.tv_date);
        this.textViewLanguage = (TextView) rootview.findViewById(R.id.tv_language);
        this.textViewOverview = (TextView) rootview.findViewById(R.id.tv_overview);
        this.checkBoxFavorite = (CheckBox) rootview.findViewById(R.id.cb_favorite);

        new ShowMovieInformationConnection(rootview.getContext(), ShowMovieFragment.this, button, textView, linearLayout, imageViewCover, imageViewBackground, textViewName, textViewVoteAverage, textViewVoteCount, textViewDate, textViewLanguage, textViewOverview, checkBoxFavorite).execute("");
        this.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ShowMovieInformationConnection(rootview.getContext(), ShowMovieFragment.this, button, textView, linearLayout, imageViewCover, imageViewBackground, textViewName, textViewVoteAverage, textViewVoteCount, textViewDate, textViewLanguage, textViewOverview, checkBoxFavorite).execute("");
            }
        });

        return rootview;
    }
}