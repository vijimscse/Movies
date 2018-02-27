package com.udacity.movies.io;

import com.udacity.movies.BuildConfig;
import com.udacity.movies.dto.MovieList;
import com.udacity.movies.dto.ReviewList;
import com.udacity.movies.dto.VideoList;
import com.udacity.movies.utils.Config;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.udacity.movies.utils.Config.BASE_URL;
import static com.udacity.movies.utils.Config.POPULAR;
import static com.udacity.movies.utils.SortType.TOP_RATED;

/**
 * Created by Vijayalakshmi.IN on 27-02-2018.
 */

public class IOManager {

    private static Retrofit getRetrofit() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        return  new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static void requestMovies(int sortType, Callback<MovieList> callback) {
        Retrofit retrofit = getRetrofit();
        APIService apiService = retrofit.create(APIService.class);
        Call<MovieList> call;

        switch (sortType) {
            case TOP_RATED:
                call = apiService.getMovie(Config.TOP_RATED, BuildConfig.API_KEY);
                break;

            default:
                call = apiService.getMovie(POPULAR, BuildConfig.API_KEY);
                break;
        }

        call.enqueue(callback);
    }

    public static void requestTrailerVideos(int movieID, Callback<VideoList> callback) {
        Retrofit retrofit = getRetrofit();
        APIService apiService = retrofit.create(APIService.class);
        Call<VideoList> call = apiService.requestTrailerVideos(String.valueOf(movieID), BuildConfig.API_KEY);
        call.enqueue(callback);
    }

    public static void requestMovieReviews(int movieID, Callback<ReviewList> callback) {
        Retrofit retrofit = getRetrofit();
        APIService apiService = retrofit.create(APIService.class);
        Call<ReviewList> call = apiService.requestMovieReviews(String.valueOf(movieID), BuildConfig.API_KEY);
        call.enqueue(callback);
    }
}
