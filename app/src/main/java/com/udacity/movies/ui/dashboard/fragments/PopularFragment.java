package com.udacity.movies.ui.dashboard.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.udacity.movies.utils.SortType;

/**
 * Created by VijayaLakshmi.IN on 27-02-2018.
 */

public class PopularFragment extends MovieListBaseFragment {

    public static PopularFragment newInstance() {
        return new PopularFragment();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        fetchMovieList(SortType.POPULAR);
    }
}
