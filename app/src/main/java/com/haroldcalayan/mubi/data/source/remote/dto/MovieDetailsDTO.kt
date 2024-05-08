package com.haroldcalayan.mubi.data.source.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieDetailsDTO(
    val adult: Boolean? = null,
    @Json(name = "backdrop_path")
    val backdropPath: String? = null,
    @Json(name = "belongs_to_collection")
    val belongsToCollection: BelongsToCollection? = null,
    val budget: Long? = null,
    val genres: List<Genre>? = null,
    val homepage: String? = null,
    val id: Long? = null,
    @Json(name = "imdb_id")
    val imdbID: String? = null,
    @Json(name = "original_language")
    val originalLanguage: String? = null,
    @Json(name = "original_title")
    val originalTitle: String? = null,
    val overview: String? = null,
    val popularity: Double? = null,
    @Json(name = "poster_path")
    val posterPath: String? = null,
    @Json(name = "production_companies")
    val productionCompanies: List<ProductionCompany>? = null,
    @Json(name = "production_countries")
    val productionCountries: List<ProductionCountry>? = null,
    @Json(name = "release_date")
    val releaseDate: String? = null,
    val revenue: Long? = null,
    val runtime: Long? = null,
    @Json(name = "spoken_languages")
    val spokenLanguages: List<SpokenLanguage>? = null,
    val status: String? = null,
    val tagline: String? = null,
    val title: String? = null,
    val video: Boolean? = null,
    @Json(name = "vote_average")
    val voteAverage: Double? = null,
    @Json(name = "vote_count")
    val voteCount: Long? = null
) {

    @JsonClass(generateAdapter = true)
    data class BelongsToCollection(
        val id: Long? = null,
        val name: String? = null,
        @Json(name = "poster_path")
        val posterPath: String? = null,
        @Json(name = "backdrop_path")
        val backdropPath: String? = null
    )

    @JsonClass(generateAdapter = true)
    data class Genre(
        val id: Long? = null,
        val name: String? = null
    )

    @JsonClass(generateAdapter = true)
    data class ProductionCompany(
        val id: Long? = null,
        @Json(name = "logo_path")
        val logoPath: String? = null,
        val name: String? = null,
        @Json(name = "origin_country")
        val originCountry: String? = null
    )

    @JsonClass(generateAdapter = true)
    data class ProductionCountry(
        @Json(name = "iso_3166_1")
        val iso3166_1: String? = null,
        val name: String? = null
    )

    @JsonClass(generateAdapter = true)
    data class SpokenLanguage(
        @Json(name = "english_name")
        val englishName: String? = null,
        @Json(name = "iso_639_1")
        val iso639_1: String? = null,
        val name: String? = null
    )
}


