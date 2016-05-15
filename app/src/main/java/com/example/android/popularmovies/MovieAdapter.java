package com.example.android.popularmovies;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class MovieAdapter extends BaseAdapter{

    private Context mContext;
    public ArrayList<String> images = new ArrayList<String>();

    public MovieAdapter(Context mContext){
        this.mContext = mContext;
    }

    public void addMovie(String url) {images.add(url);}

    public void clearMovies(){images.clear();}

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Object getItem(int position) {
        return images.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if(convertView == null){
            imageView = new ImageView(mContext);
            imageView.setPadding(0,0,0,0);
            imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            imageView.setAdjustViewBounds(true);
        }else {
            imageView = (ImageView) convertView;
        }

        Picasso.with(mContext).load(images.get(position)).placeholder(R.drawable.nopicture).into(imageView);
        return imageView;
    }

}
