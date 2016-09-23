package com.example.abdelrahmansamir.popularmovie.movieslist;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.WindowManager;

import com.example.abdelrahmansamir.popularmovie.R;
import com.example.abdelrahmansamir.popularmovie.SwitchActivity;
import com.example.abdelrahmansamir.popularmovie.movieinformation.ShowMovieActivity;

public class MoviesActivity extends AppCompatActivity {


    private static final int RESULT_CODE = 0;
    private static final int LANDSCAPE = 10;
    public static boolean PORTRAIT = true;

    MoviesFragment moviesFragment = new MoviesFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);
        getSupportActionBar().setElevation(1);

        if (PORTRAIT == true) {
            WindowManager windowManager = getWindowManager();
            Display display = windowManager.getDefaultDisplay();
            if (display.getWidth() > display.getHeight()) {
                Intent intent = new Intent();
                setResult(LANDSCAPE, intent);
                finish();
            }

            if (!SwitchActivity.NAME.equals("None")) {
                Intent movieInformation = new Intent(MoviesActivity.this, ShowMovieActivity.class);
                startActivityForResult(movieInformation, 100);
            }
        }

        SharedPreferences preferencesSort = getSharedPreferences("Sorting", MODE_PRIVATE);
        SharedPreferences preferencesShow = getSharedPreferences("Show", MODE_PRIVATE);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction().add(R.id.main_content, moviesFragment).commit();
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == LANDSCAPE) {
            PORTRAIT = true;
            Intent intent = new Intent();
            setResult(LANDSCAPE, intent);
            finish();
        }

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        setResult(RESULT_CODE, intent);
        super.onBackPressed();
    }
}
