package com.example.android.popularmovies;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import com.example.android.popularmovies.data.MovieContract;
import com.example.android.popularmovies.data.MovieContract.MovieEntry;
import java.util.ArrayList;

public class MoviesDB {
    static final String AUTHORITY_Uri = "content://" + MovieContract.AUTHORITY;

    public boolean isMovieFavorited(ContentResolver contentResolver, int id){
        boolean ret = false;
        Cursor cursor = contentResolver.query(Uri.parse(AUTHORITY_Uri + "/" + id), null, null, null, null, null);
        if (cursor != null && cursor.moveToNext()){
            ret = true;
            cursor.close();
        }
        return ret;
    }

    public void addMovie(ContentResolver contentResolver, Movies movie){
        ContentValues contentValues = new ContentValues();
        contentValues.put(MovieEntry.COLUMN_ID, movie.id);
        contentValues.put(MovieEntry.COLUMN_NAME, movie.movieName);
        contentValues.put(MovieEntry.COLUMN_OVERVIEW, movie.overview);
        contentValues.put(MovieEntry.COLUMN_POSTER, movie.image);
        contentValues.put(MovieEntry.COLUMN_BACKDROP, movie.backdropUrl);
        contentValues.put(MovieEntry.COLUMN_RATING, movie.rating + "");
        contentValues.put(MovieEntry.COLUMN_RELEASE, movie.releaseDate);
        contentResolver.insert(Uri.parse(AUTHORITY_Uri + "/movies"), contentValues);
    }

    public void removeMovie(ContentResolver contentResolver, int id){
        Uri uri = Uri.parse(AUTHORITY_Uri + "/" + id);
        contentResolver.delete(uri, null, new String[]{id + ""});
    }

    public ArrayList<Movies> getFavoriteMovies(ContentResolver contentResolver){
        Uri uri = Uri.parse(AUTHORITY_Uri + "/movies");
        Cursor cursor = contentResolver.query(uri, null, null, null, null, null);
        ArrayList <Movies> movies = new ArrayList<>();

        if (cursor != null && cursor.moveToFirst()){
            do {
                Movies movie = new Movies();
                movie.id = cursor.getInt(cursor.getColumnIndex(MovieEntry.COLUMN_ID));
                movie.movieName = cursor.getString(cursor.getColumnIndex(MovieEntry.COLUMN_NAME));
                movie.overview = cursor.getString(cursor.getColumnIndex(MovieEntry.COLUMN_OVERVIEW));
                movie.rating = Float.parseFloat(cursor.getString(cursor.getColumnIndex(MovieEntry.COLUMN_RATING)));
                movie.image = cursor.getString(cursor.getColumnIndex(MovieEntry.COLUMN_POSTER));
                movie.backdropUrl=cursor.getString(cursor.getColumnIndex(MovieEntry.COLUMN_BACKDROP));
                movie.releaseDate = cursor.getString(cursor.getColumnIndex(MovieEntry.COLUMN_RELEASE));
                movies.add(movie);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return movies;
    }
}
