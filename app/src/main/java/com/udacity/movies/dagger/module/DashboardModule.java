package com.udacity.movies.dagger.module;

import com.udacity.movies.ui.dashboard.DashboardPresenter;
import com.udacity.movies.ui.dashboard.DashboardView;
import com.udacity.movies.ui.dashboard.fragments.MovieListBaseFragment;
import com.udacity.movies.ui.dashboard.fragments.MovieListFragment;
import com.udacity.movies.ui.dashboard.fragments.PopularFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by VijayaLakshmi.IN on 27-02-2018.
 */
@Module
public class DashboardModule {
    private final MovieListBaseFragment mMovieListFragment;
    private final DashboardView mView;

    public DashboardModule(MovieListBaseFragment activity, DashboardView view) {
        mMovieListFragment = activity;
        mView = view;
    }

    @Provides
    @ActivityScope
    MovieListBaseFragment provideFragment() {
        return mMovieListFragment;
    }

    @Provides
    @ActivityScope
    DashboardView provideView() {
        return mView;
    }

    @Provides
    @ActivityScope
    DashboardPresenter providePresenter(MovieListBaseFragment movieListFragment, DashboardView view) {
        return new DashboardPresenter(view, mMovieListFragment);
    }
}
