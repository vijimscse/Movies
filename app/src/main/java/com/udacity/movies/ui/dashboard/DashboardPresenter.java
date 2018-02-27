package com.udacity.movies.ui.dashboard;

import android.content.Context;

import com.udacity.movies.dto.MovieList;
import com.udacity.movies.io.IOManager;
import com.udacity.movies.ui.dashboard.fragments.MovieListBaseFragment;
import com.udacity.movies.utils.NetworkUtility;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by VijayaLakshmi.IN on 27-02-2018.
 */

public class DashboardPresenter {
    private DashboardView mView;
    private MovieListBaseFragment mMovieListBaseFragment;

    public DashboardPresenter(DashboardView view, MovieListBaseFragment movieListFragment) {
        mView = view;
        mMovieListBaseFragment = movieListFragment;
    }

    /**
     * Request movie list based on the selected sort type.
     *
     * @param sortType
     */
    public void requestMovies(Context context, int sortType) {
        if (NetworkUtility.isInternetConnected(context)) {
            mView.showLoading();
            IOManager.requestMovies(sortType, new Callback<MovieList>() {
                @Override
                public void onResponse(Call<MovieList> call, Response<MovieList> response) {
                    mView.hideLoading();
                    if (response != null && response.body() != null &&
                            response.body().getMovies() != null) {
                        mView.setMovieList(response.body());
                    } else {
                        mView.showError();
                    }
                }

                @Override
                public void onFailure(Call<MovieList> call, Throwable t) {
                    mView.hideLoading();
                    mView.showError();
                }
            });
        } else {
            mView.showConnectionErrorMessage();
        }
    }
}
