package com.example.android.popularmovies;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TrailerAdapter extends BaseAdapter{

    private Context mContext;
    public ArrayList<Trailer> trailers = new ArrayList<Trailer>();

    public TrailerAdapter(Context mContext){
        this.mContext = mContext;
    }

    public void addTrailer(Trailer trailer){
        trailers.add(trailer);
    }
    @Override
    public int getCount() {
        return trailers.size();
    }

    @Override
    public Object getItem(int position) {
        return trailers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View trailerRow;
        if(convertView == null){
            trailerRow = View.inflate(mContext,R.layout.trailer_list_item,null);
        }
        else
            trailerRow = convertView;
        trailerRow.setId(1000 + position);
        ((TextView)trailerRow.findViewById(R.id.trailerLabel)).setText(trailers.get(position).label);
        Picasso.with(mContext).load("http://img.youtube.com/vi/" + trailers.get(position).url + "/hqdefault.jpg")
                .placeholder(R.drawable.images)
                .into((ImageView) trailerRow.findViewById(R.id.trailerImage));
        final String url = trailers.get(position).url;
        trailerRow.findViewById(R.id.trailerImage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetailActivityFragment.instance.watchYoutubeVideo(url);
            }
        });

        return trailerRow;
    }
}
