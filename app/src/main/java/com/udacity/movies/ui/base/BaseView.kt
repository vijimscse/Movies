package com.accolite.bsm.dagger

/**
 * Created by VijayaLakshmi.IN on 27-02-2018.
 */
interface BaseView {
    fun hideLoading()

    fun showLoading()

    fun showError()

    fun showConnectionErrorMessage()
}