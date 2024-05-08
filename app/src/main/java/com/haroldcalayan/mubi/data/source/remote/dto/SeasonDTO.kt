package com.haroldcalayan.mubi.data.source.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SeasonDTO(
    @Json(name = "_id")
    val id: String? = null,

    @Json(name = "air_date")
    val airDate: String? = null,

    val episodes: List<Episode>? = null,
    val name: String? = null,
    val overview: String? = null,

    @Json(name = "id")
    val welcomeID: Long? = null,

    @Json(name = "poster_path")
    val posterPath: Any? = null,

    @Json(name = "season_number")
    val seasonNumber: Long? = null
)

@JsonClass(generateAdapter = true)
data class Episode (
    @Json(name = "air_date")
    val airDate: String? = null,

    @Json(name = "episode_number")
    val episodeNumber: Long? = null,

    val id: Long? = null,
    val name: String? = null,
    val overview: String? = null,

    @Json(name = "production_code")
    val productionCode: String? = null,

    val runtime: Any? = null,

    @Json(name = "season_number")
    val seasonNumber: Long? = null,

    @Json(name = "show_id")
    val showID: Long? = null,

    @Json(name = "still_path")
    val stillPath: Any? = null,

    @Json(name = "vote_average")
    val voteAverage: Long? = null,

    @Json(name = "vote_count")
    val voteCount: Long? = null,

    val crew: List<Any?>? = null,

    @Json(name = "guest_stars")
    val guestStars: List<Any?>? = null
)
