package com.udacity.movies.utils;

import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.udacity.movies.ui.dialog.LoadingDialogFragment;

/**
 * Created by Vijayalakshmi.IN on 27-02-2018.
 */

public class DialogUtility {
    private static Snackbar mToast;

    public static void showSnackBar(View context, String message) {
        if (mToast != null) {
            mToast.dismiss();
        }
        if (context != null) {
            mToast = Snackbar.make(context, message, Snackbar.LENGTH_SHORT);
            mToast.show();
        }
    }

    public static void showSnackBar(View context, int message) {
        if (mToast != null) {
            mToast.dismiss();
        }
        if (context != null) {
            mToast = Snackbar.make(context, message, Snackbar.LENGTH_SHORT);
            mToast.show();
        }
    }

    public static void showLoading(FragmentActivity fragmentActivity) {
        FragmentManager fm = fragmentActivity.getSupportFragmentManager();
        LoadingDialogFragment editNameDialog = new LoadingDialogFragment();
        editNameDialog.show(fm, "dialog");
    }

    public static void hideLoading(FragmentActivity fragmentActivity) {
        FragmentTransaction ft = fragmentActivity.getSupportFragmentManager().beginTransaction();
        Fragment prev = fragmentActivity.getSupportFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
            ft.commit();
        }
    }
}
