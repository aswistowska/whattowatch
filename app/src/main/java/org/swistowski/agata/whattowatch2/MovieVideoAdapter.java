package org.swistowski.agata.whattowatch2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class MovieVideoAdapter extends ArrayAdapter<MovieVideo> {

    final ArrayList<MovieVideo> mMovieVideos;

    public MovieVideoAdapter(@NonNull Context context, @NonNull ArrayList<MovieVideo> videos) {
        super(context, 0, videos);
        mMovieVideos = videos;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item_video, parent, false);
        }

        Button playButton = listItemView.findViewById(R.id.playButton);

        TextView videoName = listItemView.findViewById(R.id.trailerTitleTextView);
        videoName.setText(mMovieVideos.get(position).getVideoName());

        return listItemView;
    }
}
