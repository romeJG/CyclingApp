package com.cometchat.pro.androiduikit.di

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.room.Room
import com.cometchat.pro.androiduikit.db.CyclingDatabase
import com.cometchat.pro.androiduikit.other.Constants.CYCLING_DATABASE_NAME
import com.cometchat.pro.androiduikit.other.Constants.KEY_FIRST_TIME_TOGGLE
import com.cometchat.pro.androiduikit.other.Constants.KEY_NAME
import com.cometchat.pro.androiduikit.other.Constants.SHARED_PREFERENCES
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provdieCyclingingDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        CyclingDatabase::class.java,
        CYCLING_DATABASE_NAME
    ).build()

    @Singleton
    @Provides
    fun provideRunDao(db: CyclingDatabase) = db.getRunDao()

    @Singleton
    @Provides
    fun provideSharedPreferences(@ApplicationContext app: Context) =
        app.getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE)

    @Singleton
    @Provides
    fun provideName(sharedPref: SharedPreferences) =
        sharedPref.getString(KEY_NAME, "")?:""

    @Singleton
    @Provides
    fun provideWeight(sharedPref: SharedPreferences) =
        sharedPref.getFloat(KEY_NAME, 80f)


    @Singleton
    @Provides
    fun provideFirstTimeToggle(sharedPref: SharedPreferences) =
        sharedPref.getBoolean(KEY_FIRST_TIME_TOGGLE, true)
}