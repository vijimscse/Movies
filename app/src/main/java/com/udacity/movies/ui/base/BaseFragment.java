package com.udacity.movies.ui.base;

import android.support.v4.app.Fragment;

import com.accolite.bsm.dagger.BaseView;

/**
 * Created by VijayaLakshmi.IN on 27-02-2018.
 */

public class BaseFragment extends Fragment implements BaseView {
    @Override
    public void hideLoading() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void showError() {

    }

    @Override
    public void showConnectionErrorMessage() {

    }
}
