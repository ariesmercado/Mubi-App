package com.haroldcalayan.mubi.di

import com.haroldcalayan.mubi.data.repository.AuthenticationRepository
import com.haroldcalayan.mubi.data.repository.MovieRepository
import com.haroldcalayan.mubi.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideGetAiringTodaysUseCase(repository: MovieRepository) = GetAiringTodayUseCase(repository)

    @Provides
    @Singleton
    fun provideGetMovieDetailsUseCase(repository: MovieRepository) = GetMovieDetailsUseCase(repository)

    @Provides
    @Singleton
    fun provideGetOnTvMoviesUseCase(repository: MovieRepository) = GetOnTvMoviesUseCase(repository)

    @Provides
    @Singleton
    fun provideGetPopularMovieUseCase(repository: MovieRepository) = GetPopularMovieUseCase(repository)

    @Provides
    @Singleton
    fun provideGetTopRatedMoviesUseCase(repository: MovieRepository) = GetTopRatedMoviesUseCase(repository)

    @Provides
    @Singleton
    fun provideGetTVDetailUseCase(repository: MovieRepository) = GetTVDetailUseCase(repository)

    @Provides
    @Singleton
    fun provideRequestTokenUseCase(repository: AuthenticationRepository) = GetRequestTokenUseCase(repository)

    @Provides
    @Singleton
    fun provideSeasonDetailsUseCase(repository: MovieRepository) = GetSeasonDetailsUseCase(repository)

    @Provides
    @Singleton
    fun provideSearchResultUseCase(repository: MovieRepository) = GetSearchResultUseCase(repository)

    @Provides
    @Singleton
    fun provideAccountDetailsUseCase(repository: AuthenticationRepository) = GetAccountDetailsUseCase(repository)

    @Provides
    @Singleton
    fun provideFavoriteMovieUseCase(repository: MovieRepository) = GetFavoriteTVShowsUseCase(repository)

    @Provides
    @Singleton
    fun provideSessionUseCase(repository: AuthenticationRepository) = GetSessionUseCase(repository)

}