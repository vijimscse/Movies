package com.udacity.movies.ui.base;

import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.udacity.movies.R;
import com.udacity.movies.ui.about.AboutActivity;
import com.udacity.movies.ui.dashboard.DashboardActivity;
import com.udacity.movies.utils.DialogUtility;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by VijayaLakshmi.IN on 27-02-2018.
 */

public class BaseActivity extends AppCompatActivity implements BaseView {

    @BindView(R.id.sidebar_drawer_layout)
    DrawerLayout mDrawerLayout;

    @BindView(R.id.navigation_view)
    NavigationView mNavigationView;

    @BindView(R.id.top_toolbar)
    Toolbar mToolBar;

    @BindView(R.id.view_container)
    FrameLayout mViewContainer;

    @BindView(R.id.collapse_toolbar)
    CollapsingToolbarLayout mCollapseToolBar;

    @BindView(R.id.header_container)
    RelativeLayout mHeaderContainer;

    private ActionBarDrawerToggle mActionBarDrawerToggle;

    @Override
    public void setContentView(int layoutResId) {
        super.setContentView(R.layout.activity_base);
        mViewContainer = findViewById(R.id.view_container);
        getLayoutInflater().inflate(layoutResId, mViewContainer);

        ButterKnife.bind(this);
        if (this instanceof DashboardActivity) {
            initNavigationDrawer();
        } else {
            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        }
        initializeViews();
    }

    private void initializeViews() {
        setSupportActionBar(mToolBar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void hideLoading() {
        DialogUtility.hideLoading(this);
    }

    @Override
    public void showLoading() {
        DialogUtility.showLoading(this);
    }

    @Override
    public void showError() {
        DialogUtility.showSnackBar(mViewContainer, getString(R.string.generic_error));
    }

    @Override
    public void showConnectionErrorMessage() {
        DialogUtility.showSnackBar(mViewContainer, getString(R.string.network_error));
    }

    protected void setPageTitle(String pageTitle) {
        getSupportActionBar().setTitle(pageTitle);
    }

    protected void setPageTitle(@StringRes int pageTitleResId) {
        getSupportActionBar().setTitle(pageTitleResId);
    }

    /**
     * Initialize navigation drawer components
     */
    private void initNavigationDrawer() {
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                switch (itemId) {
                    case R.id.menu_about:
                        mDrawerLayout.closeDrawers();
                        Intent intent = new Intent(BaseActivity.this, AboutActivity.class);
                        startActivity(intent);
                        break;
                }
                return false;
            }
        });
        // Setting up the navigation drawer toggle
        mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolBar,
                R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        mDrawerLayout.addDrawerListener(mActionBarDrawerToggle);
        mActionBarDrawerToggle.syncState();
        mActionBarDrawerToggle.setDrawerIndicatorEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (this instanceof DashboardActivity) {
                    mDrawerLayout.openDrawer(GravityCompat.START);  // OPEN DRAWER
                } else {
                    onBackPressed();
                }
                return true;
        }

        return false;
    }

    protected void hideToolBar() {
        mCollapseToolBar.setVisibility(View.GONE);
    }

    protected void addHeaderView(@LayoutRes int layoutRes) {
        if (layoutRes != 0) {
            getLayoutInflater().inflate(layoutRes, mHeaderContainer, true);
        }
    }
}
