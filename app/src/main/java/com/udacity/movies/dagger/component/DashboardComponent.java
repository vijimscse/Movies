package com.udacity.movies.dagger.component;

import com.udacity.movies.dagger.module.ActivityScope;
import com.udacity.movies.dagger.module.DashboardModule;
import com.udacity.movies.ui.dashboard.fragments.MovieListBaseFragment;

import dagger.Subcomponent;

/**
 * Created by VijayaLakshmi.IN on 27-02-2018.
 */
@ActivityScope
@Subcomponent
        (
                modules = DashboardModule.class
        )
public interface DashboardComponent {

    MovieListBaseFragment inject(MovieListBaseFragment movieListFragment);
}
