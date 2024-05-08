package com.haroldcalayan.mubi.di

import android.content.Context
import androidx.room.Room
import com.haroldcalayan.mubi.BuildConfig
import com.haroldcalayan.mubi.data.source.local.database.AppDatabase
import com.haroldcalayan.mubi.data.source.local.pref.PreferenceManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object StorageModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            BuildConfig.DATABASE_NAME
        ).build()
    }

    @Provides
    fun provideFavoriteTVShowDao(database: AppDatabase) = database.favoriteTVShowDao()

    @Provides
    @Singleton
    fun providePreferenceManager(@ApplicationContext appContext: Context): PreferenceManager {
        return PreferenceManager(appContext, BuildConfig.SHARED_PREF_NAME)
    }

}