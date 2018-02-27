package com.udacity.movies.ui.dashboard;

import com.udacity.movies.dto.Movie;

/**
 * Created by VijayaLakshmi.IN on 27-02-2018.
 */

public interface IMovieListFragmentListener {
    void onMovieSelected(Movie selectedMovie);
}
