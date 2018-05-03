package com.udacity.movies.ui.dashboard.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.udacity.movies.R;
import com.udacity.movies.utils.Constants;

public class FavoriteFragment extends MovieListBaseFragment {
    public static FavoriteFragment newInstance() {
        return new FavoriteFragment();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mProgressBar.setVisibility(View.VISIBLE);
        mMovieList.clear();
        mMovieList.addAll(fetchFavMoviesFromDB());
        mMovieListAdapter.notifyDataSetChanged();
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void setSelectedPos() {
        if (Constants.mSelectedFragmentPosition == 2 && Constants.mSelectedItemPosition != -1) {
            mRecyclerView.getLayoutManager().scrollToPosition(Constants.mSelectedItemPosition);
            if (mMovieList.size() > Constants.mSelectedItemPosition && getResources().getBoolean(R.bool.multipane)) {
                mMovieListFragmentListener.onMovieSelected(mMovieList.get(Constants.mSelectedItemPosition));
            }
        }
    }

    public void updateUI() {
        mProgressBar.setVisibility(View.VISIBLE);
        mMovieList.clear();
        mMovieList.addAll(fetchFavMoviesFromDB());
        mMovieListAdapter.notifyDataSetChanged();
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onItemClick(int position) {
        //mSelectedMoviePosition = position;
        Constants.mSelectedFragmentPosition = 2;
        Constants.mSelectedItemPosition = position;
        mMovieListFragmentListener.onMovieSelected(mMovieList.get(position));
    }
}
