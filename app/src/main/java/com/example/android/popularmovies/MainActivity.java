package com.example.android.popularmovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    static int activeId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        if(activeId == 0)   activeId = R.id.sort_popularity;
        else
            menu.findItem(activeId).setChecked(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        MainActivityFragment fragment = MainActivityFragment.instance;

        if (id == R.id.sort_rating) {
            MainActivityFragment.sort_order = "top_rated";
            //MainActivityFragment.moreParams = "vote_count.gte=50&include_video=false";
        }
        else if (id == R.id.sort_popularity) {
            MainActivityFragment.sort_order = "popular";
            MainActivityFragment.moreParams = "";
        }
        else if (id == R.id.sort_now_playing) {
            MainActivityFragment.sort_order = "now_playing";
            MainActivityFragment.moreParams = "";
        }



        item.setChecked(true);
        if (id == R.id.sort_popularity || id == R.id.sort_rating || id == R.id.sort_now_playing){
            fragment.updateUI(false);
            activeId = id;
        } else if (id == R.id.sort_favourite){
            fragment.updateUI(true);
            activeId = id;
        }
        return super.onOptionsItemSelected(item);
    }
}
