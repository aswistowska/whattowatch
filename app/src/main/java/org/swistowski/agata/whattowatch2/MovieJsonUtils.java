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

    public static int extractMovieRuntimeFromJsonString(String movieJSON) {
        try {
            JSONObject movieRuntimeJsonObject = new JSONObject(movieJSON);
            return movieRuntimeJsonObject.getInt("runtime");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static ArrayList<MovieVideo> extractMovieVideosFromJsonString(String movieJSON) {
        ArrayList<MovieVideo> movieVideos = new ArrayList<>();

        try {
            JSONObject movieVideoJsonObject = new JSONObject(movieJSON);
            JSONArray movieVideosArray = movieVideoJsonObject.getJSONArray("results");

            for (int i = 0; i < movieVideosArray.length(); i++) {
                JSONObject currentVideo = movieVideosArray.getJSONObject(i);
                String videoKey = currentVideo.getString("key");
                String videoName = currentVideo.getString("name");

                MovieVideo movieVideo = new MovieVideo(videoKey, videoName);
                movieVideos.add(movieVideo);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return movieVideos;
    }

    public static ArrayList<MovieReview> extractMovieReviewsFromJsonString(String movieJSON) {
        ArrayList<MovieReview> movieReviews = new ArrayList<>();

        try {
            JSONObject movieReviewJsonObject = new JSONObject(movieJSON);
            JSONArray movieReviewArray = movieReviewJsonObject.getJSONArray("results");

            for (int i = 0; i < movieReviewArray.length(); i++) {
                JSONObject currentReview = movieReviewArray.getJSONObject(i);
                String author = currentReview.getString("author");
                String content = currentReview.getString("content");

                MovieReview movieReview = new MovieReview(author, content);
                movieReviews.add(movieReview);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return movieReviews;
    }
}
