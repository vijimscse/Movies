package com.udacity.movies.ui.dashboard.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.udacity.movies.R;
import com.udacity.movies.utils.SortType;

import butterknife.ButterKnife;

/**
 * Created by VijayaLakshmi.IN on 27-02-2018.
 */

public class RatingFragment extends MovieListBaseFragment {
    public static RatingFragment newInstance() {
        return new RatingFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycler_view, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        fetchMovieList(SortType.POPULAR);
    }
}
