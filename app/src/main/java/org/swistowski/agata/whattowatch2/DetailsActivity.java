package org.swistowski.agata.whattowatch2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;



public class DetailsActivity extends AppCompatActivity {

    Movie mMovie;

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

           TextView durationTextView = findViewById(R.id.durationTextView);
           durationTextView.setText(mMovie.getDuration());

           TextView ratingTextView = findViewById(R.id.ratingTextView);
           ratingTextView.setText(Double.toString(mMovie.getVoteAverage()));

           TextView overviewTextView = findViewById(R.id.overviewTextView);
           overviewTextView.setText(mMovie.getOverview());
       }
    }
}

