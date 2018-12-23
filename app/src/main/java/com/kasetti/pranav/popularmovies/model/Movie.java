package com.kasetti.pranav.popularmovies.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Movie {

    private static final String IDENTIFIER_KEY = "id";
    private static final String TITLE_KEY = "original_title";
    private static final String OVERVIEW_KEY = "overview";
    private static final String VOTE_AVERAGE_KEY = "vote_average";
    private static final String VOTE_COUNT_KEY = "vote_count";
    private static final String POSTER_PATH_KEY = "poster_path";
    private static final String RELEASE_DATE_KEY = "release_date";

    @SerializedName(IDENTIFIER_KEY)
    @Expose
    private Double identifier;

    @SerializedName(TITLE_KEY)
    @Expose
    private String originalTitle;

    @SerializedName(OVERVIEW_KEY)
    @Expose
    private String overview;

    @SerializedName(VOTE_AVERAGE_KEY)
    @Expose
    private Double userRating;

    @SerializedName(VOTE_COUNT_KEY)
    @Expose
    private Double numberOfVotes;

    @SerializedName(POSTER_PATH_KEY)
    @Expose
    private String imageThumbnailURLPath;

    @SerializedName(RELEASE_DATE_KEY)
    @Expose
    private String releaseDateString;

    public Double getIdentifier() {
        return identifier;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public Double getUserRating() {
        return userRating;
    }

    public Double getNumberOfVotes() {
        return numberOfVotes;
    }

    public String getImageThumbnailURLPath() {
        return imageThumbnailURLPath;
    }

    public String getReleaseDateString() {
        return releaseDateString;
    }
}

