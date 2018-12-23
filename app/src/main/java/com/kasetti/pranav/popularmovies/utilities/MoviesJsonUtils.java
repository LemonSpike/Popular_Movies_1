package com.kasetti.pranav.popularmovies.utilities;

import com.google.gson.Gson;
import com.kasetti.pranav.popularmovies.model.Movie;
import com.kasetti.pranav.popularmovies.model.MoviesResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

public class MoviesJsonUtils {

    private static List<Movie> moviesArray;

    public static List<Movie> getMovies(String moviesResponseStr) {
        try {
            JSONObject moviesJson = new JSONObject(moviesResponseStr);

            final String OWM_MESSAGE_CODE = "cod";

            /* Is there an error? */
            if (moviesJson.has(OWM_MESSAGE_CODE)) {
                int errorCode = moviesJson.getInt(OWM_MESSAGE_CODE);

                switch (errorCode) {
                    case HttpURLConnection.HTTP_OK:
                        break;
                    case HttpURLConnection.HTTP_NOT_FOUND:
                        /* Location invalid */
                        return null;
                    default:
                        /* Server probably down */
                        return null;
                }
            }

            moviesArray = (new Gson()).fromJson(moviesResponseStr, MoviesResponse.class).movies;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return moviesArray;
    }

    public static List<String> getImageUrlsFromMovies(List<Movie> movies) {
        ArrayList<String> imageUrlList = new ArrayList<>();
        for (Movie movie: movies) {
            String posterPath = movie.getImageThumbnailURLPath();
            imageUrlList.add(NetworkUtils.buildImageURLString(posterPath.substring(1)));
        }
        return imageUrlList;
    }

    public static String convertMovieToJsonString(Movie movie) {
        return (new Gson()).toJson(movie);
    }

    public static Movie deserializeMovieFromJsonString(String movieJson) {
        return (new Gson()).fromJson(movieJson, Movie.class);
    }
}
