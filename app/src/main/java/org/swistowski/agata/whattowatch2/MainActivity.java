package org.swistowski.agata.whattowatch2;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MoviesAdapter.MoviesAdapterOnClickHandler,
        LoaderManager.LoaderCallbacks<ArrayList<Movie>>, SharedPreferences.OnSharedPreferenceChangeListener {

    private static final int MOVIE_LOADER_ID = 1;
    private MoviesAdapter mMoviesAdapter;
    private RecyclerView mRecyclerView;
    private TextView mEmptyStateTextView;
    private ProgressBar mLoadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.rv_movies);
        mEmptyStateTextView = findViewById(R.id.error_view);
        mEmptyStateTextView.setText(R.string.no_movies);
        mLoadingIndicator = findViewById(R.id.pb_loading_indicator);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,calculateNoOfColumns(this));
        mRecyclerView.setLayoutManager(gridLayoutManager);

        mMoviesAdapter = new MoviesAdapter(this, this);
        mRecyclerView.setAdapter(mMoviesAdapter);

        if (isNetworkAvailable(this)) {
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(MOVIE_LOADER_ID, null, this);
            showMovieDataView();

            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            preferences.registerOnSharedPreferenceChangeListener(this);

        } else {
            View loadingIndicator = findViewById(R.id.pb_loading_indicator);
            loadingIndicator.setVisibility(View.GONE);

            TextView NoInternetTextView = findViewById(R.id.error_view);
            NoInternetTextView.setText(R.string.no_internet_connection);
        }
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        switch (item.getItemId()) {
            case R.id.menuSortPopular:
                preferences.edit().putString(getString(R.string.sort_by_key),
                        getString(R.string.sort_by_popular_value)).apply();
                mRecyclerView.scrollToPosition(0);
                return true;
            case R.id.menuSortRating:
                preferences.edit().putString(getString(R.string.sort_by_key),
                        getString(R.string.sort_by_highest_rated_value)).apply();
                mRecyclerView.scrollToPosition(0);
                return true;
            case R.id.menuSortFavorite:
                preferences.edit().putString(getString(R.string.sort_by_key),
                        getString(R.string.sort_by_favorite_value)).apply();
                mRecyclerView.scrollToPosition(0);
                return true;
        }

        return super.onOptionsItemSelected(item);
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

    private void showMovieDataView() {
        mEmptyStateTextView.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void showEmptyStateTextView() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        mEmptyStateTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public Loader<ArrayList<Movie>> onCreateLoader(int i, Bundle bundle) {
        mLoadingIndicator.setVisibility(View.VISIBLE);
        return new GiveMeMyMovieLoader(this, NetworkUtils.getPreferredSortBy(this));
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Movie>> loader, ArrayList<Movie> movies) {
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        if (movies.size() == 0) {
            showEmptyStateTextView();
        }
        mMoviesAdapter.setMovies(movies);
    }
        /*
        gdzieś tutaj chyba powinnam ustawić coś, że jak favority są 0 to reszta ma się i tak odświeżyć normalnie :P
        ale za cholerę nie pamiętam co i jak tam było... ach ta skleroza :P

        Kocham Cię !
         */
    @Override
    public void onLoaderReset(Loader<ArrayList<Movie>> loader) {
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if(key.equals(getString(R.string.sort_by_key))) {
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.restartLoader(MOVIE_LOADER_ID, null, this);
        }
    }
}
