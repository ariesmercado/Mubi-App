package com.haroldcalayan.mubi.di

import com.haroldcalayan.mubi.data.repository.AuthenticationRepository
import com.haroldcalayan.mubi.data.repository.AuthenticationRepositoryImpl
import com.haroldcalayan.mubi.data.repository.MovieRepository
import com.haroldcalayan.mubi.data.repository.MovieRepositoryImpl
import com.haroldcalayan.mubi.data.source.local.database.AppDatabase
import com.haroldcalayan.mubi.data.source.local.pref.PreferenceManager
import com.haroldcalayan.mubi.data.source.remote.TMDBApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideMovieRepository(api: TMDBApi, db: AppDatabase): MovieRepository {
        return MovieRepositoryImpl(api, db)
    }

    @Provides
    @Singleton
    fun provideAuthenticationRepository(api: TMDBApi, pref: PreferenceManager): AuthenticationRepository {
        return AuthenticationRepositoryImpl(api, pref)
    }
}