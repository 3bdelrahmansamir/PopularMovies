package com.example.abdelrahmansamir.popularmovie;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;


public class SettingsActivity extends AppCompatActivity {

    CheckBox checkBoxAdult;
    RadioGroup radioGroup;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initCheckBox();
        initRadioGroup();
    }

    void initCheckBox() {
        CheckBox checkBoxAdult = (CheckBox) findViewById(R.id.cb_show_adult);
        CheckBox checkBoxFavorite = (CheckBox) findViewById(R.id.cb_show_favorite);
        SharedPreferences sharedPreferences = getSharedPreferences("Show", MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        if (sharedPreferences.getString("ShowAdult", "No").equals("Yes")) {
            checkBoxAdult.setChecked(true);
        }
        if (sharedPreferences.getString("ShowFavorite", "No").equals("Yes")) {
            checkBoxFavorite.setChecked(true);
        }

        checkBoxAdult.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    editor.putString("ShowAdult", "Yes");
                } else {
                    editor.putString("ShowAdult", "No");
                }
                editor.commit();
            }
        });

        checkBoxFavorite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    editor.putString("ShowFavorite", "Yes");
                } else {
                    editor.putString("ShowFavorite", "No");
                }
                editor.commit();
            }
        });
    }


    void initRadioGroup() {

        RadioGroup radioGroupSort = (RadioGroup) findViewById(R.id.sort_groub);
        final RadioButton radioButtonName = (RadioButton) findViewById(R.id.rb_name);
        final RadioButton radioButtonDate = (RadioButton) findViewById(R.id.rb_date);
        final RadioButton radioButtonVote = (RadioButton) findViewById(R.id.rb_vote);
        final RadioButton radioButtonVoteAverage = (RadioButton) findViewById(R.id.rb_vote_average);
        RadioButton radioButtonPopularity = (RadioButton) findViewById(R.id.rb_popularity);
        SharedPreferences sharedPreferences = getSharedPreferences("Sorting", MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        if (sharedPreferences.getString("Sort", "Name").equals("Name")) {
            radioButtonName.setChecked(true);
        } else if (sharedPreferences.getString("Sort", "Name").equals("Date")) {
            radioButtonDate.setChecked(true);
        } else if (sharedPreferences.getString("Sort", "Name").equals("Vote")) {
            radioButtonVote.setChecked(true);
        } else if (sharedPreferences.getString("Sort", "Name").equals("VoteAverage")) {
            radioButtonVoteAverage.setChecked(true);
        } else if (sharedPreferences.getString("Sort", "Name").equals("Popularity")) {
            radioButtonPopularity.setChecked(true);
        }

        radioGroupSort.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (radioButtonName.getId() == i) {
                    editor.putString("Sort", "Name");
                } else if (radioButtonDate.getId() == i) {
                    editor.putString("Sort", "Date");
                } else if (radioButtonVote.getId() == i) {
                    editor.putString("Sort", "Vote");
                } else if (radioButtonVoteAverage.getId() == i) {
                    editor.putString("Sort", "VoteAverage");
                } else {
                    editor.putString("Sort", "Popularity");
                }
                editor.commit();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            super.onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }
}