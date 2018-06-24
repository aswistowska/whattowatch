package org.swistowski.agata.whattowatch2;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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

        final MovieVideo movieVideo = mMovieVideos.get(position);

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item_video, parent, false);
        }

        Button playButton = listItemView.findViewById(R.id.playButton);
        playButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                watchYoutubeVideo(getContext(), movieVideo.getVideoKey());
            }
        });

        TextView videoName = listItemView.findViewById(R.id.trailerTitleTextView);
        videoName.setText(movieVideo.getVideoName());

        return listItemView;
    }

    public static void watchYoutubeVideo(Context context, String key){
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + key));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.youtube.com/watch?v=" + key));
        try {
            context.startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            context.startActivity(webIntent);
        }
    }
}
