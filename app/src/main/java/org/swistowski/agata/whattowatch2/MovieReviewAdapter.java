package org.swistowski.agata.whattowatch2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MovieReviewAdapter extends ArrayAdapter<MovieReview> {

    final ArrayList<MovieReview> mMovieReviews;

    public MovieReviewAdapter(@NonNull Context context, @NonNull ArrayList<MovieReview> reviews) {
        super(context, 0, reviews);
        mMovieReviews = reviews;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item_review, parent, false);
        }

        MovieReview movieReview = mMovieReviews.get(position);

        TextView reviewContentTextView = listItemView.findViewById(R.id.reviewContentTextView);
        reviewContentTextView.setText(movieReview.getContent());

        TextView reviewAuthorTextView = listItemView.findViewById(R.id.reviewAuthorTextView);
        reviewAuthorTextView.setText(movieReview.getAuthor());

        return listItemView;
    }
}
