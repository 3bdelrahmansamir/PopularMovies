package com.example.abdelrahmansamir.popularmovie;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends Activity {

    private static final int REQUEST_CODE = 0;
    private static int CREATED = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (CREATED == 0) {
            Thread newActivity = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1350);
                    } catch (InterruptedException e) {

                    }
                    startActivityForResult(new Intent(MainActivity.this, SwitchActivity.class), REQUEST_CODE);
                }
            });
            newActivity.start();
            CREATED = 1;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == REQUEST_CODE) {
            CREATED = 0;
            finish();
        }
    }
}
