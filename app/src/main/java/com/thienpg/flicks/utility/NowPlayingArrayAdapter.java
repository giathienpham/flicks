package com.thienpg.flicks.utility;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.thienpg.flicks.R;
import com.thienpg.flicks.model.NowPlaying;
import com.thienpg.flicks.model.Result;

import java.util.ArrayList;

import static com.thienpg.flicks.utility.Constant.IMAGE_URL;

/**
 * Created by tuan on 02/18/2017.
 */

public class NowPlayingArrayAdapter extends ArrayAdapter<Result> {
   private Context mContext;

    // View lookup cache
    private static class ViewHolder {
        ImageView poster;
        TextView title;
        TextView overview;
    }



    public NowPlayingArrayAdapter(Context context, NowPlaying nowPlaying) {
        super(context, R.layout.nowplaying_item, (ArrayList) nowPlaying.getResults());
        mContext = context;
    }



    @Override

    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position
        Result movie = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        if (convertView == null) {
            // If there's no view to re-use, inflate a brand new view for row
            viewHolder = new ViewHolder();

            LayoutInflater inflater = LayoutInflater.from(getContext());

            convertView = inflater.inflate(R.layout.nowplaying_item, parent, false);

            viewHolder.title = (TextView) convertView.findViewById(R.id.tvMovieTitle);
            viewHolder.overview = (TextView) convertView.findViewById(R.id.tvMovieOverview);
            viewHolder.poster = (ImageView) convertView.findViewById(R.id.ivPosterImage);

            // Cache the viewHolder object inside the fresh view
            convertView.setTag(viewHolder);

        } else {
            // View is being recycled, retrieve the viewHolder object from tag
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Populate the data from the data object via the viewHolder object
        // into the template view.

        viewHolder.title.setText(movie.getTitle());
        viewHolder.overview.setText(movie.getOverview());
//        viewHolder.poster.setDateFormatText(movie.getOverview());
        loadImageToView(movie.getPosterPath(), viewHolder.poster);

        // Return the completed view to render on screen

        return convertView;

    }

    private void loadImageToView(String imageUri, ImageView view){
        Log.d("ImageUrl", IMAGE_URL + imageUri);
        Picasso.with(mContext).load(IMAGE_URL + imageUri).resize(500, 500)
                .into(view);
    }

}