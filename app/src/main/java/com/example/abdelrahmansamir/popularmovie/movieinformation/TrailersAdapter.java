package com.example.abdelrahmansamir.popularmovie.movieinformation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.abdelrahmansamir.popularmovie.R;

import java.util.ArrayList;

/**
 * Created by Abdelrahman Samir on 23/09/2016.
 */
public class TrailersAdapter extends BaseAdapter {

    ArrayList<String[]> objects;
    LayoutInflater inflater;

    public TrailersAdapter(ArrayList<String[]> objects, LayoutInflater inflater) {

        this.objects = objects;
        this.inflater = inflater;

    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = inflater.inflate(R.layout.adapter_trailers, viewGroup, false);
        }

        TextView textView = (TextView) view.findViewById(R.id.tv_trailer);
        textView.setText(objects.get(i)[1]);
        return view;
    }
}
