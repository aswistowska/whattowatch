package org.swistowski.agata.whattowatch2;

public class Movie implements java.io.Serializable {

    public static final String IMAGE_API_BASE = "https://image.tmdb.org/t/p/w500";
    private String mTitle;
    private String mReleaseDate;
    private String mPosterUrl;
    private double mVoteAverage;
    private String mOverview;
    private String mDuration;


    public Movie(String title, String releaseDate, String posterUrl, double voteAverage,
                 String overview, String duration) {
        mTitle = title;
        mReleaseDate = releaseDate;
        mPosterUrl = posterUrl;
        mVoteAverage = voteAverage;
        mOverview = overview;
        mDuration = duration;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public String getPosterUrl() {
        return IMAGE_API_BASE + mPosterUrl;
    }

    public double getVoteAverage() {
        return mVoteAverage;
    }

    public String getOverview() {
        return mOverview;
    }

    public String getDuration() {
        return mDuration;
    }

}
