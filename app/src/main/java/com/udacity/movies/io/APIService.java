package com.udacity.movies.io;

import com.udacity.movies.dto.MovieList;
import com.udacity.movies.dto.ReviewList;
import com.udacity.movies.dto.VideoList;
import com.udacity.movies.utils.Config;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import static com.udacity.movies.utils.Config.MOVIE_REVIEWS;
import static com.udacity.movies.utils.Config.SORT_API;
import static com.udacity.movies.utils.Config.TRAILER_VIDEOS;

/**
 * Created by Vijayalakshmi.IN on 27-02-2018.
 */

public interface APIService {
    @GET(SORT_API)
    Call<MovieList> getMovie(@Path("sort") String order, @Query("api_key") String key);

    @GET(TRAILER_VIDEOS)
    Call<VideoList> requestTrailerVideos(@Path(Config.ID) String id, @Query(Config.API_KEY) String apiKey);


    @GET(MOVIE_REVIEWS)
    Call<ReviewList> requestMovieReviews(@Path(Config.ID) String id, @Query(Config.API_KEY) String apiKey);
}

