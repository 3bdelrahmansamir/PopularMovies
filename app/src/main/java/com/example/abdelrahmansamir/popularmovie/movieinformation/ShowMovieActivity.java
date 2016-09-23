package com.example.abdelrahmansamir.popularmovie.movieinformation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.WindowManager;

import com.example.abdelrahmansamir.popularmovie.R;
import com.example.abdelrahmansamir.popularmovie.SwitchActivity;
import com.example.abdelrahmansamir.popularmovie.movieslist.MoviesActivity;

public class ShowMovieActivity extends AppCompatActivity {

    ShowMovieFragment showMovieFragment = new ShowMovieFragment();
    public static final int LANDSCAPE = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_movie);
        getSupportActionBar().setElevation(1);

        setTitle(SwitchActivity.NAME);
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();

        if (display.getWidth() > display.getHeight()) {
            MoviesActivity.PORTRAIT = false;
            Intent intent = new Intent();
            setResult(LANDSCAPE, intent);
            finish();
        }

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction().add(R.id.ll_show_movie, showMovieFragment).commit();
        }
    }

    @Override
    public void onBackPressed() {
        MoviesActivity.PORTRAIT = true;
        SwitchActivity.NAME = "None";
        super.onBackPressed();
    }
}
