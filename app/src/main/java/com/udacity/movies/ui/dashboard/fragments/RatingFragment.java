package com.udacity.movies.ui.dashboard.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.udacity.movies.utils.SortType;

/**
 * Created by VijayaLakshmi.IN on 27-02-2018.
 */

public class RatingFragment extends MovieListBaseFragment {
    public static RatingFragment newInstance() {
        return new RatingFragment();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        fetchMovieList(SortType.TOP_RATED);
    }

    public void updateUI() {
        updateMovieListFavMovies();
    }
}
