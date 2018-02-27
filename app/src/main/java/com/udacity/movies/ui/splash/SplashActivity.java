package com.udacity.movies.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.udacity.movies.R;
import com.udacity.movies.ui.dashboard.DashboardActivity;

/**
 * Created by VijayaLakshmi.IN on 27-02-2018.
 */

public class SplashActivity extends AppCompatActivity {
    private static final int SPLASH_SCREEN_DISPLAY_TIME = 3000;
    private static final int SPLASH_HANDLER_START_NEXT_PAGE_ID = 101;

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.what == SPLASH_HANDLER_START_NEXT_PAGE_ID) {
                launchNextScreen();
            }
        }
    };

    /**
     * Launch next screen based on login status
     */
    private void launchNextScreen() {
        mHandler.removeMessages(SPLASH_HANDLER_START_NEXT_PAGE_ID);
        Intent intent;

        intent = new Intent(this, DashboardActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mHandler.sendEmptyMessageDelayed(SPLASH_HANDLER_START_NEXT_PAGE_ID, SPLASH_SCREEN_DISPLAY_TIME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mHandler.removeMessages(SPLASH_HANDLER_START_NEXT_PAGE_ID);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mHandler.sendEmptyMessageDelayed(SPLASH_HANDLER_START_NEXT_PAGE_ID, SPLASH_SCREEN_DISPLAY_TIME);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeMessages(SPLASH_HANDLER_START_NEXT_PAGE_ID);
    }
}
