package com.kasetti.pranav.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.kasetti.pranav.popularmovies.model.Movie;
import com.kasetti.pranav.popularmovies.utilities.MoviesJsonUtils;
import com.kasetti.pranav.popularmovies.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    private ImageView movieThumbnailImageView;
    private TextView originalTitleTextView;
    private TextView originalTitleHeaderTextView;
    private TextView titleOverviewTextView;
    private TextView overviewTextView;
    private TextView titleUserRatingTextView;
    private TextView userRatingTextView;
    private TextView titleReleaseDateTextView;
    private TextView releaseDateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        movieThumbnailImageView = findViewById(R.id.iv_movie_thumbnail);
        originalTitleTextView = findViewById(R.id.tv_original_title);
        originalTitleHeaderTextView = findViewById(R.id.tv_original_title_heading);
        titleOverviewTextView = findViewById(R.id.tv_title_plot_synopsis);
        overviewTextView = findViewById(R.id.tv_plot_synopsis);
        titleUserRatingTextView = findViewById(R.id.tv_title_user_rating);
        userRatingTextView = findViewById(R.id.tv_user_rating);
        titleReleaseDateTextView = findViewById(R.id.tv_title_release_date);
        releaseDateTextView = findViewById(R.id.tv_release_date);

        Intent startDetailActivityIntent = getIntent();

        if (startDetailActivityIntent.hasExtra(Intent.EXTRA_TEXT)) {
            String intentText = startDetailActivityIntent.getStringExtra(Intent.EXTRA_TEXT);
            populateViews(MoviesJsonUtils.deserializeMovieFromJsonString(intentText));
        }
    }

    private void populateViews(Movie movie) {
        populateImageView(movie);
        originalTitleTextView.setText(movie.getOriginalTitle());
        originalTitleHeaderTextView.setText(R.string.original_title_heading);
        titleOverviewTextView.setText(R.string.overview_title);
        overviewTextView.setText(movie.getOverview());
        titleUserRatingTextView.setText(R.string.user_rating_title);
        userRatingTextView.setText(String.valueOf(movie.getUserRating()));
        titleReleaseDateTextView.setText(R.string.release_date_title);
        releaseDateTextView.setText(movie.getReleaseDateString());
    }

    private void populateImageView(Movie movie) {
        String imageURLString = NetworkUtils.buildImageURLString(movie.getImageThumbnailURLPath().substring(1));
        Picasso.get().load(imageURLString).placeholder(R.drawable.placeholder).into(movieThumbnailImageView);
    }
}
