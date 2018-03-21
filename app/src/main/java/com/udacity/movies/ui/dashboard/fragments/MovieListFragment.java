package com.udacity.movies.ui.dashboard.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.udacity.movies.R;
import com.udacity.movies.ui.adapter.DashboardPagerAdapter;
import com.udacity.movies.ui.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by VijayaLakshmi.IN on 27-02-2018.
 */

public class MovieListFragment extends BaseFragment {

    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;

    @BindView(R.id.tab_pager)
    ViewPager mTabPager;

    private DashboardPagerAdapter mDashboardPagerAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_list, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mDashboardPagerAdapter = new DashboardPagerAdapter(getChildFragmentManager(), getActivity());
        mTabPager.setAdapter(mDashboardPagerAdapter);
        mTabPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mDashboardPagerAdapter.updateUI();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mTabLayout.setupWithViewPager(mTabPager);
    }

    public static MovieListFragment newInstance() {
        return new MovieListFragment();
    }
}
