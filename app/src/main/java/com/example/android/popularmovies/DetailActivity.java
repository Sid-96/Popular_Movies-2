package com.example.android.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {
    CollapsingToolbarLayout collapsingToolbarLayout;
    Movies movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ImageView header = (ImageView) findViewById(R.id.headerimage);
        Toolbar toolbar = (Toolbar) findViewById(R.id.anim_toolbar);
        setSupportActionBar(toolbar);
        movies = getIntent().getParcelableExtra(Intent.EXTRA_TEXT);
        DetailActivityFragment detailActivityFragment = (DetailActivityFragment) getSupportFragmentManager().findFragmentById(R.id.detailFragment);
        detailActivityFragment.movie = movies;
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        assert collapsingToolbarLayout != null;
        collapsingToolbarLayout.setTitle(movies.movieName);
        Picasso.with(getApplicationContext()).load(movies.backdropUrl).into(header);

    }

}
