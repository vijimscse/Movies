package com.udacity.movies;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.udacity.movies.dagger.component.AppComponent;
import com.udacity.movies.dagger.component.DaggerAppComponent;
import com.udacity.movies.dagger.module.AppModule;
import com.udacity.movies.utils.PreferenceManager;

import io.fabric.sdk.android.Fabric;

/**
 * Created by VijayaLakshmi.IN on 27-02-2018.
 */

public class MoviesApplication extends Application {
    private AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        //Init Fabric SDK for logging crashes
        if (BuildConfig.BUILD_TYPE.contains("release")) {
            Fabric.with(this, new Crashlytics());
        }
        PreferenceManager.init(this);
        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }
}
