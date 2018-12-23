package com.kasetti.pranav.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.kasetti.pranav.popularmovies.model.Movie;
import com.kasetti.pranav.popularmovies.model.MoviesSortType;
import com.kasetti.pranav.popularmovies.utilities.MoviesJsonUtils;
import com.kasetti.pranav.popularmovies.utilities.NetworkUtils;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MoviesAdapter.MoviesAdapterOnClickHandler {

    private RecyclerView mRecyclerView;
    private MoviesAdapter mMoviesAdapter;
    private TextView mErrorMessageDisplay;
    private ProgressBar mLoadingIndicator;

    private MoviesSortType currentMoviesSortType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
         * Using findViewById, we get a reference to our RecyclerView from xml. This allows us to
         * do things like set the adapter of the RecyclerView and toggle the visibility.
         */
        mRecyclerView = findViewById(R.id.recycler_view_movies);

        /* This TextView is used to display errors and will be hidden if there are no errors */
        mErrorMessageDisplay = findViewById(R.id.tv_error_message_display);

        /*
         * LinearLayoutManager can support HORIZONTAL or VERTICAL orientations. The reverse layout
         * parameter is useful mostly for HORIZONTAL layouts that should reverse for right to left
         * languages.
         */
        GridLayoutManager layoutManager
                = new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);

        mRecyclerView.setLayoutManager(layoutManager);

        /*
         * Use this setting to improve performance if you know that changes in content do not
         * change the child layout size in the RecyclerView
         */
        mRecyclerView.setHasFixedSize(true);

        /*
         * The MoviesAdapter is responsible for linking our movies data with the Views that
         * will end up displaying our movies data.
         */
        mMoviesAdapter = new MoviesAdapter(this);

        /* Setting the adapter attaches it to the RecyclerView in our layout. */
        mRecyclerView.setAdapter(mMoviesAdapter);

        /*
         * The ProgressBar that will indicate to the user that we are loading data. It will be
         * hidden when no data is loading.
         *
         * Please note: This so called "ProgressBar" isn't a bar by default. It is more of a
         * circle. We didn't make the rules (or the names of Views), we just follow them.
         */
        mLoadingIndicator = findViewById(R.id.pb_loading_indicator);

        /* Once all of our views are setup, we can load the weather data. */
        setCurrentMoviesSortTypeAndReload(MoviesSortType.MOST_POPULAR);
    }

    private void setCurrentMoviesSortTypeAndReload(MoviesSortType moviesSortType) {
        currentMoviesSortType = moviesSortType;
        loadMoviesData(currentMoviesSortType);
    }

    private void loadMoviesData(MoviesSortType moviesSortType) {
        showMovieImageViews();
        new FetchMoviesTask().execute(moviesSortType);
    }

    @Override
    public void onClick(Movie movie) {
        Context context = this;
        Class destinationClass = DetailActivity.class;
        Intent intentToStartDetailActivity = new Intent(context, destinationClass);
        String movieJson = MoviesJsonUtils.convertMovieToJsonString(movie);
        intentToStartDetailActivity.putExtra(Intent.EXTRA_TEXT, movieJson);
        startActivity(intentToStartDetailActivity);
    }

    private void showMovieImageViews() {
        /* First, make sure the error is invisible */
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        /* Then, make sure the movie data is visible */
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        /* First, hide the currently visible data */
        mRecyclerView.setVisibility(View.INVISIBLE);
        /* Then, show the error */
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    class FetchMoviesTask extends AsyncTask<MoviesSortType, Void, List<Movie>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<Movie> doInBackground(MoviesSortType... moviesSortType) {
            try {
                List<Movie> movies = NetworkUtils.getMoviesListForSortType(moviesSortType[0]);
                return movies;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<Movie> movies) {
            super.onPostExecute(movies);
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (movies != null) {
                showMovieImageViews();
                List<String> movieImageURLs = MoviesJsonUtils.getImageUrlsFromMovies(movies);
                mMoviesAdapter.setMoviesData(movies, movieImageURLs);
            } else {
                showErrorMessage();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /* Use AppCompatActivity's method getMenuInflater to get a handle on the menu inflater */
        MenuInflater inflater = getMenuInflater();
        /* Use the inflater's inflate method to inflate our menu layout to this menu */
        inflater.inflate(R.menu.main, menu);
        /* Return true so that the menu is displayed in the Toolbar */
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_refresh) {
            mMoviesAdapter.setMoviesData(null,null);
            loadMoviesData(currentMoviesSortType);
            return true;
        } else if (id == R.id.action_popular) {
            mMoviesAdapter.setMoviesData(null,null);
            setCurrentMoviesSortTypeAndReload(MoviesSortType.MOST_POPULAR);
            return true;
        } else if (id == R.id.action_top_rated) {
            mMoviesAdapter.setMoviesData(null,null);
            setCurrentMoviesSortTypeAndReload(MoviesSortType.TOP_RATED);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
