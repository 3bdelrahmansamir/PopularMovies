package com.example.abdelrahmansamir.popularmovie.movieslist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.abdelrahmansamir.popularmovie.R;
import com.example.abdelrahmansamir.popularmovie.database.Fetch_MoviesDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Abdelrahman Samir on 08/09/2016.
 */
public class ShowMoviesListAdapter extends BaseAdapter {
    public Fetch_MoviesDatabase fetch_moviesDatabase;
    private LayoutInflater inflater;
    int rowCount;


    public ShowMoviesListAdapter(LayoutInflater inflater, Context context) {
        this.inflater = inflater;
        this.fetch_moviesDatabase = new Fetch_MoviesDatabase(context);
        getDataView = fetch_moviesDatabase.getDataView();
    }


    @Override
    public int getCount() {
        return getDataView.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public ArrayList<String[]> getDataView;

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (view == null) {
            view = inflater.inflate(R.layout.adapter_movies, viewGroup, false);
        }

        TextView text = (TextView) view.findViewById(R.id.tv_show_adapter);
        CircleImageView circleImageView = (CircleImageView) view.findViewById(R.id.civ_show_adapter);
        if (!getDataView.get(0)[0].equals("No Movies Found")) {
            text.setText(getDataView.get(i)[0]);
            Picasso.with(view.getContext()).load("http://image.tmdb.org/t/p/w300/" + getDataView.get(i)[1]).into(circleImageView);
        }
        return view;
    }
}
