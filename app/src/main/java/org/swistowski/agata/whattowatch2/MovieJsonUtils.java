package org.swistowski.agata.whattowatch2;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public final class MovieJsonUtils {

    private MovieJsonUtils() {
    }


    public static ArrayList<Movie> extractMoviesFromJsonString(String moviesJSON) {
        ArrayList<Movie> movies = new ArrayList<>();

        try {
            JSONObject sampleJsonResponse = new JSONObject(moviesJSON);
            JSONArray movieArray = sampleJsonResponse.getJSONArray("results");

            for (int i = 0; i < movieArray.length(); i++) {
                JSONObject currentMovie = movieArray.getJSONObject(i);
                String title = currentMovie.getString("title");
                String releaseDate = currentMovie.getString("release_date");
                String posterUrl = currentMovie.getString("poster_path");
                double voteAverage = currentMovie.getDouble("vote_average");
                String overview = currentMovie.getString("overview");
                int id = currentMovie.getInt("id");


                Movie movie = new Movie(id, title, releaseDate, posterUrl, voteAverage, overview);
                movies.add(movie);
            }

        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the movie JSON results", e);
        }

        return movies;
    }
}
