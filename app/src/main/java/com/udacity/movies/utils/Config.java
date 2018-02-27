package com.udacity.movies.utils;

/**
 * Created by VijayaLakshmi.IN on 27-02-2018.
 */

public interface Config {

    String API_KEY = "api_key";
    String POPULAR = "popular";
    String TOP_RATED = "top_rated";
    String BASE_URL = "http://api.themoviedb.org/3/movie/";
    String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/w185";

    String ID = "id";
    String TRAILER_VIDEOS = "{id}/videos";
    String MOVIE_REVIEWS = "{id}/reviews";
    String SORT_API = "{sort}";
}
