package com.thienpg.flicks;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

import static com.thienpg.flicks.utility.Constant.IMAGE_URL;

public class MovieActivity extends AppCompatActivity {

    @BindView(R.id.tvDetailMovieTitle)
    TextView title;

    @BindView(R.id.ivDetailBackdrop)
    ImageView backdrop;

    @BindView(R.id.tvDetailDate)
    TextView date;

    @BindView(R.id.rbDetailRating)
    RatingBar rating;

    @BindView(R.id.tvDetailMovieOverview)
    TextView overview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        ButterKnife.bind(this);
        Intent intent = getIntent();

        title.setText(intent.getStringExtra("title"));
        date.setText("Release Date: "  + intent.getStringExtra("date"));
        rating.setRating(Float.parseFloat(intent.getStringExtra("rating")));
        overview.setText(intent.getStringExtra("overview"));
        loadBackdropToView(intent.getStringExtra("backdrop"),backdrop);

    }

    public void onSubmit(View v) {
        // closes the activity and returns to first screen
        this.finish();
    }

    private void loadBackdropToView(String imageUri, ImageView view){
        Log.d("ImageUrl", IMAGE_URL + imageUri);
        Picasso.with(this).load(IMAGE_URL + imageUri).resize(1000, 0)
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .transform(new RoundedCornersTransformation(20, 20))
                .into(view);
    }
}
