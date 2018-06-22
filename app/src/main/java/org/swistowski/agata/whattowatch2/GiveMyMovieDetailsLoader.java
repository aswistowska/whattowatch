package org.swistowski.agata.whattowatch2;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.ArrayList;


public class GiveMyMovieDetailsLoader extends AsyncTaskLoader<MovieDetails> {

    final Movie mMovie;

    public GiveMyMovieDetailsLoader(Context context, Movie movie) {
        super(context);
        mMovie = movie;
    }


    @Override
    protected void onStartLoading() {
        forceLoad();
    }


    @Override
    public MovieDetails loadInBackground() {

        int moviesRuntime = NetworkUtils.getMovieRuntime(mMovie.getId(), String.valueOf(R.string.api_key));

        ArrayList<MovieVideo> moviesVideos = NetworkUtils.getMovieVideos(mMovie.getId(), String.valueOf(R.string.api_key));

        ArrayList<MovieReview> moviesReviews = NetworkUtils.getMovieReviews(mMovie.getId(), String.valueOf(R.string.api_key));

        return new MovieDetails(moviesRuntime, moviesVideos, moviesReviews);
    }
}