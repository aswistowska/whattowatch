package org.swistowski.agata.whattowatch2;

import java.util.ArrayList;

public class MovieDetails {

    int mRuntime;
    ArrayList<MovieVideo> mVideos;
    ArrayList<MovieReview> mReviews;

    public MovieDetails(int runtime,  ArrayList<MovieVideo> videos,   ArrayList<MovieReview> reviews) {
        mRuntime = runtime;
        mVideos = videos;
        mReviews = reviews;
    }

    public int getRuntime() {
        return mRuntime;
    }

    public ArrayList<MovieVideo> getVideos() {
        return mVideos;
    }

    public ArrayList<MovieReview> getReviews() {
        return mReviews;
    }

}

