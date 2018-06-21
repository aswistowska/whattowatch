package org.swistowski.agata.whattowatch2;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Menu;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MoviesAdapter.MoviesAdapterOnClickHandler {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<Movie> movies = new ArrayList<>();
        movies.add(new Movie("Deadpool 2", "2018-05-15", "/to0spRl1CMDvyUbOnbb4fTk3VAd.jpg",
                8, "Wisecracking mercenary Deadpool battles the evil and powerful Cable and other bad guys to save a boy's life.", "120min"));
        movies.add(new Movie("Deadpool 2", "2018-05-15", "/to0spRl1CMDvyUbOnbb4fTk3VAd.jpg",
                8, "Wisecracking mercenary Deadpool battles the evil and powerful Cable and other bad guys to save a boy's life.", "120min"));
        movies.add(new Movie("Deadpool 2", "2018-05-15", "/to0spRl1CMDvyUbOnbb4fTk3VAd.jpg",
                8, "Wisecracking mercenary Deadpool battles the evil and powerful Cable and other bad guys to save a boy's life.", "120min"));

        RecyclerView recyclerView = findViewById(R.id.rv_movies);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,calculateNoOfColumns(this));
        recyclerView.setLayoutManager(gridLayoutManager);

        MoviesAdapter adapter = new MoviesAdapter(this, this);
        adapter.setMovies(movies);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(Movie chosenMovie) {
        Context context = this;
        Class destinationClass = DetailsActivity.class;
        Intent intentToStartDetailActivity = new Intent(context, destinationClass);
        intentToStartDetailActivity.putExtra("movie", chosenMovie);
        startActivity(intentToStartDetailActivity);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int scalingFactor = 200;
        int noOfColumns = (int) (dpWidth / scalingFactor);
        if(noOfColumns < 2)
            noOfColumns = 2;
        return noOfColumns;
    }

    /**
     * Returns true if network is available or about to become available
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }
    //You can then use the code below to check for internet connections:
    //
    //if(isNetworkAvailable(this)) {
    // Run network query i.e execute FetchDataTask
    //}
    //else{
    //
}
