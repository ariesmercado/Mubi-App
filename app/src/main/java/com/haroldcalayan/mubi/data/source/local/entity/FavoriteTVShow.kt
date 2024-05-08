package com.haroldcalayan.mubi.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavoriteTVShow(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val adult: Boolean? = null,
    val backdropPath: String? = null,
    val originalLanguage: String? = null,
    val originalTitle: String? = null,
    val overview: String? = null,
    val popularity: Double? = null,
    val posterPath: String? = null,
    val releaseDate: String? = null,
    val title: String? = null,
    val video: Boolean? = null,
    val voteAverage: Double? = null,
    val voteCount: Long? = null
)