package com.haroldcalayan.mubi.domain.mapper

import com.haroldcalayan.mubi.data.source.local.entity.FavoriteTVShow
import com.haroldcalayan.mubi.data.source.remote.dto.MoviesDTO

fun MoviesDTO.Result.toFavoriteTVShow() = FavoriteTVShow(
    adult = adult,
    backdropPath = backdropPath,
    originalLanguage = originalLanguage,
    originalTitle = originalTitle,
    overview = overview,
    popularity = popularity,
    posterPath = posterPath,
    releaseDate = releaseDate,
    title = title,
    video = video,
    voteAverage = voteAverage,
    voteCount = voteCount
)

fun FavoriteTVShow.toMoviesDTO() = MoviesDTO.Result(
    adult = adult,
    backdropPath = backdropPath,
    originalLanguage = originalLanguage,
    originalTitle = originalTitle,
    overview = overview,
    popularity = popularity,
    posterPath = posterPath,
    releaseDate = releaseDate,
    title = title,
    video = video,
    voteAverage = voteAverage,
    voteCount = voteCount
)