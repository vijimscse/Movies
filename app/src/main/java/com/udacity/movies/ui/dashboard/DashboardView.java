package com.udacity.movies.ui.dashboard;

import com.accolite.bsm.dagger.BaseView;
import com.udacity.movies.dto.MovieList;

/**
 * Created by VijayaLakshmi.IN on 27-02-2018.
 */

public interface DashboardView extends BaseView {
    void setMovieList(MovieList movieList);
}
