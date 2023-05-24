package com.aisle.di

import android.content.Context
import androidx.room.Room
import com.aisle.BuildConfig
import com.aisle.data.local.pref.EncPref
import com.aisle.data.local.pref.Preference
import com.aisle.data.local.pref.PreferenceManager
import com.aisle.data.remote.Api
import com.aisle.data.remote.ApiManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Singleton
    @Provides
    fun provideAppEncSharedPref(@ApplicationContext context: Context): EncPref {
        return EncPref.Builder()
            .serPrefName(context.packageName)
            .setContext(context)
            .setDebuggable(BuildConfig.DEBUG)
            .build()
    }


    @Singleton
    @Provides
    fun provideAppPreference(encPref: EncPref): Preference {
        return PreferenceManager(encPref)
    }


    @Singleton
    @Provides
    fun provideAppApi(): Api {
        return ApiManager()
    }
}