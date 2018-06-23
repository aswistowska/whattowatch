package org.swistowski.agata.whattowatch2;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class DetailsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<MovieDetails>{

    Movie mMovie;
    private static final int MOVIE_DETAILS_LOADER_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

       Intent intent = getIntent();
       if (intent != null) {
           if (intent.hasExtra("movie")) {
               mMovie = (Movie) intent.getSerializableExtra("movie");
           }
       }
       if (mMovie != null) {

           ImageView posterImageView = findViewById(R.id.detailPosterImageView);
           Picasso.with(this).load(mMovie.getPosterUrl()).into(posterImageView);

           TextView movieTitle = findViewById(R.id.titleTextView);
           movieTitle.setText(mMovie.getTitle());

           TextView releaseDateTextView = findViewById(R.id.releaseDateTextView);
           releaseDateTextView.setText(mMovie.getReleaseDate());

//          TextView durationTextView = findViewById(R.id.durationTextView);
//          durationTextView.setText(mMovie.getDuration());

           TextView ratingTextView = findViewById(R.id.ratingTextView);
           ratingTextView.setText(Double.toString(mMovie.getVoteAverage()));

           TextView overviewTextView = findViewById(R.id.overviewTextView);
           overviewTextView.setText(mMovie.getOverview());

           LoaderManager loaderManager = getLoaderManager();
           loaderManager.initLoader(MOVIE_DETAILS_LOADER_ID, null, this);
       }
    }


    @Override
    public Loader<MovieDetails> onCreateLoader(int i, Bundle bundle) {
        return new GiveMyMovieDetailsLoader(this, mMovie);
    }

    @Override
    public void onLoadFinished(Loader<MovieDetails> loader, MovieDetails movieDetails) {
        int runtime = movieDetails.getRuntime();
        ArrayList<MovieReview> movieReviews = movieDetails.getReviews();
        ArrayList<MovieVideo> movieVideos = movieDetails.getVideos();

        ListView videoListView = findViewById(R.id.trailerList);
        MovieVideoAdapter movieVideoAdapter = new MovieVideoAdapter(this, movieVideos);
        videoListView.setAdapter(movieVideoAdapter);
        justifyListViewHeightBasedOnChildren(videoListView);

        ListView reviewListView = findViewById(R.id.reviewsList);
        MovieReviewAdapter movieReviewAdapter = new MovieReviewAdapter(this, movieReviews);
        reviewListView.setAdapter(movieReviewAdapter);
        justifyListViewHeightBasedOnChildren(reviewListView);


        // mReviewsAdapter.setReviews(movieReviews);
        // mVideosAdapter.setVideos(movieVideos);
        // TextView runtimeTextView = findViewById(R.id.runtimeTextView);
        // runtimeTextView.setText(Integer.toString(runtime));
    }

    @Override
    public void onLoaderReset(Loader loader) {

    }

    public static void justifyListViewHeightBasedOnChildren (ListView listView) {

        ListAdapter adapter = listView.getAdapter();

        if (adapter == null) {
            return;
        }
        ViewGroup vg = listView;
        int totalHeight = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            View listItem = adapter.getView(i, null, vg);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams par = listView.getLayoutParams();
        par.height = totalHeight + (listView.getDividerHeight() * (adapter.getCount() - 1));
        listView.setLayoutParams(par);
        listView.requestLayout();
    }
}

