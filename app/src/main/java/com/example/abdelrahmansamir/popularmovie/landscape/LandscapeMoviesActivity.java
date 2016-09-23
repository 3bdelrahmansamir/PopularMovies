package com.example.abdelrahmansamir.popularmovie.landscape;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.WindowManager;

import com.example.abdelrahmansamir.popularmovie.R;

public class LandscapeMoviesActivity extends AppCompatActivity {

    private static final int RESULT_CODE = 0;
    private static final int PORTRAIT = 100;
    LandscapeMoviesFragment landscapeMoviesFragment = new LandscapeMoviesFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        savedInstanceState = null;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landscape_movies);
        getSupportActionBar().setElevation(1);

        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();

        if (display.getHeight() > display.getWidth()) {
            Intent intent = new Intent();
            setResult(PORTRAIT, intent);
            finish();
        }

        SharedPreferences preferencesSort = getSharedPreferences("Sorting", MODE_PRIVATE);
        SharedPreferences preferencesShow = getSharedPreferences("Show", MODE_PRIVATE);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction().add(R.id.main_content, landscapeMoviesFragment).commit();
        }

    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent();
        setResult(RESULT_CODE, intent);
        super.onBackPressed();
    }
}