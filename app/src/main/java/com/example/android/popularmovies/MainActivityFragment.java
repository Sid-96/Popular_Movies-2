package com.example.android.popularmovies;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private static final String TAG = MainActivityFragment.class.getSimpleName();
    public View mainFragmentView;
    public TextView emptyView;
    private GridView mGridView;
    private RequestQueue mRequestQueue;
    private boolean isDualPane = false;
    private MovieAdapter mMovieAdapter;
    private ArrayList<Movies> mMoviesData;
    public static MainActivityFragment instance;
    public static String sort_order = "popular", moreParams = "";
    public static boolean cached = false;
    public int gridPos = -1;

    public MainActivityFragment() {
        instance = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        mainFragmentView = rootView;
        emptyView = new TextView(getContext());
        emptyView.setText("Images Loading");
        mGridView = (GridView) rootView.findViewById(R.id.movies_grid);
        mGridView.setEmptyView(emptyView);
        mMoviesData = new ArrayList<Movies>();
        mRequestQueue = Volley.newRequestQueue(getContext());
        mMovieAdapter = new MovieAdapter(getContext());
        mGridView.setAdapter(mMovieAdapter);
        updateUI(cached);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // If tablet layout (dualpane)
                if(isDualPane){
                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    DetailActivityFragment detailActivityFragment = DetailActivityFragment.newInstance(mMoviesData.get(position));
                    fragmentTransaction.replace(R.id.detailContainer,detailActivityFragment);
                    fragmentTransaction.commit();
                }
                else{
                    Intent intent = new Intent(getContext(),DetailActivity.class);
                    intent.putExtra(Intent.EXTRA_TEXT,(Parcelable) mMoviesData.get(position));
                    startActivity(intent);
                }
            }
        });
        if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            setGridColCount(3);
        else
            setGridColCount(2);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isDualPane = getPaneLayout();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("gridview_pos", mGridView.getFirstVisiblePosition());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null)
            gridPos = savedInstanceState.getInt("gridview_pos");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mRequestQueue.cancelAll(new RequestQueue.RequestFilter() {
            @Override
            public boolean apply(Request<?> request) {
                return true;
            }
        });
    }


    public void updateUI(boolean cache) {
        mMoviesData.clear();
        mMovieAdapter.clearMovies();
        this.cached = cache;
        if(!cached)
            getMovies(sort_order);
        else
            getFavorites();
    }

    private void getMovies(String sort_order) {
        /*String urlString = "http://api.themoviedb.org/3/movie?sort_by=" + sort_order + "&" + moreParams
                + "&api_key=" + BuildConfig.API;*/
        String urlString1 = "http://api.themoviedb.org/3/movie/" + sort_order + "?api_key=" + BuildConfig.API;

        if(sort_order.equals("now_playing")){
            JsonObjectRequest req = new JsonObjectRequest(urlString1, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONArray results = response.getJSONArray("results");
                        JSONObject result;
                        for(int i=0 ; i<results.length() ; i++){
                            Movies movies_item = new Movies();
                            result = results.getJSONObject(i);
                            movies_item.movieName = result.getString("original_title");
                            movies_item.id = result.getInt("id");
                            movies_item.image = "http://image.tmdb.org/t/p/w500/" + result.getString("poster_path");
                            movies_item.overview = result.getString("overview");
                            movies_item.rating = (float) result.getDouble("vote_average");
                            movies_item.releaseDate = result.getString("release_date");
                            movies_item.backdropUrl = "http://image.tmdb.org/t/p/w780/" + result.getString("backdrop_path");
                            movies_item.popularity = result.getDouble("popularity");
                            mMoviesData.add(movies_item);
                            mMovieAdapter.addMovie(movies_item.image);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mGridView.setAdapter(mMovieAdapter);
                            if(gridPos>-1)
                                mGridView.setSelection(gridPos);
                            gridPos = -1;
                        }
                    });
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });

            mRequestQueue.add(req);
        }
        else {
            JsonObjectRequest req = new JsonObjectRequest(urlString1, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONArray results = response.getJSONArray("results");
                        JSONObject result;
                        for(int i=0 ; i<results.length() ; i++){
                            Movies movies_item = new Movies();
                            result = results.getJSONObject(i);
                            movies_item.movieName = result.getString("original_title");
                            movies_item.id = result.getInt("id");
                            movies_item.image = "http://image.tmdb.org/t/p/w500/" + result.getString("poster_path");
                            movies_item.overview = result.getString("overview");
                            movies_item.rating = (float) result.getDouble("vote_average");
                            movies_item.releaseDate = result.getString("release_date");
                            movies_item.backdropUrl = "http://image.tmdb.org/t/p/w780/" + result.getString("backdrop_path");
                            movies_item.popularity = result.getDouble("popularity");
                            mMoviesData.add(movies_item);
                            mMovieAdapter.addMovie(movies_item.image);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mGridView.setAdapter(mMovieAdapter);
                            if(gridPos>-1)
                                mGridView.setSelection(gridPos);
                            gridPos = -1;
                        }
                    });
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });

            mRequestQueue.add(req);

        }

    }
    public void getFavorites(){
        mMoviesData.addAll((new MoviesDB()).getFavoriteMovies(getContext().getContentResolver()));
        for (Movies movie : mMoviesData){
            mMovieAdapter.addMovie(movie.image);
        }
        mGridView.setAdapter(mMovieAdapter);
        if (gridPos > -1)
            mGridView.setSelection(gridPos);
        gridPos = -1;
    }

    public void updateFavoritesGrid(){
        if (cached) {
            int p = mGridView.getLastVisiblePosition();
            updateUI(true);
            mGridView.smoothScrollToPosition(p);
        }
    }

    public void setGridColCount(int n){
        ((GridView) mainFragmentView.findViewById(R.id.movies_grid)).setNumColumns(n);
    }

    public boolean getPaneLayout(){
        return (getActivity().findViewById(R.id.detailContainer) != null);
    }

}