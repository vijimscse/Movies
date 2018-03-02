package com.udacity.movies.ui.dashboard.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

public class FavoriteFragment extends MovieListBaseFragment {
    public static FavoriteFragment newInstance() {
        return new FavoriteFragment();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mProgressBar.setVisibility(View.VISIBLE);
        mMovieList.clear();
        mMovieList.addAll(fetchFavMoviesFromDB());
        mMovieListAdapter.notifyDataSetChanged();
        mProgressBar.setVisibility(View.GONE);
    }

    public void updateUI() {
        mProgressBar.setVisibility(View.VISIBLE);
        mMovieList.clear();
        mMovieList.addAll(fetchFavMoviesFromDB());
        mMovieListAdapter.notifyDataSetChanged();
        mProgressBar.setVisibility(View.GONE);
    }
}
