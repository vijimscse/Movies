package com.udacity.movies.ui.dashboard.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.udacity.movies.R;
import com.udacity.movies.utils.Constants;
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

    @Override
    public void setSelectedPos() {
        if (Constants.mSelectedFragmentPosition == 0) {
            if (Constants.mSelectedItemPosition == -1) {
                Constants.mSelectedItemPosition = 0;
            }
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
        Constants.mSelectedFragmentPosition = 0;
        Constants.mSelectedItemPosition = position;
        mMovieListFragmentListener.onMovieSelected(mMovieList.get(position));
    }
}
