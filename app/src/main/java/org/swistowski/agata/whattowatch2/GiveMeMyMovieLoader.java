package org.swistowski.agata.whattowatch2;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Loader;

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

        if(mUrl.equals(getContext().getString(R.string.sort_by_favorite_value))){
            AppDatabase db = AppDatabase.getInstance(getContext().getApplicationContext());
            List<FavoriteEntry> favorites = db.favoriteDao().loadAllFavorites();
            ArrayList<Movie> movies = new ArrayList<>();

            for(int i = 0; i < favorites.size(); i++) {
                FavoriteEntry favoriteEntry = favorites.get(i);
                Movie movie = NetworkUtils.fetchMovie(favoriteEntry.getId(),
                        getContext().getString(R.string.api_key));
                if(movie != null) {
                    movies.add(movie);
                }
            }

            return movies;
        }

        ArrayList<Movie> movies = NetworkUtils.fetchMovieData(mUrl, getContext().getString(R.string.api_key));
        return movies;
    }
}
