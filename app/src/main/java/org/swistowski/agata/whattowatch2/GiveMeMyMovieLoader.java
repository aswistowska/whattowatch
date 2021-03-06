package org.swistowski.agata.whattowatch2;

import android.content.AsyncTaskLoader;
import android.content.Context;

import org.swistowski.agata.whattowatch2.database.AppDatabase;
import org.swistowski.agata.whattowatch2.database.FavoriteEntry;

import java.util.ArrayList;
import java.util.List;

class GiveMeMyMovieLoader extends AsyncTaskLoader<ArrayList<Movie>> {

    private static final String LOG_TAG = GiveMeMyMovieLoader.class.getName();

    private String mUrl;

    public GiveMeMyMovieLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public ArrayList<Movie> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        ArrayList<Movie> movies = NetworkUtils.fetchMovieData(mUrl, getContext().getString(R.string.api_key));
        return movies;
    }
}
