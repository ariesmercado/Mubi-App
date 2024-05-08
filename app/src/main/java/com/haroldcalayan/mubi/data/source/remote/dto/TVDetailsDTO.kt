package com.haroldcalayan.mubi.data.source.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TVDetailsDTO (
    val adult: Boolean? = null,
    @Json(name = "backdrop_path")
    val backdropPath: Any? = null,
    @Json(name = "created_by")
    val createdBy: List<Any?>? = null,
    @Json(name = "episode_run_time")
    val episodeRunTime: List<Long>? = null,
    @Json(name = "first_air_date")
    val firstAirDate: String? = null,
    val genres: List<Genre>? = null,
    val homepage: String? = null,
    val id: Long? = null,
    @Json(name = "in_production")
    val inProduction: Boolean? = null,
    val languages: List<String>? = null,
    @Json(name = "last_air_date")
    val lastAirDate: String? = null,
    @Json(name = "last_episode_to_air")
    val lastEpisodeToAir: TEpisodeToAir? = null,
    val name: String? = null,
    @Json(name = "next_episode_to_air")
    val nextEpisodeToAir: TEpisodeToAir? = null,
    val networks: List<Network>? = null,
    @Json(name = "number_of_episodes")
    val numberOfEpisodes: Long? = null,
    @Json(name = "number_of_seasons")
    val numberOfSeasons: Long? = null,
    @Json(name = "origin_country")
    val originCountry: List<String>? = null,
    @Json(name = "original_language")
    val originalLanguage: String? = null,
    @Json(name = "original_name")
    val originalName: String? = null,
    val overview: String? = null,
    val popularity: Double? = null,
    @Json(name = "poster_path")
    val posterPath: String? = null,
    @Json(name = "production_companies")
    val productionCompanies: List<Network>? = null,
    @Json(name = "production_countries")
    val productionCountries: List<ProductionCountry>? = null,
    val seasons: List<Season>? = null,
    @Json(name = "spoken_languages")
    val spokenLanguages: List<SpokenLanguage>? = null,
    val status: String? = null,
    val tagline: String? = null,
    val type: String? = null,
    @Json(name = "vote_average")
    val voteAverage: Double? = null,
    @Json(name = "vote_count")
    val voteCount: Long? = null
) {
    @JsonClass(generateAdapter = true)
    data class Genre (
        val id: Long? = null,
        val name: String? = null
    )

    @JsonClass(generateAdapter = true)
    data class TEpisodeToAir (
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
        val stillPath: String? = null,
        @Json(name = "vote_average")
        val voteAverage: Long? = null,
        @Json(name = "vote_count")
        val voteCount: Long? = null
    )

    @JsonClass(generateAdapter = true)
    data class Network (
        val id: Long? = null,
        val name: String? = null,
        @Json(name = "logo_path")
        val logoPath: String? = null,
        @Json(name = "origin_country")
        val originCountry: String? = null
    )

    @JsonClass(generateAdapter = true)
    data class ProductionCountry (
        @Json(name = "iso_3166_1")
        val iso3166_1: String? = null,
        val name: String? = null
    )

    @JsonClass(generateAdapter = true)
    data class Season (
        @Json(name = "air_date")
        val airDate: String? = null,
        @Json(name = "episode_count")
        val episodeCount: Long? = null,
        val id: Long? = null,
        val name: String? = null,
        val overview: String? = null,
        @Json(name = "poster_path")
        val posterPath: Any? = null,
        @Json(name = "season_number")
        val seasonNumber: Long? = null
    )

    @JsonClass(generateAdapter = true)
    data class SpokenLanguage (
        @Json(name = "english_name")
        val englishName: String? = null,
        @Json(name = "iso_639_1")
        val iso639_1: String? = null,
        val name: String? = null
    )
}


