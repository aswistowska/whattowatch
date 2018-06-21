package org.swistowski.agata.whattowatch2;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.util.Log;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class NetworkUtils {

    private static final String LOG_TAG = NetworkUtils.class.getSimpleName();
    private static final String MOVIE_POPULAR_URL = "https://api.themoviedb.org/3/movie/popular";
    private static final String MOVIE_TOP_RATED_URL = "https://api.themoviedb.org/3/movie/top_rated";
    private static final String MOVIE_BASE_URL = "https://api.themoviedb.org/3/movie/";
    Context mContext;

    public static ArrayList<Movie> fetchMovieData(String requestUrl, String apiKey) {
        URL url = createUrl(requestUrl, apiKey);
        String jsonResponse = null;
        try {
            jsonResponse = getResponseFromHttpUrl(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }
        ArrayList<Movie> movies = MovieJsonUtils.extractMoviesFromJsonString(jsonResponse);

        return movies;
    }

    public static String getPreferredSortBy(Context context) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(context);
        String keyForSortBy = context.getString(R.string.sort_by_key);
        String defaultSortBy = context.getString(R.string.sort_by_default);
        return prefs.getString(keyForSortBy, defaultSortBy);
    }

    public static URL createUrl(String sortBy, String apiKey) {

        Uri builtUri = Uri.parse(MOVIE_BASE_URL + sortBy).buildUpon().
                appendQueryParameter("api_key", apiKey).build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    public static URL createMovieUrl (int id, String apiKey) {
        Uri builtUri = Uri.parse(MOVIE_BASE_URL + id).buildUpon().
                appendQueryParameter("api_key", apiKey).build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

//    public static URL getMovieRuntime (int id, String apiKey) {
//
//        URL url = createMovieUrl(id, apiKey);
//        String jsonResponse = null;
//        try {
//            jsonResponse = getResponseFromHttpUrl(url);
//        } catch (IOException e) {
//            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
//        }
//        ArrayList<Movie> movies = MovieJsonUtils.extractMoviesFromJsonString(jsonResponse);
//        return movies;
//    }

//    public static URL getMovieVideos(int id, String apiKey) {
//        URL url = createMovieUrl(id, apiKey);
//        String jsonResponse = null;
//        try {
//            jsonResponse = getResponseFromHttpUrl(url);
//        } catch (IOException e) {
//            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
//        }
//        return;
//    }

//    public static URL getMovieReviews(int id, String apiKey) {
//        URL url = createMovieUrl(id, apiKey);
//        String jsonResponse = null;
//        try {
//            jsonResponse = getResponseFromHttpUrl(url);
//        } catch (IOException e) {
//            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
//        }
//        return;
//    }


    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
