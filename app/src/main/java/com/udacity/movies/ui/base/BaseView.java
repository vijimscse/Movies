package com.udacity.movies.ui.base;

/**
 * Created by VijayaLakshmi.IN on 02-03-2018.
 */
public interface BaseView {
    void hideLoading();

    void showLoading();

    void showError();

    void showConnectionErrorMessage();
}
