package com.wopata.babytracker;

import android.app.Application;

import timber.log.Timber;

/**
 * Created by WOPATA on 31/01/17.
 */

public class OpenCVApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());
    }
}
