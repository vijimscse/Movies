package com.udacity.movies.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.udacity.movies.R;
import com.udacity.movies.ui.dashboard.fragments.FavoriteFragment;
import com.udacity.movies.ui.dashboard.fragments.PopularFragment;
import com.udacity.movies.ui.dashboard.fragments.RatingFragment;

/**
 * Created by VijayaLakshmi.IN on 27-02-2018.
 */

public class DashboardPagerAdapter extends FragmentStatePagerAdapter {
    private static final int PAGE_COUNT = 3;
    private Context mContext;
    private PopularFragment mPopularFragment;
    private RatingFragment mRatingFragment;
    private FavoriteFragment mFavoriteFragment;

    public DashboardPagerAdapter(FragmentManager childFragmentManager, Context context) {
        super(childFragmentManager);
        mContext = context;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;

        switch (position) {
            case 0:
                title = mContext.getString(R.string.popular);
                break;
            case 1:
                title = mContext.getString(R.string.rating);
                break;
            case 2:
                title = mContext.getString(R.string.favorite);
                break;
        }

        return title;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;

        switch (position) {
            case 0:
                fragment = mPopularFragment = PopularFragment.newInstance();
                break;
            case 1:
                fragment = mRatingFragment = RatingFragment.newInstance();
                break;

            case 2:
                fragment = mFavoriteFragment = FavoriteFragment.newInstance();
                break;
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    public void updateUI() {
        if (mFavoriteFragment != null) {
            mFavoriteFragment.updateUI();
        }
    }
}
