package com.kasetti.pranav.popularmovies.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MoviesResponse {

    private static final String RESULTS_KEY = "results";

    @SerializedName(RESULTS_KEY)
    @Expose
    public List<Movie> movies;
}
