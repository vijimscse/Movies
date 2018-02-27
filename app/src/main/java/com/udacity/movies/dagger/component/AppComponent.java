package com.udacity.movies.dagger.component;

import com.udacity.movies.dagger.module.AppModule;
import com.udacity.movies.dagger.module.DashboardModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by VijayaLakshmi.IN on 27-02-2018.
 */
@Singleton
@Component(
        modules = {
                AppModule.class
        }
)
public interface AppComponent {

    DashboardComponent plus(DashboardModule module);
}
