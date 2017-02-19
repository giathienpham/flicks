package com.thienpg.flicks.utility;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.thienpg.flicks.MovieActivity;
import com.thienpg.flicks.R;
import com.thienpg.flicks.model.NowPlaying;
import com.thienpg.flicks.model.Result;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

import static com.thienpg.flicks.utility.Constant.IMAGE_URL;

/**
 * Created by tuan on 02/18/2017.
 */

public class NowPlayingArrayAdapter extends ArrayAdapter<Result> {
   private Context mContext;

    // View lookup cache
    static class ViewHolder {
       @BindView(R.id.ivPosterImage) ImageView poster;
        @BindView(R.id.tvMovieTitle)  TextView title;
        @BindView(R.id.tvMovieOverview)  TextView overview;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }



    public NowPlayingArrayAdapter(Context context, NowPlaying nowPlaying) {
        super(context, R.layout.nowplaying_item, (ArrayList) nowPlaying.getResults());
        mContext = context;
    }




    @Override

    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position
        final Result movie = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        if (convertView == null) {
            // If there's no view to re-use, inflate a brand new view for row
            LayoutInflater inflater = LayoutInflater.from(getContext());

            convertView = inflater.inflate(R.layout.nowplaying_item, parent, false);

            viewHolder = new ViewHolder(convertView);
//            viewHolder.title = (TextView) convertView.findViewById(R.id.tvMovieTitle);
//            viewHolder.overview = (TextView) convertView.findViewById(R.id.tvMovieOverview);
//            viewHolder.poster = (ImageView) convertView.findViewById(R.id.ivPosterImage);

            // Cache the viewHolder object inside the fresh view
            convertView.setTag(viewHolder);

        } else {
            // View is being recycled, retrieve the viewHolder object from tag
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.title.setText(movie.getTitle());
        viewHolder.overview.setText(movie.getOverview());
        loadPosterToView(movie.getPosterPath(), viewHolder.poster);
        // Populate the data from the data object via the viewHolder object
        // into the template view.
        int orientation = getContext().getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            loadPosterToView(movie.getPosterPath(), viewHolder.poster);
        } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            loadBackdropToView(movie.getBackdropPath(), viewHolder.poster);
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), MovieActivity.class);
                i.putExtra("backdrop", movie.getBackdropPath());
                i.putExtra("title", movie.getTitle());
                i.putExtra("date", movie.getReleaseDate());
                i.putExtra("rating", movie.getVoteAverage()+"");
                i.putExtra("overview", movie.getOverview());
                getContext().startActivity(i);
            }
        });

        // Return the completed view to render on screen
        return convertView;

    }

    private void loadPosterToView(String imageUri, ImageView view){
        Log.d("ImageUrl", IMAGE_URL + imageUri);
        Picasso.with(mContext).load(IMAGE_URL + imageUri)
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .transform(new RoundedCornersTransformation(20, 20)).resize(500, 0)
                .into(view);
    }

    private void loadBackdropToView(String imageUri, ImageView view){
        Log.d("ImageUrl", IMAGE_URL + imageUri);
        Picasso.with(mContext).load(IMAGE_URL + imageUri).resize(1000, 0)
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .transform(new RoundedCornersTransformation(20, 20))
                .into(view);
    }

}