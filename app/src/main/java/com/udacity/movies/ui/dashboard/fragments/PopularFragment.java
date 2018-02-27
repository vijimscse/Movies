package com.udacity.movies.ui.dashboard.fragments;

import com.udacity.movies.utils.SortType;

/**
 * Created by VijayaLakshmi.IN on 27-02-2018.
 */

public class PopularFragment extends MovieListBaseFragment {

    public static PopularFragment newInstance() {
        return new PopularFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        fetchMovieList(SortType.POPULAR);
    }
}
