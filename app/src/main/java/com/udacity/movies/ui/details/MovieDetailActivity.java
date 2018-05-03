package com.udacity.movies.ui.details;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;

import com.udacity.movies.R;
import com.udacity.movies.dto.Movie;
import com.udacity.movies.ui.base.BaseActivity;

import static com.udacity.movies.utils.IBundleKeys.SELECTED_MOVIE;

/**
 * Created by VijayaLakshmi.IN on 20-03-2018.
 */

public class MovieDetailActivity extends BaseActivity {
    private MovieDetailFragment movieDetailFragment;
    private Movie mSelectedMovie;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dashboard);
        setPageTitle(R.string.dashboard);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE && getResources().getBoolean(R.bool.multipane)) {
            finish();
        }
        if (savedInstanceState != null) {
            return;
        }
        mSelectedMovie = getIntent().getParcelableExtra(SELECTED_MOVIE);
        addMovieDetailFragment(mSelectedMovie);
    }

    private void addMovieDetailFragment(Movie selectedMovie) {
        movieDetailFragment = MovieDetailFragment.newInstance(selectedMovie);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragment_container, movieDetailFragment);
        transaction.commit();
    }
}
