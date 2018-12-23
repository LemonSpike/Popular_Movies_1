package com.kasetti.pranav.popularmovies.utilities;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.kasetti.pranav.popularmovies.model.Movie;
import com.kasetti.pranav.popularmovies.model.MoviesSortType;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

/**
 * These utilities will be used to communicate with the themoviedb servers.
 */

public final class NetworkUtils {
    private static final String TAG = NetworkUtils.class.getSimpleName();

    private static final String API_KEY = ""; //Insert API Key here
    private static final String POPULAR_MOVIES_BASE_URL = "http://api.themoviedb.org/3/movie/popular";
    private static final String TOP_RATED_MOVIES_BASE_URL = "http://api.themoviedb.org/3/movie/top_rated";
    private static final String MOVIE_IMAGE_BASE_URL = "http://image.tmdb.org/t/p/";

    /* The image size we want the API to return. */
    private static final String imageDefaultSize = "w185";
    private static final String API_KEY_PARAM = "api_key";

    private static URL buildMostPopularMoviesURL() {
        return buildMoviesURL(POPULAR_MOVIES_BASE_URL);
    }

    private static URL buildTopRatedMoviesURL() {
        return buildMoviesURL(TOP_RATED_MOVIES_BASE_URL);
    }

    private static URL buildMoviesURL(String baseURL) {
        Uri builtUri = Uri.parse(baseURL).buildUpon()
                .appendQueryParameter(API_KEY_PARAM, API_KEY).build();
        URL moviesURL = null;
        try {
            moviesURL = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.v(TAG, "Built Movies URL " + moviesURL);

        return moviesURL;
    }

    public static String buildImageURLString(String posterPath) {
        Uri builtUri = Uri.parse(MOVIE_IMAGE_BASE_URL).buildUpon()
                        .appendPath(imageDefaultSize)
                        .appendPath(posterPath)
                        .appendQueryParameter(API_KEY_PARAM, API_KEY)
                        .build();
        String imageURLString = builtUri.toString();
        Log.v(TAG, "Built Image URL " + imageURLString);

        return imageURLString;
    }

    public static List<Movie> getMoviesListForSortType(@NonNull MoviesSortType moviesSortType) throws IOException {
        URL url = null;
        if (moviesSortType == MoviesSortType.MOST_POPULAR) {
            url = buildMostPopularMoviesURL();
        } else if (moviesSortType == MoviesSortType.TOP_RATED) {
            url = buildTopRatedMoviesURL();
        }
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                String input = scanner.next();
                return MoviesJsonUtils.getMovies(input);
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
