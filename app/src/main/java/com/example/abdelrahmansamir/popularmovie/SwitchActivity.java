package com.example.abdelrahmansamir.popularmovie;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.WindowManager;

import com.example.abdelrahmansamir.popularmovie.landscape.LandscapeMoviesActivity;
import com.example.abdelrahmansamir.popularmovie.movieslist.MoviesActivity;

public class SwitchActivity extends AppCompatActivity {

    private static int CREATED = 0;
    public static String NAME = "None";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_switch);
        getSupportActionBar().setElevation(1);

        if (CREATED == 0) {
            WindowManager windowManager = getWindowManager();
            Display display = windowManager.getDefaultDisplay();
            if (display.getWidth() > display.getHeight()) {
                //switch landscape
                Intent intent = new Intent(SwitchActivity.this, LandscapeMoviesActivity.class);
                startActivityForResult(intent, 10);
            } else {
                //switch portrait
                Intent intent = new Intent(SwitchActivity.this, MoviesActivity.class);
                startActivityForResult(intent, 100);
            }
            CREATED = 1;
        }

        SharedPreferences preferencesSort = getSharedPreferences("Sorting", MODE_PRIVATE);
        SharedPreferences preferencesShow = getSharedPreferences("Show", MODE_PRIVATE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 0) {
            CREATED = 0;
            NAME = "None";
            MoviesActivity.PORTRAIT = true;
            Intent intent = new Intent();
            setResult(resultCode, intent);
            finish();
        } else if (resultCode == 10) {
            // switch Landscape
            Intent intent = new Intent(SwitchActivity.this, LandscapeMoviesActivity.class);
            startActivityForResult(intent, 10);
        } else if (resultCode == 100) {
            // switch portrait
            Intent intent = new Intent(SwitchActivity.this, MoviesActivity.class);
            startActivityForResult(intent, 100);
        }
    }

}
