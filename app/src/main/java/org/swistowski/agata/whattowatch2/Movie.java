package org.swistowski.agata.whattowatch2;

public class Movie implements java.io.Serializable {

    public static final String IMAGE_API_BASE = "https://image.tmdb.org/t/p/w500";

    private int mId;
    private String mTitle;
    private String mReleaseDate;
    private String mPosterUrl;
    private double mVoteAverage;
    private String mOverview;


    public Movie(int id, String title, String releaseDate, String posterUrl, double voteAverage,
                 String overview) {
        mId = id;
        mTitle = title;
        mReleaseDate = releaseDate;
        mPosterUrl = posterUrl;
        mVoteAverage = voteAverage;
        mOverview = overview;
    }

    public int getId() {
        return mId;
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

}
