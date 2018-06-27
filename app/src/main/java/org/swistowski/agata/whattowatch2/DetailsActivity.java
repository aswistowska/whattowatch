package org.swistowski.agata.whattowatch2;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.swistowski.agata.whattowatch2.database.AppDatabase;
import org.swistowski.agata.whattowatch2.database.FavoriteEntry;

import java.util.ArrayList;
import java.util.List;


public class DetailsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<MovieDetails>{

    Movie mMovie;
    private AppDatabase mDb;
    private static final int MOVIE_DETAILS_LOADER_ID = 1;
    private boolean mIsFavorite;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mDb = AppDatabase.getInstance(getApplicationContext());


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

           TextView ratingTextView = findViewById(R.id.ratingTextView);
           ratingTextView.setText(Double.toString(mMovie.getVoteAverage()));

           TextView overviewTextView = findViewById(R.id.overviewTextView);
           overviewTextView.setText(mMovie.getOverview());

           LoaderManager loaderManager = getLoaderManager();
           loaderManager.initLoader(MOVIE_DETAILS_LOADER_ID, null, this);

           Button favoriteButton = findViewById(R.id.favoriteButton);
           favoriteButton.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   onFavoriteButtonClicked();
               }
           });
           AppExecutors.getInstance().diskIO().execute(new Runnable() {
               @Override
               public void run() {
                   mIsFavorite = mDb.favoriteDao().getFavorite(mMovie.getId()).size()>0;
                   runOnUiThread(new Runnable() {
                       @Override
                       public void run() {
                           updateFavoriteText();
                       }
                   });
               }
           });
       }
    }

    private void updateFavoriteText() {
        Button favoriteButton = findViewById(R.id.favoriteButton);
        if(!mIsFavorite) {
            favoriteButton.setText(R.string.mark_as_favorite);
        } else {
            favoriteButton.setText(R.string.remove_from_favorite);
        }
    }

    private void onFavoriteButtonClicked() {
        final FavoriteEntry favoriteEntry = new FavoriteEntry(mMovie.getId());
        if(!mIsFavorite) {
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    mDb.favoriteDao().insertFavorite(favoriteEntry);
                    //Toast.makeText(this, R.string.saved_favorite_movie, Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    mDb.favoriteDao().deleteFavorite(favoriteEntry);
                    //Toast.makeText(this, R.string.movie_removed, Toast.LENGTH_SHORT).show();
                }
            });
        }
        mIsFavorite = !mIsFavorite;
        updateFavoriteText();
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

        ListView reviewListView = findViewById(R.id.reviewsList);
        MovieReviewAdapter movieReviewAdapter = new MovieReviewAdapter(this, movieReviews);
        reviewListView.setAdapter(movieReviewAdapter);

         TextView runtimeTextView = findViewById(R.id.durationTextView);
        runtimeTextView.setText(Integer.toString(runtime) + getString(R.string.min));
    }

    @Override
    public void onLoaderReset(Loader loader) {

    }
}

