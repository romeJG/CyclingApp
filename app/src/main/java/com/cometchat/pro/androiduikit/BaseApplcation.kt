package com.cometchat.pro.androiduikit

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber


class BaseApplcation:  Application(){

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}