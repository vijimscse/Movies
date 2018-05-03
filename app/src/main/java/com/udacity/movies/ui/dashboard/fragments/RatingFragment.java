package com.udacity.movies.ui.dashboard.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.udacity.movies.R;
import com.udacity.movies.utils.Constants;
import com.udacity.movies.utils.SortType;

/**
 * Created by VijayaLakshmi.IN on 27-02-2018.
 */

public class RatingFragment extends MovieListBaseFragment {
    public static RatingFragment newInstance() {
        return new RatingFragment();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        fetchMovieList(SortType.TOP_RATED);
    }

    @Override
    public void setSelectedPos() {
        if (Constants.mSelectedFragmentPosition == 1 && Constants.mSelectedItemPosition != -1) {
            mRecyclerView.getLayoutManager().scrollToPosition(Constants.mSelectedItemPosition);
            if (mMovieList.size() > Constants.mSelectedItemPosition && getResources().getBoolean(R.bool.multipane)) {
                mMovieListFragmentListener.onMovieSelected(mMovieList.get(Constants.mSelectedItemPosition));
            }
        }
    }

    public void updateUI() {
        updateMovieListFavMovies();
    }

    @Override
    public void onItemClick(int position) {
        //mSelectedMoviePosition = position;
        Constants.mSelectedFragmentPosition = 1;
        Constants.mSelectedItemPosition = position;
        mMovieListFragmentListener.onMovieSelected(mMovieList.get(position));
    }
}
