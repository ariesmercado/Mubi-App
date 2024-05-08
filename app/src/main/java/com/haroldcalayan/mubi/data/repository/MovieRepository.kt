package com.haroldcalayan.mubi.data.repository

import com.haroldcalayan.mubi.common.base.BaseRepository
import com.haroldcalayan.mubi.data.source.local.database.AppDatabase
import com.haroldcalayan.mubi.data.source.remote.TMDBApi
import com.haroldcalayan.mubi.data.source.remote.dto.*
import com.haroldcalayan.mubi.domain.mapper.toFavoriteTVShow
import com.haroldcalayan.mubi.domain.mapper.toMoviesDTO
import okio.IOException
import retrofit2.HttpException

interface MovieRepository {
    suspend fun getPopularMovies(): MoviesDTO
    suspend fun getTopRatedMovies(): MoviesDTO
    suspend fun getOnTvMovies(): MoviesDTO
    suspend fun getAiringTodayMovies(): MoviesDTO
    suspend fun getMovieDetails(movieId: Int): MovieDetailsDTO
    suspend fun getTVDetails(tvId: Int): TVDetailsDTO
    suspend fun getSeasonDetails(tvId: Int, seasonNumber: Int): SeasonDTO
    suspend fun searchMovie(query: String): SearchResultDTO
    suspend fun getFavoriteTVShows(accountId: Int, sessionId: String): MoviesDTO
    suspend fun clearLocalData()
}

class MovieRepositoryImpl(
    private val api: TMDBApi,
    private val db: AppDatabase
) : BaseRepository(), MovieRepository {

    override suspend fun getPopularMovies(): MoviesDTO {
        return api.getPopularMovies()
    }

    override suspend fun getTopRatedMovies(): MoviesDTO {
        return api.getTopRatedMovies()
    }

    override suspend fun getOnTvMovies(): MoviesDTO {
        return api.getOnTvMovies()
    }

    override suspend fun getAiringTodayMovies(): MoviesDTO {
        return api.getAiringTodayMovies()
    }

    override suspend fun getMovieDetails(movieId: Int): MovieDetailsDTO {
        return api.getMovieDetail(movieId = movieId)
    }

    override suspend fun getTVDetails(tvId: Int): TVDetailsDTO {
        return api.getTVDetail(tvId = tvId)
    }

    override suspend fun getSeasonDetails(tvId: Int, seasonNumber: Int): SeasonDTO {
        return api.getSeasonDetail(tvId = tvId, seasonNumber = seasonNumber)
    }

    override suspend fun searchMovie(query: String): SearchResultDTO {
        return api.getSearch(query = query)
    }

    override suspend fun getFavoriteTVShows(accountId: Int, sessionId: String): MoviesDTO {
        return try {
            val response = api.getFavoriteTVShows(accountId = accountId, sessionId = sessionId)
            db.favoriteTVShowDao().nukeTable()
            val favoriteTVShows = response.results?.map { it.toFavoriteTVShow() }
            db.favoriteTVShowDao().insertAll(favoriteTVShows.orEmpty())
            response
        } catch (ex: Exception) {
            ex.printStackTrace()
            when(ex) {
                is HttpException, is IOException -> {
                    val movies = db.favoriteTVShowDao().all()?.map { it.toMoviesDTO() }
                    MoviesDTO(results = movies)
                }
                else -> throw ex
            }
        }
    }

    override suspend fun clearLocalData() {
        db.favoriteTVShowDao().nukeTable()
    }
}