package com.example.trainee1.wozcar;

import android.app.Application;

import timber.log.Timber;

/**
 * Created by Trainee1 on 7/21/2017.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Contextor.getInstance().init(this);

        if (isEnableLog()) {
            Timber.plant(new Timber.DebugTree());
        }
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    private boolean isEnableLog() {
        if (BuildConfig.DEBUG) {
            return true;
        }

        return false;
    }
}