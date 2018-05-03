package com.udacity.movies.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.udacity.movies.R;
import com.udacity.movies.dto.Movie;
import com.udacity.movies.ui.base.BaseActivity;
import com.udacity.movies.ui.dashboard.fragments.MovieListFragment;
import com.udacity.movies.ui.details.MovieDetailActivity;
import com.udacity.movies.ui.details.MovieDetailFragment;
import com.udacity.movies.utils.IBundleKeys;

/**
 * Created by VijayaLakshmi.IN on 27-02-2018.
 */

public class DashboardActivity extends BaseActivity implements IMovieListFragmentListener {
    private MovieListFragment mMovieListFragment;
    private MovieDetailFragment movieDetailFragment;
    private Movie mSelectedMovie;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dashboard);
        setPageTitle(R.string.dashboard);
        if (savedInstanceState != null) {
            return;
        } else {
            addMovieListFragment();
        }
        if (getResources().getBoolean(R.bool.multipane) && mSelectedMovie != null) {
            addMovieDetailFragment(mSelectedMovie);
        }
    }

    private void addMovieListFragment() {
        mMovieListFragment = MovieListFragment.newInstance();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragment_container, mMovieListFragment);
        transaction.commit();
    }

    private void addMovieDetailFragment(Movie selectedMovie) {
        mSelectedMovie = selectedMovie;
        movieDetailFragment = MovieDetailFragment.newInstance(selectedMovie);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment2_container, movieDetailFragment);
        transaction.commit();
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (fragment != null && fragment instanceof MovieDetailFragment) {
            outState.putIntArray("ARTICLE_SCROLL_POSITION",
                    new int[]{((MovieDetailFragment)fragment).getScrollX(), ((MovieDetailFragment)fragment).getScrollY()});
        }
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        final int[] position = savedInstanceState.getIntArray("ARTICLE_SCROLL_POSITION");
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (position != null && fragment != null && fragment instanceof MovieDetailFragment) {
            ((MovieDetailFragment)fragment).scroll(position[0], position[1]);
        }
    }

    @Override
    public void onMovieSelected(Movie selectedMovie) {
        selectedMovie.setSelected(true);
        if (!getResources().getBoolean(R.bool.multipane)) {
            Intent intent = new Intent(this, MovieDetailActivity.class);
            intent.putExtra(IBundleKeys.SELECTED_MOVIE, selectedMovie);
            startActivity(intent);
        } else {
            addMovieDetailFragment(selectedMovie);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);

        if (fragment instanceof MovieListFragment) {
            ((MovieListFragment)fragment).updateUI();
        }
    }
}
