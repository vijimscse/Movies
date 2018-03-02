package com.udacity.movies.ui.dashboard;

import com.udacity.movies.dto.MovieList;
import com.udacity.movies.ui.base.BaseView;

/**
 * Created by VijayaLakshmi.IN on 27-02-2018.
 */
public interface DashboardView extends BaseView {
    void setMovieList(MovieList movieList);
}
