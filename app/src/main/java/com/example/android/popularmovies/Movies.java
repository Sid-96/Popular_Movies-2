package com.example.android.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;


public class Movies implements Parcelable {
    public String movieName;
    public String image;
    public String overview;
    public String releaseDate;
    public Float rating;
    public Double popularity;
    public int id;
    public String backdropUrl;

    public Movies(){

    }

    protected Movies(Parcel in){
        movieName = in.readString();
        image = in.readString();
        overview = in.readString();
        releaseDate = in.readString();
        rating = in.readFloat();
        backdropUrl = in.readString();
        id = in.readInt();
        popularity = in.readByte()==0x00?null:in.readDouble();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(movieName);
        dest.writeString(image);
        dest.writeString(overview);
        dest.writeString(releaseDate);
        dest.writeFloat(rating);
        dest.writeString(backdropUrl);
        dest.writeInt(id);
        if(popularity==null)
            dest.writeByte((byte) 0x00);
        else {
            dest.writeByte((byte) 0x11);
            dest.writeDouble(popularity);
        }
    }

    public static final Creator<Movies> CREATOR = new Creator<Movies>() {
        @Override
        public Movies createFromParcel(Parcel source) {
            return new Movies(source);
        }

        @Override
        public Movies[] newArray(int size) {
            return new Movies[size];
        }
    };
}
