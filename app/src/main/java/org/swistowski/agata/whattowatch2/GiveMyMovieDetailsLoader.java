package org.swistowski.agata.whattowatch2;

import android.os.AsyncTask;

import java.util.ArrayList;


public class GiveMyMovieDetailsLoader extends AsyncTask {

    Movie mMovie;


    @Override
    protected Object doInBackground(Object[] objects) {

        int moviesRuntime = NetworkUtils.getMovieRuntime(mMovie.getId(), String.valueOf(R.string.api_key));

        ArrayList<MovieVideo> moviesVideos = NetworkUtils.getMovieVideos(mMovie.getId(), String.valueOf(R.string.api_key));

        ArrayList<MovieReview> moviesReviews = NetworkUtils.getMovieReviews(mMovie.getId(), String.valueOf(R.string.api_key));

        return new MovieDetails(moviesRuntime, moviesVideos, moviesReviews);
    }
}